/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.tracecompass.training.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.tracecompass.statesystem.core.ITmfStateSystem;
import org.eclipse.tracecompass.statesystem.core.StateSystemUtils;
import org.eclipse.tracecompass.statesystem.core.exceptions.AttributeNotFoundException;
import org.eclipse.tracecompass.statesystem.core.exceptions.StateSystemDisposedException;
import org.eclipse.tracecompass.statesystem.core.exceptions.StateValueTypeException;
import org.eclipse.tracecompass.statesystem.core.exceptions.TimeRangeException;
import org.eclipse.tracecompass.statesystem.core.interval.ITmfStateInterval;
import org.eclipse.tracecompass.statesystem.core.statevalue.ITmfStateValue;
import org.eclipse.tracecompass.tmf.core.signal.TmfSignalHandler;
import org.eclipse.tracecompass.tmf.core.signal.TmfTraceSelectedSignal;
import org.eclipse.tracecompass.tmf.core.statesystem.TmfStateSystemAnalysisModule;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.eclipse.tracecompass.tmf.core.trace.TmfTraceManager;
import org.eclipse.tracecompass.tmf.ui.views.TmfView;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.TimeGraphViewer;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.ITimeEvent;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.ITimeGraphEntry;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.TimeEvent;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.TimeGraphEntry;

public class ProcessingStatesView extends TmfView {

    /** The time graph viewer */
    private TimeGraphViewer fTimeGraphViewer;

    /** The selected trace */
    private ITmfTrace fTrace;

    public ProcessingStatesView() {
        super("ProvessingStates");
    }

    @Override
    public void createPartControl(Composite parent) {
        // Create Time Graph Viewer
        fTimeGraphViewer = new TimeGraphViewer(parent, SWT.NONE);
        fTimeGraphViewer.setTimeGraphProvider(new ProcessingStatesPresentationProvider());

        // Select trace if it is already open when opening the view
        ITmfTrace trace = TmfTraceManager.getInstance().getActiveTrace();
        if (trace != null) {
            traceSelected(new TmfTraceSelectedSignal(this, trace));
        }
    }

    @Override
    public void setFocus() {
        fTimeGraphViewer.setFocus();
    }

    @TmfSignalHandler
    public void traceSelected(final TmfTraceSelectedSignal signal) {
        if (signal.getTrace() == fTrace) {
            return;
        }
        fTrace = signal.getTrace();
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                /*
                 * Signal might be sent from non-UI thread
                 */
                fillTimeGraph();
            }
        });
    }

    private void fillTimeGraph() {
        ITmfTrace trace = fTrace;
        if (trace == null) {
            return;
        }

        // get relevant state system
        final ITmfStateSystem ssq = TmfStateSystemAnalysisModule.getStateSystem(trace, ProcessingTimeAnalysis.ID);
        if (ssq == null) {
            return;
        }

        // make sure that it is fully build
        ssq.waitUntilBuilt();

        // set the start and end time of the in the view
        long start = ssq.getStartTime();
        long end = ssq.getCurrentEndTime() + 1;

        // Create a root entry for the trace
        final TraceEntry traceEntry = new TraceEntry(trace.getName(), start, end);
        /*
         * Get the quarks of all children of attribute "Requester".
         */
        List<Integer> requesterQuarks = ssq.getQuarks("Requester", "*");
        for (Integer requesterQuark : requesterQuarks) {
             String requesterName = ssq.getAttributeName(requesterQuark);
             // Create RequesterEntry and add it as child to the trace entry
             RequesterEntry reqEntry = new RequesterEntry(requesterName, start, end, ssq, requesterQuark);
             traceEntry.addChild(reqEntry);

             // Get all time events for this entry
             List<ITimeEvent> eventList = getEventList(reqEntry, start, end);
             reqEntry.setEventList(eventList);

             List<Integer> idQuarks = ssq.getQuarks(requesterQuark, "*");
             for (Integer idQuark : idQuarks) {
                 String idName = ssq.getAttributeName(idQuark);
                 RequesterEntry idEntry = new RequesterEntry(idName, start, end, ssq, idQuark);
                 reqEntry.addChild(idEntry);

                 // Get all time events for this entry
                 eventList = getEventList(idEntry, start, end);
                 idEntry.setEventList(eventList);
             }
        }

        // Store entry list into TimeGraphViewer
        List<ITimeGraphEntry> entryList = new ArrayList<>();
        entryList.add(traceEntry);
        fTimeGraphViewer.setInput(entryList);
        fTimeGraphViewer.refresh();
    }

    private static List<ITimeEvent> getEventList(TimeGraphEntry entry, long startTime, long endTime) {
        if (!(entry instanceof RequesterEntry)) {
            return Collections.EMPTY_LIST;
        }
        RequesterEntry requesterEntry = (RequesterEntry) entry;
        ITmfStateSystem ssq = requesterEntry.getStateSystem();
        List<ITimeEvent> eventList = null;
        int quark = requesterEntry.getQuark();

        try {
            if (requesterEntry.getQuark() != -1) {
                List<ITmfStateInterval> statusIntervals = StateSystemUtils.queryHistoryRange(ssq, quark, startTime, endTime - 1);
                eventList = new ArrayList<>(statusIntervals.size());
                for (ITmfStateInterval statusInterval : statusIntervals) {
                    int status = getStatusFromInterval(statusInterval);
                    long time = statusInterval.getStartTime();
                    long duration = statusInterval.getEndTime() - time + 1;
                    if (!statusInterval.getStateValue().isNull()) {
                        eventList.add(new TimeEvent(entry, time, duration, status));
                    }
                }
            }
        } catch (AttributeNotFoundException | TimeRangeException | StateValueTypeException | StateSystemDisposedException e) {
            /* Ignored */
        }
        return eventList;
    }


    private static class TraceEntry extends TimeGraphEntry {
        public TraceEntry(String name, long startTime, long endTime) {
            super(name, startTime, endTime);
        }

        @Override
        public boolean hasTimeEvents() {
            return false;
        }
    }

    public static class RequesterEntry extends TimeGraphEntry {
        private int fQuark;
        ITmfStateSystem fSsq;
        public RequesterEntry(String name, long startTime, long endTime, ITmfStateSystem ssq, int quark) {
            super(name, startTime, endTime);
            fSsq = ssq;
            fQuark = quark;
        }
        public RequesterEntry(String name, long startTime, long endTime, ITmfStateSystem ssq) {
            this(name, startTime, endTime, ssq, -1);
        }

        public int getQuark() {
            return fQuark;
        }

        public ITmfStateSystem getStateSystem() {
            return fSsq;
        }
    }

    private static int getStatusFromInterval(ITmfStateInterval statusInterval) {
        ITmfStateValue stateValue = statusInterval.getStateValue();
        int status = -1;
        switch (stateValue.getType()) {
        case INTEGER:
        case NULL:
            status = stateValue.unboxInt();
            break;
        case LONG:
            status = (int) stateValue.unboxLong();
            break;
        case DOUBLE:
            status = (int) stateValue.unboxDouble();
            break;
        case STRING:
        case CUSTOM:
        default:
            break;
        }
        return status;
    }

}
