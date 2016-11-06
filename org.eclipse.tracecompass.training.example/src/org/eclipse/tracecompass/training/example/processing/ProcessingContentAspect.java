/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.tracecompass.training.example.processing;

import java.util.Comparator;

import org.eclipse.tracecompass.segmentstore.core.ISegment;
import org.eclipse.tracecompass.tmf.core.segment.ISegmentAspect;

/**
 * An aspect (column) that describes the content of a single Processing segment.
 */
final class ProcessingContentAspect implements ISegmentAspect {
    public static final ISegmentAspect INSTANCE = new ProcessingContentAspect();

    private ProcessingContentAspect() {
    }

    @Override
    public String getHelpText() {
        return "Content";
    }

    @Override
    public String getName() {
        return "Content";
    }

    @Override
    public Comparator<?> getComparator() {
        return null;
    }

    @Override
    public String resolve(ISegment segment) {
        if (segment instanceof ProcessingSegment) {
            ProcessingSegment procSegment = (ProcessingSegment) segment;
            return "requestor=" + procSegment.getRequestor() + ", id=" + procSegment.getId();
        }
        return EMPTY_STRING;
    }
}
