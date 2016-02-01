/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import org.eclipse.cdt.core.IAddress;
import org.eclipse.cdt.dsf.concurrent.DataRequestMonitor;
import org.eclipse.cdt.dsf.gdb.service.extensions.GDBStack_HEAD;
import org.eclipse.cdt.dsf.service.DsfSession;

public class FrameSpyStackService extends GDBStack_HEAD {
	public FrameSpyStackService(DsfSession session) {
		super(session);
	}
	
	@Override
	public void getFrameData(IFrameDMContext frameDmc, DataRequestMonitor<IFrameDMData> rm) {
		super.getFrameData(frameDmc, new DataRequestMonitor<IFrameDMData>(getExecutor(), rm) {
			@Override
			protected void handleSuccess() {
				IFrameDMData baseData = getData();
				
				rm.done(new IFrameDMData() {
					@Override
					public String getModule() {
						return baseData.getModule();
					}
					
					@Override
					public int getLine() {
						return baseData.getLine();
					}
					
					@Override
					public String getFunction() {
						if (baseData.getFunction().equals("main")) {
							return "entry";
						} else {
							return baseData.getFunction();
						}
					}
					
					@Override
					public String getFile() {
						return baseData.getFile();
					}
					
					@Override
					public int getColumn() {
						return baseData.getColumn();
					}

					@Override
					public IAddress getAddress() {
						return baseData.getAddress();
					}
				});
			}
		});
	}
}
