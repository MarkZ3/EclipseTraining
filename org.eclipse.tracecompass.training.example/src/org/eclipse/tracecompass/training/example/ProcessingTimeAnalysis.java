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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.tracecompass.tmf.core.analysis.TmfAbstractAnalysisModule;
import org.eclipse.tracecompass.tmf.core.analysis.requirements.TmfAbstractAnalysisRequirement;
import org.eclipse.tracecompass.tmf.core.analysis.requirements.TmfAbstractAnalysisRequirement.PriorityLevel;
import org.eclipse.tracecompass.tmf.core.analysis.requirements.TmfAnalysisEventRequirement;
import org.eclipse.tracecompass.tmf.core.exceptions.TmfAnalysisException;

import com.google.common.collect.ImmutableSet;

public class ProcessingTimeAnalysis extends TmfAbstractAnalysisModule {

    /** The analysis's requirements. Only set after the trace is set. */
    private Set<TmfAbstractAnalysisRequirement> fAnalysisRequirements;


    public ProcessingTimeAnalysis() {
    }

    @Override
    protected boolean executeAnalysis(IProgressMonitor monitor) throws TmfAnalysisException {
        System.out.println("Executing ProcessingTimeAnalysis...");
        return true;
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

}
