/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import java.util.Map;

import org.eclipse.cdt.dsf.concurrent.RequestMonitorWithProgress;
import org.eclipse.cdt.dsf.concurrent.Sequence;
import org.eclipse.cdt.dsf.gdb.service.extensions.GDBControl_HEAD;
import org.eclipse.cdt.dsf.mi.service.command.CommandFactory;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.debug.core.ILaunchConfiguration;

public class FrameSpyControlService extends GDBControl_HEAD {
    public FrameSpyControlService(DsfSession session, ILaunchConfiguration config, CommandFactory factory) {
    	super(session, config, factory);
    }

    @Override
	protected Sequence getCompleteInitializationSequence(Map<String, Object> attributes, RequestMonitorWithProgress rm) {
		return new FrameSpyFinalLaunchSequence(getSession(), attributes, rm);
	}
}