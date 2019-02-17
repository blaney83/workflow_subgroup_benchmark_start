package io.github.blaney;

import java.io.File;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.knime.core.data.DataTableSpec;
import org.knime.core.data.DataTableSpecCreator;
import org.knime.core.data.container.ColumnRearranger;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;

	

/**
 * This is the model implementation of SubgroupBenchmarkStart.
 * The starting point for workflow clocking. Passes through data table and starts the clock for a workflow or sub-section of a workflow. When connected to the SubgroupBenchmarkEnd node(s), this node allows tracking time performance between different executions.
 *
 * @author Benjamin Laney
 */
public class SubgroupBenchmarkStartNodeModel extends NodeModel {
	
	private static final int IN_PORT = 0;

	private static int m_runCount = 1;
	
	//note config keys must match with the "End" node keys!
	private static final String CFGKEY_RUN_NAME = "runName";
	private static final String CFGKEY_CLEAR_DATA = "clearData";
	private static final String CFGKEY_RUN_COUNT = "runCount";
	private static final String CFGKEY_START_TIME = "runStartTime";
	
	private static final String DEFAULT_RUN_NAME = "Execution";
	private static final boolean DEFAULT_CLEAR_DATA = false;
	
	private static SettingsModelString m_runName = new SettingsModelString(CFGKEY_RUN_NAME, DEFAULT_RUN_NAME);
	private static SettingsModelBoolean m_clearData = new SettingsModelBoolean(CFGKEY_CLEAR_DATA, DEFAULT_CLEAR_DATA);

    protected SubgroupBenchmarkStartNodeModel() {
        super(1, 1);
    }

    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
            final ExecutionContext exec) throws Exception { 	
    	DataTableSpecCreator tableSpecCreator = new DataTableSpecCreator(inData[IN_PORT].getDataTableSpec());
    	//must be done in execution as opposed to config or other
    	tableSpecCreator.putProperties(propertiesBuilder());
    	ColumnRearranger outColumnRearranger = new ColumnRearranger(tableSpecCreator.createSpec());
    	BufferedDataTable outTable = exec.createColumnRearrangeTable(inData[IN_PORT], outColumnRearranger, exec);
    	m_runCount++;
        return new BufferedDataTable[]{outTable};
    }
    
    private Map<String, String> propertiesBuilder(){
    	Map<String, String> properties = new LinkedHashMap<String, String>();
    	properties.put(CFGKEY_START_TIME, Long.toString(System.nanoTime()));
    	properties.put(CFGKEY_RUN_COUNT, Integer.toString(m_runCount));
    	properties.put(CFGKEY_RUN_NAME, m_runName.getStringValue());
    	properties.put(CFGKEY_CLEAR_DATA, Boolean.toString(m_clearData.getBooleanValue()));
    	return properties;
    }
    

    @Override
    protected void reset() {
    	if(m_clearData.getBooleanValue()) {
    		m_runCount = 0;
    	}
    }

    @Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
            throws InvalidSettingsException {
    	
    	//currently nothing to do here
        return new DataTableSpec[]{null};
    }

    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {
    	settings.addInt(CFGKEY_RUN_COUNT, m_runCount);
    	m_runName.saveSettingsTo(settings);
    	
    	//default behavior should probably NOT save the settings for this because 
    	//desired bahavior will probably be one clear and then additional data 
    	//collection until the next clear
//    	m_clearData.saveSettingsTo(settings);
    }

    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
    	m_runCount = settings.getInt(CFGKEY_RUN_COUNT);
    	m_runName.setStringValue(settings.getString(CFGKEY_RUN_NAME));
    	
//    	m_clearData.setBooleanValue(settings.getBoolean(CFGKEY_CLEAR_DATA));
    }

    @Override
    protected void validateSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
    	if(m_runName.getStringValue().trim().length() == 0) {
    		throw new InvalidSettingsException("Please enter a valid name for Row Key seed in the benchmarked output table or leave it blank.");
    	}
    	if(m_clearData.getBooleanValue()) {
    		m_runCount = 0;
    	}
    }

    @Override
    protected void loadInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
    	//not needed at this time
    }

    @Override
    protected void saveInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
    	//not needed at this time
    }

}

