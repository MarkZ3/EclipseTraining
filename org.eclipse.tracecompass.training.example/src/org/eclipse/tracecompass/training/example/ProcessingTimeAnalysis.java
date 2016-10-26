/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.tracecompass.training.example;

import java.util.Set;

import org.eclipse.tracecompass.tmf.core.analysis.requirements.TmfAbstractAnalysisRequirement;
import org.eclipse.tracecompass.tmf.core.analysis.requirements.TmfAbstractAnalysisRequirement.PriorityLevel;
import org.eclipse.tracecompass.tmf.core.analysis.requirements.TmfAnalysisEventRequirement;
import org.eclipse.tracecompass.tmf.core.statesystem.ITmfStateProvider;
import org.eclipse.tracecompass.tmf.core.statesystem.TmfStateSystemAnalysisModule;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;

import com.google.common.collect.ImmutableSet;

public class ProcessingTimeAnalysis extends TmfStateSystemAnalysisModule {

    /** The analysi module ID (has to match the one in the plugin.xml) */
    public static final String ID = "org.eclipse.tracecompass.training.example.processing.module";

    /** The analysis's requirements. Only set after the trace is set. */
    private Set<TmfAbstractAnalysisRequirement> fAnalysisRequirements;


    public ProcessingTimeAnalysis() {
    }

    @Override
    protected void canceling() {
    }

    @Override
    public Iterable<TmfAbstractAnalysisRequirement> getAnalysisRequirements() {
        Set<TmfAbstractAnalysisRequirement> requirements = fAnalysisRequirements;
        if (requirements == null) {
            Set<String> requiredEvents = ImmutableSet.of(
                    IEventConstants.CREATE_EVENT,
                    IEventConstants.START_EVENT,
                    IEventConstants.STOP_EVENT,
                    IEventConstants.END_EVENT,
                    IEventConstants.PROCESS_INIT_EVENT,
                    IEventConstants.PROCESS_START_EVENT,
                    IEventConstants.PROCESS_END_EVENT
                    );
            /* Initialize the requirements for the analysis: events */
            TmfAbstractAnalysisRequirement eventsReq = new TmfAnalysisEventRequirement(requiredEvents, PriorityLevel.MANDATORY);
            requirements = ImmutableSet.of(eventsReq);
            fAnalysisRequirements = requirements;
        }
        return requirements;
    }

    @Override
    protected ITmfStateProvider createStateProvider() {
        ITmfTrace trace = getTrace();
        if (trace == null) {
            throw new IllegalStateException();
        }
        return new ProcessingTimeStateProvider(trace);
    }
}
