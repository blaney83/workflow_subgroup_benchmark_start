package io.github.blaney;

import java.io.File;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.knime.base.node.preproc.double2int.WarningMessage;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.DataTableSpecCreator;
import org.knime.core.data.container.ColumnRearranger;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.core.util.KNIMETimer;
import org.knime.time.util.DateTimeUtils;
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
	static final String CFGKEY_RUN_NAME = "runName";
	static final String CFGKEY_CLEAR_DATA = "clearData";
	static final String CFGKEY_RUN_COUNT = "runCount";
	static final String CFGKEY_START_TIME = "runStartTime";
	static final String CFGKEY_RUN_DATE = "runDate";
	static final String CFGKEY_RUN_NOTES = "runNotes";
	
	private static final String DEFAULT_RUN_NAME = "Execution";
	private static final boolean DEFAULT_CLEAR_DATA = false;
	private static final String DEFAULT_NOTE = "none";

	static SettingsModelString m_runName = new SettingsModelString(CFGKEY_RUN_NAME, DEFAULT_RUN_NAME);
	static SettingsModelBoolean m_clearData = new SettingsModelBoolean(CFGKEY_CLEAR_DATA, DEFAULT_CLEAR_DATA);
	static SettingsModelString m_runNote = new SettingsModelString(CFGKEY_RUN_NOTES, DEFAULT_NOTE);

    protected SubgroupBenchmarkStartNodeModel() {
        super(1, 1);
    }

    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
            final ExecutionContext exec) throws Exception { 	
    	DataTableSpecCreator tableSpecCreator = new DataTableSpecCreator(inData[IN_PORT].getDataTableSpec());
    	//must be done in execution as opposed to config or other
    	tableSpecCreator.putProperties(propertiesBuilder());
    	BufferedDataTable outTable = exec.createSpecReplacerTable(inData[IN_PORT], tableSpecCreator.createSpec());
    	m_runCount++;
        return new BufferedDataTable[]{outTable};
    }
    
    private Map<String, String> propertiesBuilder(){
    	Map<String, String> properties = new LinkedHashMap<String, String>();
    	properties.put(CFGKEY_START_TIME, Long.toString(System.nanoTime()));
    	properties.put(CFGKEY_RUN_COUNT, Integer.toString(m_runCount));
    	properties.put(CFGKEY_RUN_NAME, m_runName.getStringValue());
    	properties.put(CFGKEY_CLEAR_DATA, Boolean.toString(m_clearData.getBooleanValue()));
    	properties.put(CFGKEY_RUN_NOTES, m_runNote.getStringValue());
    	properties.put(CFGKEY_RUN_DATE, String.valueOf(DateTimeUtils.asLocalDate(Long.toString(System.currentTimeMillis())).get()));
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
    	if(m_runName.getStringValue().trim().length() == 0) {
    		throw new InvalidSettingsException("Please enter a valid name for Row Key seed in the benchmarked output table or leave it blank.");
    	}
    	if(m_clearData.getBooleanValue()) {
    		m_runCount = 1;
    	}
        return new DataTableSpec[]{inSpecs[IN_PORT]};
    }

    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {
    	settings.addInt(CFGKEY_RUN_COUNT, m_runCount);
    	m_runName.saveSettingsTo(settings);
    	m_runNote.saveSettingsTo(settings);
    	//default behavior should probably NOT save the settings for this because 
    	//desired bahavior will probably be one clear and then additional data 
    	//collection until the next clear
//    	m_clearData.saveSettingsTo(settings);
    }

    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
    	try {
    	m_runCount = settings.getInt(CFGKEY_RUN_COUNT);

    	} catch (InvalidSettingsException e) {
    		setWarningMessage("Your benchmark node has not detected previous executions.");
    	}
    	m_runNote.setStringValue(settings.getString(CFGKEY_RUN_NOTES));
    	m_runName.setStringValue(settings.getString(CFGKEY_RUN_NAME));
//    	m_clearData.setBooleanValue(settings.getBoolean(CFGKEY_CLEAR_DATA));
    }

    @Override
    protected void validateSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
    	m_runName.validateSettings(settings);
    	m_clearData.validateSettings(settings);
    	m_runName.validateSettings(settings);
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

