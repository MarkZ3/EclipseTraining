/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.tracecompass.training.example.processing.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.tracecompass.analysis.timing.ui.views.segmentstore.statistics.AbstractSegmentStoreStatisticsView;
import org.eclipse.tracecompass.analysis.timing.ui.views.segmentstore.statistics.AbstractSegmentStoreStatisticsViewer;

/**
 * A statistics view showing processing latencies.
 */
public class ProcessingLatencyStatisticsView extends AbstractSegmentStoreStatisticsView {

    @Override
    protected AbstractSegmentStoreStatisticsViewer createSegmentStoreStatisticsViewer(Composite parent) {
        return new ProcessingLatencyStatisticsViewer(parent);
    }
}
