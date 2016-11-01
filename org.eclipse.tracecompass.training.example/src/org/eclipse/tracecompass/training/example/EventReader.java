package org.eclipse.tracecompass.training.example;

import org.eclipse.tracecompass.tmf.core.signal.TmfSignalHandler;
import org.eclipse.tracecompass.tmf.core.signal.TmfSignalManager;
import org.eclipse.tracecompass.tmf.core.signal.TmfTraceOpenedSignal;

public class EventReader {

    public EventReader () {
        TmfSignalManager.register(this);
    }

    @TmfSignalHandler
    public void traceOpened(TmfTraceOpenedSignal signal) {
        // TODO: Create a TmfEventRequest here.
        // Output each event to the console.
        // Output something when request is completed.
    }
}
