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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.tracecompass.statesystem.core.ITmfStateSystem;
import org.eclipse.tracecompass.statesystem.core.StateSystemUtils;
import org.eclipse.tracecompass.statesystem.core.exceptions.AttributeNotFoundException;
import org.eclipse.tracecompass.statesystem.core.exceptions.StateSystemDisposedException;
import org.eclipse.tracecompass.statesystem.core.exceptions.StateValueTypeException;
import org.eclipse.tracecompass.statesystem.core.exceptions.TimeRangeException;
import org.eclipse.tracecompass.statesystem.core.interval.ITmfStateInterval;
import org.eclipse.tracecompass.statesystem.core.statevalue.ITmfStateValue;
import org.eclipse.tracecompass.tmf.core.statesystem.TmfStateSystemAnalysisModule;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.eclipse.tracecompass.tmf.ui.views.timegraph.AbstractTimeGraphView;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.ITimeEvent;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.ITimeGraphEntry;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.TimeEvent;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.TimeGraphEntry;

public class ProcessingStatesView extends AbstractTimeGraphView {

    public static final String ID = "org.eclipse.tracecompass.training.example.processing.states";

    private static String[] FILTER_COLUMNS = { "Entry" };

    public ProcessingStatesView() {
        super(ID, new ProcessingStatesPresentationProvider());
        // Enable entry filtering
        setFilterColumns(FILTER_COLUMNS);
        setFilterLabelProvider(new FilterLabelProvider());
    }

    @Override
    public void createPartControl(Composite parent) {
        super.createPartControl(parent);
    }

    @Override
    protected void buildEntryList(ITmfTrace trace, ITmfTrace parentTrace, IProgressMonitor monitor) {
        final ITmfStateSystem ssq = TmfStateSystemAnalysisModule.getStateSystem(trace, ProcessingTimeAnalysis.ID);
        if (ssq == null) {
            return;
        }
        // wait until state system is built
        ssq.waitUntilBuilt();
        if (monitor.isCanceled()) {
            return;
        }

        // set the start and end time of the in the view
        long start = ssq.getStartTime();
        long end = ssq.getCurrentEndTime() + 1;
        setStartTime(Math.min(getStartTime(), start));
        setEndTime(Math.max(getEndTime(), end));

        // Create a root entry for the trace
        final TraceEntry traceEntry = new TraceEntry(trace.getName(), start, end);

        // Register root entry to view
        addToEntryList(parentTrace, Collections.singletonList(traceEntry));

        // Register root entry to view
        List<Integer> requesterQuarks = ssq.getQuarks("Requester", "*");
        for (Integer requesterQuark : requesterQuarks) {
            String requesterName = ssq.getAttributeName(requesterQuark);
            RequesterEntry reqEntry = new RequesterEntry(requesterName, start, end, ssq, requesterQuark);
            traceEntry.addChild(reqEntry);

            List<Integer> idQuarks = ssq.getQuarks(requesterQuark, "*");
            for (Integer idQuark : idQuarks) {
                String idName = ssq.getAttributeName(idQuark);
                RequesterEntry idEntry = new RequesterEntry(idName, start, end, ssq, idQuark);
                reqEntry.addChild(idEntry);
            }
            buildStatusEvent(reqEntry, monitor, start, end);
        }
        if (parentTrace.equals(getTrace())) {
            refresh();
        }
    }

    @Override
    protected List<ITimeEvent> getEventList(TimeGraphEntry entry, long startTime, long endTime, long resolution, IProgressMonitor monitor) {
        if (!(entry instanceof RequesterEntry)) {
            return Collections.EMPTY_LIST;
        }
        RequesterEntry requesterEntry = (RequesterEntry) entry;
        ITmfStateSystem ssq = requesterEntry.getStateSystem();
        List<ITimeEvent> eventList = null;
        int quark = requesterEntry.getQuark();

        try {
            if (requesterEntry.getQuark() != -1) {
                List<ITmfStateInterval> statusIntervals = StateSystemUtils.queryHistoryRange(ssq, quark, startTime, endTime - 1, resolution, monitor);
                eventList = new ArrayList<>(statusIntervals.size());
                for (ITmfStateInterval statusInterval : statusIntervals) {
                    if (monitor.isCanceled()) {
                        return null;
                    }
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

    /*-------------------------------------------------------------------------
     * Helper functions
     *-------------------------------------------------------------------------*/
    private void buildStatusEvent(RequesterEntry parentEntry, IProgressMonitor monitor, long start, long end) {
        long resolution = getResolution(start, end);
        long startTime = Math.max(start, parentEntry.getStartTime());
        long endTime = Math.min(end + 1, parentEntry.getEndTime());
        List<ITimeEvent> eventList = getEventList(parentEntry, startTime, endTime, resolution, monitor);
        if (monitor.isCanceled()) {
            return;
        }
        parentEntry.setEventList(eventList);
        redraw();

        for (ITimeGraphEntry entry : parentEntry.getChildren()) {
            if (monitor.isCanceled()) {
                return;
            }
            RequesterEntry xmlEntry = (RequesterEntry) entry;
            buildStatusEvent(xmlEntry, monitor, start, end);
        }
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

    private static class FilterLabelProvider extends TreeLabelProvider {

        @Override
        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof ITimeGraphEntry) {
                return ((ITimeGraphEntry) element).getName();
            }
            return ""; //$NON-NLS-1$
        }

    }

    private long getResolution(long start, long end) {
        return Math.max(1, (end - start) / getDisplayWidth());
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
