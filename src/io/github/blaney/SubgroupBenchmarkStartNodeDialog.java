package io.github.blaney;

import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponent;
import org.knime.core.node.defaultnodesettings.DialogComponentBoolean;
import org.knime.core.node.defaultnodesettings.DialogComponentString;
import org.knime.core.node.port.PortObjectSpec;

/**
 * <code>NodeDialog</code> for the "SubgroupBenchmarkStart" Node.
 * The starting point for workflow clocking. Passes through data table and starts the clock for a workflow or sub-section of a workflow. When connected to the SubgroupBenchmarkEnd node(s), this node allows tracking time performance between different executions.
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author Benjamin Laney
 */
public class SubgroupBenchmarkStartNodeDialog extends DefaultNodeSettingsPane {

    protected SubgroupBenchmarkStartNodeDialog() {
        super();
        
        addDialogComponent(new DialogComponentString(
                SubgroupBenchmarkStartNodeModel.m_runName, 
                "Choose a name for tracking purposes (ex: Test, Execution, 'Your Name', etc.)"));
        
        addDialogComponent(new DialogComponentString(
        		SubgroupBenchmarkStartNodeModel.m_runNote,
        		"Execution annotation."));
        
        addDialogComponent(new DialogComponentBoolean(
        		SubgroupBenchmarkStartNodeModel.m_clearData, 
        		"Clear cached benchmark data on next execution?"));
                    
    }
}

