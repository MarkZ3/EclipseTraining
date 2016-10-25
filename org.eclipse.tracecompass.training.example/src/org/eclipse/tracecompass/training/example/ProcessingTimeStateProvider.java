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
import org.eclipse.tracecompass.statesystem.core.statevalue.ITmfStateValue;
import org.eclipse.tracecompass.statesystem.core.statevalue.TmfStateValue;
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
        return 5;
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
         *            |---<id> -> State Value
         *                 |---number -> Number Value
         *
         * State Value:
         * -----------
         * Use enum Processing state in {@link IEventConstants}
         *
         * Number Value:
         * ------------
         * Store optional event field "value"
         *
         */
        switch (event.getName()) {
        case IEventConstants.CREATE_EVENT: {
            ITmfStateValue stateValue = TmfStateValue.newValueInt(IEventConstants.ProcessingStates.INITIALIZING.ordinal());
            updateRequesterState(stateSystem, event, stateValue);
            return;
        }

        case IEventConstants.START_EVENT: {
            ITmfStateValue stateValue = TmfStateValue.newValueInt(IEventConstants.ProcessingStates.PROCESSING.ordinal());
            updateRequesterState(stateSystem, event, stateValue);
            return;
        }

        case IEventConstants.STOP_EVENT: {
            ITmfStateValue stateValue = TmfStateValue.newValueInt(IEventConstants.ProcessingStates.WAITING.ordinal());
            updateRequesterState(stateSystem, event, stateValue);
            return;
        }

        case IEventConstants.END_EVENT: {
            ITmfStateValue stateValue = TmfStateValue.nullValue();
            updateRequesterState(stateSystem, event, stateValue);
            return;
        }

        case IEventConstants.PROCESS_INIT_EVENT: {
            ITmfStateValue stateValue = TmfStateValue.newValueInt(IEventConstants.ProcessingStates.INITIALIZING.ordinal());
            updateProcessingState(stateSystem, event, stateValue);
            return;
        }

        case IEventConstants.PROCESS_START_EVENT: {
            ITmfStateValue stateValue = TmfStateValue.newValueInt(IEventConstants.ProcessingStates.PROCESSING.ordinal());
            updateProcessingState(stateSystem, event, stateValue);
            return;
        }

        case IEventConstants.PROCESS_END_EVENT: {
            ITmfStateValue stateValue = TmfStateValue.nullValue();
            updateProcessingState(stateSystem, event, stateValue);
            return;
        }

        default:
            return;
        }
    }

    private static void updateRequesterState(ITmfStateSystemBuilder stateSystem, ITmfEvent event, ITmfStateValue stateValue) {
        // get event field with name
        String requester = event.getContent().getFieldValue(String.class, "requester");
        if (requester == null) {
            return;
        }

        // get quark of attribute for path Requester/requester
        int quark = stateSystem.getQuarkAbsoluteAndAdd("Requester", requester);

        // get time of event
        long t = event.getTimestamp().getValue();

        // apply state change
        stateSystem.modifyAttribute(t, stateValue, quark);
        return;
    }

    private static void updateProcessingState(ITmfStateSystemBuilder stateSystem, ITmfEvent event, ITmfStateValue stateValue) {
        // get event field with name
        String requester = event.getContent().getFieldValue(String.class, "requester");
        if (requester == null) {
            return;
        }

        Long id = event.getContent().getFieldValue(Long.class, "id");
        if (id == null) {
            return;
        }

        // get quark of attribute for path Requester/requester
        int quark = stateSystem.getQuarkAbsoluteAndAdd("Requester", requester, String.valueOf(id));

        // get time of event
        long t = event.getTimestamp().getValue();

        // apply state change
        stateSystem.modifyAttribute(t, stateValue, quark);

        // TODO add optional attribute to store the number (using optional event field "value")
        return;
    }
}
