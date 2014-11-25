package com.vogella.saneclipse.preferences.internal;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;

public class PreferenceInitializerAddon {

	private boolean preferecesInitialized;

	@Inject
	@Optional
	public void name(@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) Event event) {
		if(!preferecesInitialized) {
			preferecesInitialized = true;
			configureJDT();
			configureDebug();
			configureEditor();
			configureResourceEncoding();
			configureLineSeparator();
			configureMemoryMonitorActive();
		}
	}

	private void configureDebug() {
		IEclipsePreferences prefs = getNode("org.eclipse.debug.ui");
		prefs.put("org.eclipse.debug.ui.switch_perspective_on_suspend", "always");
		prefs.put("preferredDetailPanes", "DefaultDetailPane:DefaultDetailPane|");
		savePrefs(prefs);
	}

	private void configureEditor() {
		IEclipsePreferences prefs = getNode("org.eclipse.ui.editors");
		prefs.putBoolean("lineNumberRuler", true);
		savePrefs(prefs);
	}

	private void configureMemoryMonitorActive() {
		// Platform.ui settings
		IEclipsePreferences platformuiprefs = InstanceScope.INSTANCE.getNode("org.eclipse.ui"); // does
																								// all
																								// the
																								// above
																								// behind
																								// the
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
		IEclipsePreferences runtimeprefs = InstanceScope.INSTANCE.getNode("org.eclipse.core.runtime"); // does
																										// all
																										// the
																										// above
																										// behind
																										// the
																										// scenes

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
		IEclipsePreferences resourcesprefs = InstanceScope.INSTANCE.getNode("org.eclipse.core.resources"); // does
																											// all
																											// the
																											// above
																											// behind
																											// the
																											// scenes

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
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode("org.eclipse.jdt.ui"); // does
																							// all
		prefs.putInt("content_assist_autoactivation_delay", 20);
		// commented out until https://bugs.eclipse.org/bugs/show_bug.cgi?id=453125 is solved
		// prefs.put("content_assist_autoactivation_triggers_java",
		//		".abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_");
		prefs.put("org.eclipse.jdt.ui.typefilter.enabled", "java.awt.*;javax.swing.*;");
		prefs.putBoolean("content_assist_insert_completion", false);
		prefs.putBoolean("enclosingBrackets", true);
		prefs.putBoolean("smart_semicolon", true);
		prefs.putBoolean("smart_opening_brace", true);
		
		configureSaveActions(prefs);
		
		try {
			// prefs are automatically flushed during a plugin's "super.stop()".
			prefs.flush();
		} catch (org.osgi.service.prefs.BackingStoreException e) {
			e.printStackTrace();
		}
	}
	
	private void configureSaveActions(IEclipsePreferences prefs) {
		prefs.putBoolean("editor_save_participant_org.eclipse.jdt.ui.postsavelistener.cleanup", true);
		prefs.putBoolean("sp_cleanup.format_source_code_changes_only", true);
		prefs.putBoolean("sp_cleanup.organize_imports", true);
		prefs.putBoolean("sp_cleanup.on_save_use_additional_actions", true);
		prefs.putBoolean("sp_cleanup.remove_trailing_whitespaces", true);
		prefs.putBoolean("sp_cleanup.use_blocks", true);
		prefs.putBoolean("sp_cleanup.convert_to_enhanced_for_loop", true);
		prefs.putBoolean("sp_cleanup.remove_unnecessary_casts", true);
		prefs.putBoolean("sp_cleanup.remove_unused_imports", true);
		prefs.putBoolean("sp_cleanup.add_missing_annotations", true);
		prefs.putBoolean("sp_cleanup.add_missing_override_annotations", true);
		prefs.putBoolean("sp_cleanup.add_missing_override_annotations_interface_methods", true);
		prefs.putBoolean("sp_cleanup.add_missing_deprecated_annotations", true);
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
