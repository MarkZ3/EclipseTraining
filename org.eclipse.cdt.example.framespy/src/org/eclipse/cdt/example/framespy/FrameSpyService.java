/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

import org.eclipse.cdt.dsf.concurrent.DataRequestMonitor;
import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.cdt.dsf.service.AbstractDsfService;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.osgi.framework.BundleContext;

public class FrameSpyService extends AbstractDsfService {

	public FrameSpyService(DsfSession session) {
		super(session);
	}

	@Override
	protected BundleContext getBundleContext() {
		return Activator.getBundleContext();
	}
	
	@Override
	public void initialize(RequestMonitor rm) {
		super.initialize(
			new RequestMonitor(getExecutor(), rm) {
				@Override
				protected void handleSuccess() {
					// Register when the service is ready to be called
					register(new String[]{FrameSpyService.class.getName()}, new Hashtable<String,String>());
					rm.done();
				}
			});
	}
	
	@Override
	public void shutdown(RequestMonitor rm) {
		// Unregister first, so that no one calls us anymore
		unregister();
		super.shutdown(rm);
	}
	
	public String getLocalTimeOfDayString() {
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	}
	
	// The asynchronous method below is more versatile because it could
	// be used to go ask GDB the time of day on the target.
	// Since sending message to GDB is considered long, we must use an
	// asynchronous method.
	public void getTargetTimeOfDayString(DataRequestMonitor<String> rm) {
		rm.done(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
	}
	
	// Global TODO: Add a method to get the number of arguments for a particular frame.
	//              Call IStack to get the list of arguments for that frame.
	//              Update FrameSpyView to use this info to show
	//                 [time] method:line (# args)
	
	// TODO: Create a new method that can be called to get the number
	//       of arguments of a specified frame.
	//       You will be calling IStack#getArguments which is an asynchronous
	//       API; this means your method must be asynchronous.
	//
	//       Your method should
	//          - return void
	//          - take a IFrameDMContext as a parameter
	//          - take a DataRequestMonitor as a param to 'return' the number of args
	
	// TODO: In that method, call 
	//            IStack#getArguments(IFrameDMContext, DataRequestMonitor<IVariableDMContext[]>);
	//       Create a new DataRequestMonitor to pass to this call.
	
	// TODO: Override the handleSuccess() method of the DataRequestMonitor
	//       you are passing to IStack#getArguments.  In that handleSuccess() use 
	//       getData() to get the list of arguments; then count them and put the
	//       result in the DataRequestMonitor that was passed to your own method.

	// TODO: Go to FrameSpyView.java and use this new API as described in that file.
}
