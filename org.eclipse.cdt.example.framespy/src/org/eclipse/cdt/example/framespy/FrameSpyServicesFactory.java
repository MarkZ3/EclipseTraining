/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import org.eclipse.cdt.dsf.debug.service.IStack;
import org.eclipse.cdt.dsf.gdb.service.GdbDebugServicesFactory;
import org.eclipse.cdt.dsf.service.DsfSession;

public class FrameSpyServicesFactory extends GdbDebugServicesFactory {
	public FrameSpyServicesFactory(String version) {
		super(version);
	}
	
	@Override
	protected IStack createStackService(DsfSession session) {
		return new FrameSpyStackService(session);
	}
}
