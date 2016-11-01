/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.tracecompass.training.example;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ProcessingStatesView extends ViewPart {

    public ProcessingStatesView() {
    }

    @Override
    public void createPartControl(Composite parent) {
        /*
         *  TODO:
         *  - Update plugin.xml with manifest editor
         *  - Goto to Extension tab
         *  - click on "Add"
         *      - select org.eclipse.ui.views and click Ok
         *   - right mouse click on org.eclipse.ui.views -> New -> view
         *      - fill-in:
         *        id = org.eclipse.tracecompass.training.example.processing.states
         *        name = Processing States
         *        click "Browse..." and find ProcessinStatesView
         *
         *  - Select org.eclipse.linuxtools.tmf.core.analysis and right mouse click-> New -> output
         *  - fill-in:
         *      - class = org.eclipse.tracecompass.tmf.ui.analysis.TmfAnalysisViewOutput
         *      - id = org.eclipse.tracecompass.training.example.processing.states
         *  - Select TmfAnalysisViewOutput and right mouse click -> New -> analysisModuleClass
         *  - Click "Browse..."
         *  - Find ProcessingTimeAnalysis
         *  - click on OK
         */
    }

    @Override
    public void setFocus() {
    }

}
