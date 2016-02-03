/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.cdt.dsf.concurrent.RequestMonitorWithProgress;
import org.eclipse.cdt.dsf.gdb.launching.FinalLaunchSequence_7_7;
import org.eclipse.cdt.dsf.service.DsfServicesTracker;
import org.eclipse.cdt.dsf.service.DsfSession;

public class FrameSpyFinalLaunchSequence extends FinalLaunchSequence_7_7 {

    public FrameSpyFinalLaunchSequence(DsfSession session, Map<String, Object> attributes, RequestMonitorWithProgress rm) {
        super(session, attributes, rm);
    }

    @Override
    protected String[] getExecutionOrder(String group) {
        if (GROUP_TOP_LEVEL.equals(group)) {
            // Initialize the list with the base class' steps
            // We need to create a list that we can modify, which is why we create our own ArrayList.
			List<String> orderList = new ArrayList<String>(Arrays.asList(super.getExecutionOrder(GROUP_TOP_LEVEL)));

            // Add the step to enable verbose as early as possible. However, let's wait until
			// we've fetched the gdb version to avoid other printouts before those.
            orderList.add(orderList.indexOf("stepGDBVersion")+1, "stepSetVerbose"); //$NON-NLS-1
            
            return orderList.toArray(new String[orderList.size()]);
        }

        return null;
    }
    
    @Execute
    public void stepSetVerbose(final RequestMonitor rm) {
        DsfServicesTracker tracker = new DsfServicesTracker(Activator.getBundleContext(), getSession().getId());
        FrameSpyService service = tracker.getService(FrameSpyService.class);        
        tracker.dispose();
        service.setVerbose(true, new RequestMonitor(service.getExecutor(), rm) {
            @Override
            protected void handleCompleted() {
                // Accept errors by overriding handleCompleted() instead of handleSuccess()
                rm.done();
            }
        });
    }
}

