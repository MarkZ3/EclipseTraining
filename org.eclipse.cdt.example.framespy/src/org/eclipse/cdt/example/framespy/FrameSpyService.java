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
}
