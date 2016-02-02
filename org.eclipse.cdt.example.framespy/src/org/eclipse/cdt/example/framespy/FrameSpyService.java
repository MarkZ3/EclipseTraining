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
import org.eclipse.cdt.dsf.concurrent.IDsfStatusConstants;
import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.cdt.dsf.debug.service.IStack;
import org.eclipse.cdt.dsf.debug.service.IStack.IFrameDMContext;
import org.eclipse.cdt.dsf.debug.service.IStack.IVariableDMContext;
import org.eclipse.cdt.dsf.service.AbstractDsfService;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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
	
	public void getNumberArguments(IFrameDMContext frame, DataRequestMonitor<Integer> rm) {
		IStack stackService = getServicesTracker().getService(IStack.class);
		if (stackService == null) {
    		rm.done(new Status(IStatus.ERROR, Activator.PLUGIN_ID, IDsfStatusConstants.INTERNAL_ERROR, 
    				"Cannot find command control service", null));
    		return;
		}
		
		stackService.getArguments(
				frame, 
				new DataRequestMonitor<IVariableDMContext[]>(getExecutor(), rm) {
			@Override
			protected void handleSuccess() {
				rm.done(getData().length);
			}
		});
	}
	
	// Global TODO: Write a new public method called setVerbose.
	//              This method should send -gdb-set verbose on to GDB.
	//
	// TODO: Think about what type of API you should use for the method.
	//       Remember that sending things to GDB is slow and should not block.

	// TODO: You send commands to GDB with ICommandControlService.queueCommand()
	//       Use getServicesTracker().getService() to fetch the ICommandControl service.
	
	// TODO: Create a new MIGDBSet command with the necessary parameters for it to
	//       be "-gdb-set verbose on" or "-gdb-set verbose off".
	//       You should use ICommandControlService.getContext() as the first parameter;
	//       this context represents GDB itself.
	
	// TODO: Call ICommandControlService.queueCommand() passing it:
	//         1- the MIGDBSet command you created
	//         2- a DataRequestMonitor as it expects
}
