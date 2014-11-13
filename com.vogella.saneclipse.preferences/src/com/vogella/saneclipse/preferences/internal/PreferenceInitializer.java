package com.vogella.saneclipse.preferences.internal;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.IStartup;

public class PreferenceInitializer implements IStartup {

	@Override
	public void earlyStartup() {
		configureJDT();
		configureEditor();
		configureResourceEncoding();
		configureLineSeparator();
		configureMemoryMonitorActive();
	}

	private void configureEditor() {
		IEclipsePreferences prefs = getNode("org.eclipse.ui.editors");
		prefs.putBoolean("lineNumberRuler", true);
		savePrefs(prefs);
	}

	private void configureMemoryMonitorActive() {
		// Platform.ui settings
		IEclipsePreferences platformuiprefs = InstanceScope.INSTANCE
				.getNode("org.eclipse.ui"); // does all the above behind the
											// scenes

		platformuiprefs.putBoolean("SHOW_MEMORY_MONITOR", true);
		try {
			// prefs are automatically flushed during a plugin's "super.stop()".
			platformuiprefs.flush();
		} catch (org.osgi.service.prefs.BackingStoreException e) {
			e.printStackTrace();
		}
	}

	private void configureLineSeparator() {
		// Workspace settings
		IEclipsePreferences runtimeprefs = InstanceScope.INSTANCE
				.getNode("org.eclipse.core.runtime"); // does all the above
														// behind the scenes

		runtimeprefs.put("line.separator", "\n");
		try {
			// prefs are automatically flushed during a plugin's "super.stop()".
			runtimeprefs.flush();
		} catch (org.osgi.service.prefs.BackingStoreException e) {
			e.printStackTrace();
		}
	}

	private void configureResourceEncoding() {
		// Workspace settings
		IEclipsePreferences resourcesprefs = InstanceScope.INSTANCE
				.getNode("org.eclipse.core.resources"); // does all the above
														// behind the scenes

		resourcesprefs.put("encoding", "UTF-8");
		try {
			// prefs are automatically flushed during a plugin's "super.stop()".
			resourcesprefs.flush();
		} catch (org.osgi.service.prefs.BackingStoreException e) {
			e.printStackTrace();
		}
	}

	private void configureJDT() {
		// JDT settings
		IEclipsePreferences prefs = InstanceScope.INSTANCE
				.getNode("org.eclipse.jdt.ui"); // does all the above behind the
												// scenes

		prefs.putInt("content_assist_autoactivation_delay", 20);
		prefs.put("content_assist_autoactivation_triggers_java",
				".abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_");
		prefs.put("org.eclipse.jdt.ui.typefilter.enabled",
				"java.awt.*;javax.swing.*;");
		prefs.putBoolean("content_assist_insert_completion", false);
//		prefs.putBoolean("enclosingBrackets", true);
		prefs.putBoolean("smart_semicolon", true);
		prefs.putBoolean("smart_opening_brace", true);
		try {
			// prefs are automatically flushed during a plugin's "super.stop()".
			prefs.flush();
		} catch (org.osgi.service.prefs.BackingStoreException e) {
			e.printStackTrace();
		}
	}

	private IEclipsePreferences getNode(String node) {
		// Platform.ui settings
		return InstanceScope.INSTANCE.getNode(node);
	}

	private void savePrefs(IEclipsePreferences prefs) {
		try {
			// prefs are automatically flushed during a plugin's "super.stop()".
			prefs.flush();
		} catch (org.osgi.service.prefs.BackingStoreException e) {
			e.printStackTrace();
		}

	}

}
