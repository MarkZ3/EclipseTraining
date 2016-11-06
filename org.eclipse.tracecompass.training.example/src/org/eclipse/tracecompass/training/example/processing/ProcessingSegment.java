/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.tracecompass.training.example.processing;

import org.eclipse.tracecompass.segmentstore.core.BasicSegment;

/**
 * A segment that represents the beginning and end of processing for a requestor.
 */
public final class ProcessingSegment extends BasicSegment {

    private static final long serialVersionUID = -1043868202494858104L;
    private String fName;
    private String fId;
    private String fRequestor;

    public ProcessingSegment(long start, long end, String name, String id, String requestor) {
        super(start, end);
        this.fName = name;
        this.fId = id;
        this.fRequestor = requestor;
    }

    public String getId() {
        return fId;
    }

    public String getRequestor() {
        return fRequestor;
    }

    public String getName() {
        return fName;
    }
}
