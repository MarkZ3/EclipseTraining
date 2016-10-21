/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.tracecompass.training.example;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.tracecompass.tmf.core.analysis.TmfAbstractAnalysisModule;
import org.eclipse.tracecompass.tmf.core.analysis.requirements.TmfAbstractAnalysisRequirement;
import org.eclipse.tracecompass.tmf.core.exceptions.TmfAnalysisException;

public class ProcessingTimeAnalysis extends TmfAbstractAnalysisModule {

    public static final String CREATE_EVENT = "ust_master:CREATE"; //$NON-NLS-1$
    public static final String START_EVENT = "ust_master:START"; //$NON-NLS-1$
    public static final String STOP_EVENT = "ust_master:STOP"; //$NON-NLS-1$
    public static final String END_EVENT = "ust_master:END"; //$NON-NLS-1$
    public static final String PROCESS_INIT_EVENT = "ust_master:PROCESS_INIT"; //$NON-NLS-1$
    public static final String PROCESS_START_EVENT = "ust_master:PROCESS_START"; //$NON-NLS-1$
    public static final String PROCESS_END_EVENT = "ust_master:PROCESS_END"; //$NON-NLS-1$

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
        Set<TmfAbstractAnalysisRequirement> requirements = new HashSet<>();
        /*
         * TODO:
         * - Create a Collection with event names
         * - Use constants above
         */

        /* TODO:
         * - Create a TmfAnalysiEventRequirement instance
         */

        /*
         * TODO:
         * - Add requirements to the requirements set
         */
        // Return the set (which is an iterable)
        return requirements;
    }
}
