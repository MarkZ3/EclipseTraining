/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.tracecompass.training.example;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.tracecompass.tmf.core.analysis.TmfAbstractAnalysisModule;
import org.eclipse.tracecompass.tmf.core.exceptions.TmfAnalysisException;

public class ProcessingTimeAnalysis extends TmfAbstractAnalysisModule {

    public ProcessingTimeAnalysis() {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected boolean executeAnalysis(IProgressMonitor monitor) throws TmfAnalysisException {
        System.out.println("Executing ProcessingTimeAnalysis...");
        return true;
    }

    @Override
    protected void canceling() {
        // TODO Auto-generated method stub

    }

}
