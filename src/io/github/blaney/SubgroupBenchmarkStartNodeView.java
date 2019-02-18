package io.github.blaney;

import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "SubgroupBenchmarkStart" Node.
 * The starting point for workflow clocking. Passes through data table and starts the clock for a workflow or sub-section of a workflow. When connected to the SubgroupBenchmarkEnd node(s), this node allows tracking time performance between different executions.
 *
 * @author Benjamin Laney
 */
public class SubgroupBenchmarkStartNodeView extends NodeView<SubgroupBenchmarkStartNodeModel> {

    protected SubgroupBenchmarkStartNodeView(final SubgroupBenchmarkStartNodeModel nodeModel) {
        super(nodeModel);
    }

    @Override
    protected void modelChanged() {
        SubgroupBenchmarkStartNodeModel nodeModel = 
            (SubgroupBenchmarkStartNodeModel)getNodeModel();
        assert nodeModel != null;        
    }

    @Override
    protected void onClose() {
        //nothing
    }

    @Override
    protected void onOpen() {
        //nothing
    }

}

