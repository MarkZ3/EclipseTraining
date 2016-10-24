/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.tracecompass.training.example;

import org.eclipse.tracecompass.statesystem.core.ITmfStateSystemBuilder;
import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.statesystem.AbstractTmfStateProvider;
import org.eclipse.tracecompass.tmf.core.statesystem.ITmfStateProvider;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;

public class ProcessingTimeStateProvider extends AbstractTmfStateProvider {

    public ProcessingTimeStateProvider(ITmfTrace trace) {
        super(trace, "org.eclipse.tracecompass.processing.time.state.provider");
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public ITmfStateProvider getNewInstance() {
        return new ProcessingTimeStateProvider(this.getTrace());
    }

    @Override
    protected void eventHandle(ITmfEvent event) {
        final ITmfStateSystemBuilder stateSystem = getStateSystemBuilder();
        if (stateSystem == null){
            return;
        }

        /**
         * Attribute tree:
         * --------------
         * Requester
         *     |
         *     |-<requester> -> State Value
         *
         * State Value:
         * -----------
         * Use enum Processing state in {@link IEventConstants}
         *
         */
        switch (event.getName()) {
        case IEventConstants.CREATE_EVENT:
            // TODO update state of attribute Requester/requesterString to INITIALIZING
            return;

        case IEventConstants.START_EVENT:
            // TODO update state of attribute Requester/requesterString to PROCESSING
            return;

        case IEventConstants.STOP_EVENT:
            // TODO update state of attribute Requester/requesterString to WAITING
            return;

        case IEventConstants.END_EVENT:
            // TODO update state of attribute Requester/requesterString to null state
            return;

        default:
            return;
        }
    }

}
