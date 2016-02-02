/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

public class FrameSpyFinalLaunchSequence {
	// Global TODO: Extend DSF-GDB last FinalLaunchSequence (i.e. FinalLaunchSequence_7_7)
	//              and add a step that will call FrameSpyService.setVerbose(true);
	//              This new step should be the first step in the FinalLaunchSequence

	// TODO: Have this class extend FinalLaunchSequence_7_7
	//       and create appropriate constructor.
	
	// TODO: Create a new method called stepSetVerbose() by mimicking
	//       the signature of FinalLaunchSequence_7_7#stepSetDPrinfStyle.
	//       Don't forget the @Execute annotation
	
	// TODO: In stepSetVerbose(), get the FrameSpyService using a
	//       DsfServicesTracker.
	//       Call FrameSpyService#setVerbose to enable verbosity in GDB
	
	// TODO: When calling FrameSpyService#setVerbose, in the passed RequestMonitor
	//       override handleComplete() instead of the usual handleSuccess();
	//       in handleComplete() ignore any error and just call (parent)rm.done()
	//       This is avoid preventing the debugger from starting if in the future
	//       -gdb-set verbosity on would fail for any reason (removed by GDB for example)
	
	// TODO: Insert the new stepSetVerbose() step into the overall FinalLaunchSequence.
	//       Override getExecutionOrder() in a similar way as is done in FinalLaunchSequence_7_7
	//       Insert the new step early in the steps; you can probably make it the first step.
	//       You can also follow the trail of FinalLaunchSequence classes to figure out what 
	//       are the order of the steps, to make a more informed choice.
	//       
}

