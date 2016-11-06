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
import org.eclipse.tracecompass.analysis.timing.ui.views.segmentstore.table.AbstractSegmentStoreTableView;
import org.eclipse.tracecompass.analysis.timing.ui.views.segmentstore.table.AbstractSegmentStoreTableViewer;

/**
 * A table view showing processing latencies.
 */
public class ProcessingLatencyTableView extends AbstractSegmentStoreTableView {

    @Override
    protected AbstractSegmentStoreTableViewer createSegmentStoreViewer(TableViewer tableViewer) {
        return new ProcessingLatencyTableViewer(tableViewer);
    }
}
