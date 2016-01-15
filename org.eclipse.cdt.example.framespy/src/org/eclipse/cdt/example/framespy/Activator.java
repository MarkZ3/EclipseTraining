/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.framespy;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.cdt.example.framespy"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	private static BundleContext bundleContext;
	
	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		bundleContext = context;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		bundleContext = null;
	}

	/**
	 * Returns the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

    public static BundleContext getBundleContext() {
        return bundleContext;
    }

}