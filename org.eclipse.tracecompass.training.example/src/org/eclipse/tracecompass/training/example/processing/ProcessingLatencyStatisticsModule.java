/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.tracecompass.training.example.processing;

import org.eclipse.tracecompass.analysis.timing.core.segmentstore.ISegmentStoreProvider;
import org.eclipse.tracecompass.analysis.timing.core.segmentstore.statistics.AbstractSegmentStatisticsAnalysis;
import org.eclipse.tracecompass.segmentstore.core.ISegment;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.eclipse.tracecompass.tmf.core.trace.TmfTraceUtils;

/**
 * A module that generates statistics for processing segments.
 */
public class ProcessingLatencyStatisticsModule extends AbstractSegmentStatisticsAnalysis {

    @Override
    protected String getSegmentType(ISegment segment) {
        if (segment instanceof ProcessingSegment) {
            ProcessingSegment processingSegment = (ProcessingSegment) segment;
            return processingSegment.getName();
        }
        return null;
    }

    @Override
    protected ISegmentStoreProvider getSegmentProviderAnalysis(ITmfTrace trace) {
        return TmfTraceUtils.getAnalysisModuleOfClass(trace, ProcessingLatencyModule.class, ProcessingLatencyModule.ID);
    }
}
