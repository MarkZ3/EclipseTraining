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
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.tracecompass.analysis.timing.core.segmentstore.statistics.AbstractSegmentStatisticsAnalysis;
import org.eclipse.tracecompass.analysis.timing.core.segmentstore.statistics.SegmentStoreStatistics;
import org.eclipse.tracecompass.analysis.timing.ui.views.segmentstore.statistics.AbstractSegmentStoreStatisticsViewer;
import org.eclipse.tracecompass.tmf.core.analysis.TmfAbstractAnalysisModule;
import org.eclipse.tracecompass.tmf.ui.viewers.tree.ITmfTreeViewerEntry;
import org.eclipse.tracecompass.tmf.ui.viewers.tree.TmfTreeViewerEntry;
import org.eclipse.tracecompass.training.example.processing.ProcessingLatencyStatisticsModule;

/**
 * A statistics viewer showing processing latencies.
 */
public class ProcessingLatencyStatisticsViewer extends AbstractSegmentStoreStatisticsViewer {

    public ProcessingLatencyStatisticsViewer(Composite parent) {
        super(parent);
    }

    @Override
    protected TmfAbstractAnalysisModule createStatisticsAnalysiModule() {
        return new ProcessingLatencyStatisticsModule();
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

            Map<String, SegmentStoreStatistics> perProcessingStats = module.getPerSegmentTypeStats();
            if (perProcessingStats != null) {
                for (Entry<String, SegmentStoreStatistics> statsEntry : perProcessingStats.entrySet()) {
                    totalEntry.addChild(new SegmentStoreStatisticsEntry(statsEntry.getKey(), statsEntry.getValue()));
                }
            }
        }
        return root;
    }
}
