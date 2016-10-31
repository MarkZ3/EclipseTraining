/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.tracecompass.training.example;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.StateItem;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.TimeGraphPresentationProvider;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.ITimeEvent;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.NullTimeEvent;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.TimeEvent;
import org.eclipse.tracecompass.training.example.ProcessingStatesView.RequesterEntry;

public class ProcessingStatesPresentationProvider extends TimeGraphPresentationProvider {

    private static final StateItem[] STATE_ITEMS;

    static {
        STATE_ITEMS = new StateItem[IEventConstants.ProcessingStates.values().length];
        STATE_ITEMS[0] = new StateItem(new RGB(0x88, 0x88, 0x88), IEventConstants.ProcessingStates.INITIALIZING.name());
        STATE_ITEMS[1] = new StateItem(new RGB(0xbc, 0xdd, 0x68), IEventConstants.ProcessingStates.PROCESSING.name());
        STATE_ITEMS[2] = new StateItem(new RGB(0xcc, 0xcc, 0xcc), IEventConstants.ProcessingStates.WAITING.name());
    }

    /*
     * Provides name to display in tooltip
     */
    @Override
    public String getStateTypeName() {
        return "Item";
    }

    @Override
    public StateItem[] getStateTable() {
        return STATE_ITEMS;
    }

    @Override
    public int getStateTableIndex(ITimeEvent event) {
        if (event instanceof TimeEvent && ((TimeEvent) event).hasValue()) {
            int status = ((TimeEvent) event).getValue();
            if (status < STATE_ITEMS.length) {
                return status;
            }
        }
        if (event instanceof NullTimeEvent) {
            return INVISIBLE;
        }
        return TRANSPARENT;
    }

    @Override
    public Map<String, String> getEventHoverToolTipInfo(ITimeEvent event) {
        Map<String, String> retMap = new LinkedHashMap<>();
        if (!(event instanceof TimeEvent) || !((TimeEvent) event).hasValue() ||
                !(event.getEntry() instanceof RequesterEntry)) {
            return retMap;
        }

        int status = ((TimeEvent) event).getValue();
        if (status < STATE_ITEMS.length) {
            retMap.put("State", IEventConstants.ProcessingStates.values()[status].toString());
        }

        return retMap;
    }

}
