/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import org.eclipse.cdt.dsf.concurrent.DataRequestMonitor;
import org.eclipse.cdt.dsf.datamodel.IDMContext;
import org.eclipse.cdt.dsf.debug.service.IStack;
import org.eclipse.cdt.dsf.debug.service.IStack.IFrameDMContext;
import org.eclipse.cdt.dsf.debug.service.IStack.IFrameDMData;
import org.eclipse.cdt.dsf.service.DsfServicesTracker;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.ui.DebugUITools;
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
				// Get the debug selection to know what the user is looking at in the Debug view
				IAdaptable context = DebugUITools.getDebugContext();
				if (context == null) {
					return;
				}
				
				// Extract the data model context to use with the DSF services
				IDMContext dmcontext = context.getAdapter(IDMContext.class);
				if (dmcontext == null) {
					// Not dealing with a DSF session
					return;
				}
				
				// Extract DSF session id from the DM context
				String sessionId = dmcontext.getSessionId();
				// Get the full DSF session to have access to the DSF executor
				DsfSession session = DsfSession.getSession(sessionId);
				if (session == null) {
					// It could be that this session is no longer active
					return;
				}
				
				// Get Stack service using a DSF services tracker object
				DsfServicesTracker tracker = new DsfServicesTracker(Activator.getBundleContext(), sessionId);
				IStack stackService = tracker.getService(IStack.class);
				// Don't forgot to dispose of a tracker before it does out of scope
				tracker.dispose();

				if (stackService == null) {
					// Stack service not available.  The debug session
					// is probably terminating.
					return;
				}

				stackService.getTopFrame(dmcontext, new DataRequestMonitor<IFrameDMContext>(session.getExecutor(), null) {
					@Override
					protected void handleSuccess() {
						// The service called 'handleSuccess()' so we know there is no error.
						IFrameDMContext frame = getData();
						// We have a frame context.  It is just a 'pointer' though.
						// We need to get the data associated with it.
						stackService.getFrameData(frame, new DataRequestMonitor<IFrameDMData>(session.getExecutor(), null) {
							@Override
							protected void handleSuccess() {
								// We have the frame data, let's print the method name and line number
								final IFrameDMData frameData = getData();

								Display.getDefault().asyncExec(new Runnable() {
									@Override
									public void run() {
										// Pre-pend the current method:line to the log
										fLogText.setText(
												frameData.getFunction() + ":" + frameData.getLine() + "\n" +
												fLogText.getText());
									}
								});								
							}
						});
					}
				});	
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
