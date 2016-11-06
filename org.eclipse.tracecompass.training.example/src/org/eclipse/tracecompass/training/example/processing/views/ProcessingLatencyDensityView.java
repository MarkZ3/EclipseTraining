/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.tracecompass.training.example.processing.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.tracecompass.analysis.timing.ui.views.segmentstore.density.AbstractSegmentStoreDensityView;
import org.eclipse.tracecompass.analysis.timing.ui.views.segmentstore.density.AbstractSegmentStoreDensityViewer;
import org.eclipse.tracecompass.analysis.timing.ui.views.segmentstore.table.AbstractSegmentStoreTableViewer;

/**
 * A density view showing processing latencies.
 */
public class ProcessingLatencyDensityView extends AbstractSegmentStoreDensityView {

    private static final String VIEW_ID = "org.eclipse.tracecompass.training.processing.density";

    public ProcessingLatencyDensityView() {
        super(VIEW_ID);
    }

    /**
     * Creates the "table" part of the view (left part).
     */
    @Override
    protected AbstractSegmentStoreTableViewer createSegmentStoreTableViewer(Composite parent) {
        return new ProcessingLatencyTableViewer(new TableViewer(parent, SWT.FULL_SELECTION | SWT.VIRTUAL));
    }

    /**
     * Creates the "chart" part of the view (right part).
     */
    @Override
    protected AbstractSegmentStoreDensityViewer createSegmentStoreDensityViewer(Composite parent) {
        return new ProcessingLatencyDensityViewer(parent);
    }
}
