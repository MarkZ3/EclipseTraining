/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.tracecompass.training.example.processing.views;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.tracecompass.analysis.timing.core.segmentstore.statistics.AbstractSegmentStatisticsAnalysis;
import org.eclipse.tracecompass.analysis.timing.core.segmentstore.statistics.SegmentStoreStatistics;
import org.eclipse.tracecompass.analysis.timing.ui.views.segmentstore.statistics.AbstractSegmentStoreStatisticsViewer;
import org.eclipse.tracecompass.tmf.core.analysis.TmfAbstractAnalysisModule;
import org.eclipse.tracecompass.tmf.ui.viewers.tree.ITmfTreeViewerEntry;
import org.eclipse.tracecompass.tmf.ui.viewers.tree.TmfTreeViewerEntry;

/**
 * A statistics viewer showing processing latencies.
 */
public class ProcessingLatencyStatisticsViewer extends AbstractSegmentStoreStatisticsViewer {

    public ProcessingLatencyStatisticsViewer(Composite parent) {
        super(parent);
    }

    @Override
    protected TmfAbstractAnalysisModule createStatisticsAnalysiModule() {
        // TODO: Return the statistics module (instead of null)
        return null;
    }

    @Override
    protected ITmfTreeViewerEntry updateElements(long start, long end, boolean isSelection) {
        if (isSelection || (start == end)) {
            return null;
        }

        TmfAbstractAnalysisModule analysisModule = getStatisticsAnalysisModule();
        if (getTrace() == null || !(analysisModule instanceof AbstractSegmentStatisticsAnalysis)) {
            return null;
        }

        AbstractSegmentStatisticsAnalysis module = (AbstractSegmentStatisticsAnalysis) analysisModule;
        module.waitForCompletion();

        TmfTreeViewerEntry root = new TmfTreeViewerEntry("");
        final SegmentStoreStatistics entry = module.getTotalStats();
        if (entry != null) {
            List<ITmfTreeViewerEntry> entryList = root.getChildren();

            TmfTreeViewerEntry totalEntry = new SegmentStoreStatisticsEntry("Total", entry);
            entryList.add(totalEntry);

            // This builds the tree structure and the columns in the statistics viewer
            // TODO: - Get the "per segment statistics" from the module
            //       - For each entry in the statistics map
            //           - Add a new SegmentStoreStatisticsEntry as a child of "Total"
        }
        return root;
    }
}
