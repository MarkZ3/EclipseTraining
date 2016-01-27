/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;

public class FrameSpyView extends ViewPart {

	private static final String TOGGLE_STATE_PREF_KEY = "toggle.state";
	private MenuManager fMenuManager;
	private StyledText fLogText;
	private Job fPollingJob;

	public FrameSpyView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());

		fLogText = new StyledText(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		fLogText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		fMenuManager = new MenuManager();
		Menu menu = fMenuManager.createContextMenu(fLogText);
		fLogText.setMenu(menu);
		getViewSite().registerContextMenu(fMenuManager, null);
		
		// Display the new state to the user
		boolean toggledState = getToggledState();
		fLogText.setText(Boolean.toString(toggledState));
		// Create the polling job if the spy is enabled
		if (toggledState) {
			startPollingJob();
		}
	}

	@Override
	public void setFocus() {
		fLogText.setFocus();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		fMenuManager.dispose();
	}

	public boolean getToggledState() {
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		String togglePrefValue = preferences.get(TOGGLE_STATE_PREF_KEY, Boolean.FALSE.toString());
		return Boolean.parseBoolean(togglePrefValue);
	}

	public void setToggledState(boolean newState) {
		boolean oldState = getToggledState();
		if (oldState != newState) {
			// Display the new state to the user
			fLogText.setText(Boolean.toString(newState));

			// Save the toggle state in a preference so that it's remembered
			// next time the view is opened
			IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
			preferences.put(TOGGLE_STATE_PREF_KEY, Boolean.toString(newState));
						
			// Create the polling job if the spy is enabled
			if (newState) {
				startPollingJob();
			} else {
				cancelPollingJob();
			}
		}

	}

	private void startPollingJob() {
		fPollingJob = new Job("Frame Spy Polling Job") {
			int counter = 0;
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// Ignored
				}

				if (monitor.isCanceled()) {
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							setToggledState(false); // small bug: this set state to false when it could have already been put back to true
						}
					});
					// Stop here to cancel the repeating job
					return Status.OK_STATUS;
				}

				doWork();
				
				schedule();

				return Status.OK_STATUS;
			}
			
			private void doWork() {
				// Global TODO: Replace printing the counter with printing <method:line> for the current frame
				//              by doing the steps shown further down.
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						fLogText.setText(Integer.toString(counter));
					}
				});
				counter++;

				// Get the debug selection to know what the user is looking at in the Debug view				
				// TODO: Obtain current debug context using DebugUITools.getDebugContext();

				// Extract the data model context to use with the DSF services
				// TODO: Convert debug context to DSF context using: getAdapter(IDMContext.class)
				
				// Extract DSF session id from the DM context
				// TODO: Get DsfSession using DsfSession.getSession
				//       You can find the session id in the IDMContext

				// Get Stack service using a DSF services tracker object
				// TODO: Create a new DsfServicesTracker (pass in Activator.getBundleContext())
				
				// TODO: Get the IStack service using DsfServicesTracker.getService(IStack.class)

				// TODO: Don't forget to dispose of the tracker before it goes out of scope

				// Get the context for the top frame
				// TODO: Look at IStack.java and find the API to get the top frame
				// TODO: Call that method using the IDMContext you have already.
				//       You can use 'null' for the parentRequest monitor
				//       You can find the DSF Executor using the DsfSession and getExecutor()
				
				// TODO: Override handleSuccess() which is the callback
				//       The result will be in getData()
				
				// We now have a pointer to the top frame, but no other information about it
				// Call the stack service to fetch that information so we can print it
				// TODO: Look at IStack.java and find the API to get the data associated with a frame
				
				// TODO: From the handleSucess() callback extract from the frame data
				//       the 'method name' and the 'line number' and set it as the text of fToggledStateLbl
			}
		};
		fPollingJob.schedule();
	}

	private void cancelPollingJob() {
		if (fPollingJob != null) {
			fPollingJob.cancel();
		}
	}
}
