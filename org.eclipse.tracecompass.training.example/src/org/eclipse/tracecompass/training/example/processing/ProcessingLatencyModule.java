/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.tracecompass.training.example.processing;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.tracecompass.analysis.timing.core.segmentstore.AbstractSegmentStoreAnalysisEventBasedModule;
import org.eclipse.tracecompass.segmentstore.core.ISegment;
import org.eclipse.tracecompass.segmentstore.core.ISegmentStore;
import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.segment.ISegmentAspect;
import org.eclipse.tracecompass.tmf.core.util.Pair;

/**
 * A module that builds segments representing the beginning and end of processing.
 */
public class ProcessingLatencyModule extends AbstractSegmentStoreAnalysisEventBasedModule {
    /**
     * The ID of this analysis
     */
    public static final String ID = "org.eclipse.tracecompass.training.processing.module";

    private static final Collection<ISegmentAspect> PROCESSING_ASPECTS = new ArrayList<>();
    static {
        // TODO: Add Name aspect and Content aspect to PROCESSING_ASPECTS
    }
    private final Map<ProcessingInfoKey, ProcessingInitialInfo> fOngoingProcessingSegments = new HashMap<>();

    public static class ProcessingInfoKey extends Pair<String, String>{
        public ProcessingInfoKey(ITmfEvent event) {
            super(event.getContent().getField("requester").getFormattedValue(), event.getContent().getField("id").getFormattedValue());
        }
    }

    private static class ProcessingInitialInfo {
        public ProcessingInitialInfo(ITmfEvent event) {
            super();
            this.fStart = event.getTimestamp().getValue();
        }
        private long fStart;
    }

    @Override
    protected AbstractSegmentStoreAnalysisRequest createAnalysisRequest(ISegmentStore<ISegment> segmentStore) {
        // TODO: - Create a new AbstractSegmentStoreAnalysisRequest and return it (instead of null)
        //          - Override handleData
        //              - Call processEvent
        return null;
    }

    private void processEvent(ITmfEvent event, ISegmentStore<ISegment> segmentStore) {
        // TODO: - If the event name is equal to constant IEventConstants.PROCESS_START_EVENT, add it do the ongoing segments (fOngoingProcessingSegments)
        //       - otherwise, if the name is IEventConstants.PROCESS_END_EVENT, check if there is a corresponding ProcessingInitialInfo in ongoing segments (fOngoingProcessingSegments)
        //          - Add the segment
    }

    @Override
    protected Object[] readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        return (Object[]) ois.readObject();
    }

    @Override
    public Iterable<ISegmentAspect> getSegmentAspects() {
        return PROCESSING_ASPECTS;
    }
}
