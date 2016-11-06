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
import org.eclipse.tracecompass.tmf.ui.viewers.xycharts.TmfXYChartViewer;
import org.eclipse.tracecompass.tmf.ui.views.TmfChartView;

/**
 * A scatter view (X/Y chart) showing processing latencies.
 */
public class ProcessingLatencyScatterView extends TmfChartView {

    private static final String VIEW_ID = "org.eclipse.tracecompass.training.processing.scatter";

    public ProcessingLatencyScatterView() {
        super(VIEW_ID);
    }

    @Override
    protected TmfXYChartViewer createChartViewer(Composite parent) {
        final String title = "Duration vs Time";
        final String xLabel = "Time axis";
        final String yLabel = "Duration";
        return new ProcessingLatencyScatterGraphViewer(parent, title, xLabel, yLabel);
    }
}
