/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import org.eclipse.cdt.dsf.debug.service.IDsfDebugServicesFactory;
import org.eclipse.cdt.dsf.gdb.launching.GdbLaunchDelegate;
import org.eclipse.debug.core.ILaunchConfiguration;

public class FrameSpyLaunchDelegate extends GdbLaunchDelegate {

	@Override
	protected String getPluginID() {
		return Activator.PLUGIN_ID;
	}
	
	@Override
	protected IDsfDebugServicesFactory newServiceFactory(ILaunchConfiguration config, String version) {
		return new FrameSpyServicesFactory(version);
	}
}
