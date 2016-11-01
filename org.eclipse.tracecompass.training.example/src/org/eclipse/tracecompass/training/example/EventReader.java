package org.eclipse.tracecompass.training.example;

import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.event.TmfEvent;
import org.eclipse.tracecompass.tmf.core.request.ITmfEventRequest;
import org.eclipse.tracecompass.tmf.core.request.TmfEventRequest;
import org.eclipse.tracecompass.tmf.core.signal.TmfSignalHandler;
import org.eclipse.tracecompass.tmf.core.signal.TmfSignalManager;
import org.eclipse.tracecompass.tmf.core.signal.TmfTraceOpenedSignal;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;

public class EventReader {

    public EventReader () {
        TmfSignalManager.register(this);
    }

    @TmfSignalHandler
    public void traceOpened(TmfTraceOpenedSignal signal) {
        ITmfTrace trace = signal.getTrace();
        if (trace != null) {
            trace.sendRequest(new TmfEventRequest(TmfEvent.class,
                    0, ITmfEventRequest.ALL_DATA,
                    ITmfEventRequest.ExecutionType.BACKGROUND) {

                @Override
                public void handleData(ITmfEvent event) {
                    super.handleData(event);
                    System.out.println(event.getTimestamp());
                }

                @Override
                public void handleCompleted() {
                    super.handleCompleted();
                    System.out.println("completed!");
                }
            });
        }
    }
}
