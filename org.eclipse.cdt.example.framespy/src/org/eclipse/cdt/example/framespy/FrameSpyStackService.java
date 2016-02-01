/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

public class FrameSpyStackService {

	// Global TODO: Replace the "main" function name with "entry" 
	
	// TODO: Have this class extend DSF-GDB's Stack service.
	//       You can find what is the name of the service that implements IStack
	//       in DSF-GDB and extend that class.  Press F4 on IStack.
	//       It is recommended to use the *_HEAD classes if you only care
	//       about supporting the latest GDB version.

	// TODO: Override getFrameData() to return a different IFrameDMData,
	//       in which any "main" method is called "entry"
	//
	//       You will first call super.getFrameData() and store the
	//       returned IFrameDMData in a variable called baseFrame.
	//       You first instinct may be to modify that result and replace
	//       "main" with "entry".  That would be fine, except that
	//       IFrameDMData does not provide any methods to modify it.
	// 
	//       Instead, you can define a *new* class that also implements IFrameDMData.
	//       In each method you must implement, you can return the result
	//       contained in the baseFrame object you created above.
	//       
	//       Finally, in your implementation of IFrameDMData#getFunction()
	//       you will check if baseFrame.getFunction().equals("main") and 
	//       if is, you will instead, return "entry".
	
	// TODO: Call rm.done(new YourFrameData);
	
	// Note that this part of the exercise cannot be tested just yet.  It needs
	// more changes which we will do as follow-ups.
}
