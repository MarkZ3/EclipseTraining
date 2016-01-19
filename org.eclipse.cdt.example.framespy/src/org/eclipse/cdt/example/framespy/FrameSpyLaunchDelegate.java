/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

public class FrameSpyLaunchDelegate {
	
	// TODO: Base your delegate on GdbLaunchDelegate (extend it)
	
	// TODO: Override getPluginID() and return Activator.PLUGIN_ID;
	
	// TODO: Go to plugin.xml and open Extensions tab.
	//       Fill-in missing parts of the element org.eclipse.cdt.example.frameSpy.launchDelegate 
	//       under the node org.eclipse.debug.core.launchDelegates:
	//          delegate: select the FrameSpyDelegate class
	//          name: Give a name that the user will see (e.g., IMA2l-Chip Local Launcher)
	//          modes: use 'debug' (no quotes) as we only want this for debugging
	//          delegateDescription: Provide an explanation for the user to understand
	//                               what this delegate does (i.e. Local debugging for IMA2l type chips)
	//          type: Press "Browse..." and type *C/C++
	//                Select org.eclipse.cdt.launch.applicationLaunchType
	//                This will assign the new delegate to the existing
	//                "C/C++ Application" launch configuration type.
	//
	// Note: A Launch delegate provides its own UI tabs.  A "Main" tab has already been
	//       defined for you in the Extensions part of plugin.xml.
	//       You would probably want other tabs, but this goes beyond this exercise.

}
