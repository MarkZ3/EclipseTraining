/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;

public class FrameSpyView extends ViewPart {

	private static final String TOGGLE_STATE_PREF_KEY = "toggle.state";
	private MenuManager fMenuManager;
	private boolean fToggledState;
	private Label fToggledStateLbl;

	public FrameSpyView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		fMenuManager = new MenuManager();
		Menu menu = fMenuManager.createContextMenu(composite);
		composite.setMenu(menu);
		getViewSite().registerContextMenu(fMenuManager, null);
		
		fToggledStateLbl = new Label(composite, SWT.NONE);

		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		String togglePrefValue = preferences.get(TOGGLE_STATE_PREF_KEY, Boolean.FALSE.toString());
		fToggledStateLbl.setText(togglePrefValue);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void dispose() {
		super.dispose();
		fMenuManager.dispose();
	}

	public boolean getToggledState() {
		return fToggledState;
	}

	public void setToggledState(boolean toggledState) {
		fToggledState = toggledState;
		fToggledStateLbl.setText(Boolean.toString(fToggledState));
		// Save the toggle state in a preference so that it's remembered next time the view is opened
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		preferences.put(TOGGLE_STATE_PREF_KEY, Boolean.toString(fToggledState));
	}

}
