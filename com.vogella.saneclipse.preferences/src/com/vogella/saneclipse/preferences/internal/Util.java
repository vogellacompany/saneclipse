package com.vogella.saneclipse.preferences.internal;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;

public class Util {

	public static IEclipsePreferences getNode(String node) {
		return InstanceScope.INSTANCE.getNode(node);
	}

	public static  void savePrefs(IEclipsePreferences prefs) {
		try {
			prefs.flush();
		} catch (org.osgi.service.prefs.BackingStoreException e) {
			e.printStackTrace();
		}

	}
}
