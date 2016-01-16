package org.eclipse.cdt.astview;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.swt.widgets.Display;

/**
 * The activator class controls the plug-in life cycle
 */
public class CTestPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.cdt.astview"; //$NON-NLS-1$

	// The shared instance
	private static CTestPlugin fgDefault;
	
	public CTestPlugin() {
		super();
		fgDefault= this;
	}
	
	
	
	public static CTestPlugin getDefault() {
		return fgDefault;
	}
	
	public static Display getStandardDisplay() {
		Display display= Display.getCurrent();
		if (display == null) {
			display= Display.getDefault();
		}
		return display;		
	}

}
