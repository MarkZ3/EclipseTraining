/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.cdt.dsf.service.DsfSession.SessionStartedListener;

/**
 * This singleton class that starts FrameSpyServices 
 * as DSF sessions are created.  The class does not
 * worry about shutting down the service as it counts
 * on DSF-GDB to do it automatically.
 */
public class FrameSpyServiceManager implements SessionStartedListener {

	private static final FrameSpyServiceManager fInstance = new FrameSpyServiceManager();
	
	// Private constructor for singleton
	private FrameSpyServiceManager() {
	}

	public static void initialize() {
		DsfSession.addSessionStartedListener(fInstance);
	}

	public static void dispose() {
		DsfSession.removeSessionStartedListener(fInstance);
	}

	@Override
	public void sessionStarted(DsfSession session) {
		FrameSpyService service = new FrameSpyService(session);
		service.initialize(new RequestMonitor(session.getExecutor(), null));
		
		// No real need to store the service reference ourselves.
		// When the service registers, a reference for it will be kept
		// by DSF
	}
}