package io.github.blaney;

import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "SubgroupBenchmarkStart" Node.
 * The starting point for workflow clocking. Passes through data table and starts the clock for a workflow or sub-section of a workflow. When connected to the SubgroupBenchmarkEnd node(s), this node allows tracking time performance between different executions.
 *
 * @author Benjamin Laney
 */
public class SubgroupBenchmarkStartNodeView extends NodeView<SubgroupBenchmarkStartNodeModel> {

    /**
     * Creates a new view.
     * 
     * @param nodeModel The model (class: {@link SubgroupBenchmarkStartNodeModel})
     */
    protected SubgroupBenchmarkStartNodeView(final SubgroupBenchmarkStartNodeModel nodeModel) {
        super(nodeModel);

        // TODO instantiate the components of the view here.

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void modelChanged() {

        // TODO retrieve the new model from your nodemodel and 
        // update the view.
        SubgroupBenchmarkStartNodeModel nodeModel = 
            (SubgroupBenchmarkStartNodeModel)getNodeModel();
        assert nodeModel != null;
        
        // be aware of a possibly not executed nodeModel! The data you retrieve
        // from your nodemodel could be null, emtpy, or invalid in any kind.
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onClose() {
    
        // TODO things to do when closing the view
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onOpen() {

        // TODO things to do when opening the view
    }

}

