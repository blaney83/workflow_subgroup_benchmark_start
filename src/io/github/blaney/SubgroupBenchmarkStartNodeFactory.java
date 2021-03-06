package io.github.blaney;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "SubgroupBenchmarkStart" Node.
 * The starting point for workflow clocking. Passes through data table and starts the clock for a workflow or sub-section of a workflow. When connected to the SubgroupBenchmarkEnd node(s), this node allows tracking time performance between different executions.
 *
 * @author Benjamin Laney
 */
public class SubgroupBenchmarkStartNodeFactory 
        extends NodeFactory<SubgroupBenchmarkStartNodeModel> {

    @Override
    public SubgroupBenchmarkStartNodeModel createNodeModel() {
        return new SubgroupBenchmarkStartNodeModel();
    }

    @Override
    public int getNrNodeViews() {
        return 0;
    }

    @Override
    public NodeView<SubgroupBenchmarkStartNodeModel> createNodeView(final int viewIndex,
            final SubgroupBenchmarkStartNodeModel nodeModel) {
        return null;
    }

    @Override
    public boolean hasDialog() {
        return true;
    }

    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new SubgroupBenchmarkStartNodeDialog();
    }

}

