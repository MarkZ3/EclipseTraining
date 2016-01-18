/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import java.util.Hashtable;

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
}
