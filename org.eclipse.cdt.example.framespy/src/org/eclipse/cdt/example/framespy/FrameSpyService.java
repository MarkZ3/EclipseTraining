/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

public class FrameSpyService {

	// Global TODO: Make this class into a DSF service that provides 
	//              a synchronous method
	//                   getLocalTimeOfDayString();
	//              which synchronously returns 
	//                   new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	//              and an asynchronous method
	//                   getTargetTimeOfDayString(DataRequestMonitor<> rm);
	//              which should still use (for simplicity)
	//                   new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

	//              Look at any existing DSF-GDB service to guide you.
	
	// TODO: Find the abstract base class to have FrameSpyService extend
    //	     You can find the BundleContext using Activator.getBundleContext()
	
	// TODO: You must call the base class's register() method so the service
	//       can be found by others.
	//       Look at another service to see when and how register() is called,
	//       within the initialization() asynchronous method.
	//       You can use the service's class name as it registered name.
	
	// TODO: Provide the String getLocalTimeOfDayString(); as required above 

    // TODO: Provide an asynchronous getTargetTimeOfDayString(DataRequestMonitor<> rm);
	//       as described above
}
