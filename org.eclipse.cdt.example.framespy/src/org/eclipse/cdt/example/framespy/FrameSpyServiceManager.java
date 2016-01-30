/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

/**
 * This singleton class that starts FrameSpyServices 
 * as DSF sessions are created.  The class does not
 * worry about shutting down the service as it counts
 * on DSF-GDB to do it automatically.
 */
public class FrameSpyServiceManager {

	// Global TODO: Instantiate and initialize a FrameSpyService class
	//              each time a DSF session is started.
	
	private static final FrameSpyServiceManager fInstance = new FrameSpyServiceManager();
	
	// Private constructor for singleton
	private FrameSpyServiceManager() {
	}

	public static void initialize() {
		// TODO: Register with DsfSession.addSessionStartedListener
		//       Have this class implement the correct interface to
		//       be the listener for Session started calls.
	}

	public static void dispose() {
	}

	// TODO: Implement sessionStarted() method inherited from SessionStartedListener
	//       Instantiate and initialize FrameSpyService in this new method.
}
