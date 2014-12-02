package com.vogella.saneclipse.preferences.internal;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
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
			configureIde();
			configureWorkbench();
			configureDebug();
			configureEditor();
			configureResourceEncoding();
			configureLineSeparator();
			configureMemoryMonitorActive();
		}
	}

	private void configureDebug() {
		IEclipsePreferences prefs = Util.getNode("org.eclipse.debug.ui");
		prefs.put("org.eclipse.debug.ui.switch_perspective_on_suspend", "always");
		prefs.put("preferredDetailPanes", "DefaultDetailPane:DefaultDetailPane|");
		prefs.put("org.eclipse.debug.ui.save_dirty_editors_before_launch", "always|");
		prefs.put("org.eclipse.debug.ui.UseContextualLaunch", "false|");
		Util.savePrefs(prefs);
	}

	private void configureIde() {
		IEclipsePreferences prefs = Util.getNode("org.eclipse.ui.ide");
		prefs.putBoolean("EXIT_PROMPT_ON_CLOSE_LAST_WINDOW", false);
		prefs.putInt("MAX_RECENT_WORKSPACES", 10);
		Util.savePrefs(prefs);
	}
	private void configureWorkbench() {
		IEclipsePreferences prefs = Util.getNode("org.eclipse.ui.workbench");
		prefs.putBoolean("RUN_IN_BACKGROUND", true);
		Util.savePrefs(prefs);
	}
	
	private void configureEditor() {
		IEclipsePreferences prefs = Util.getNode("org.eclipse.ui.editors");
		prefs.putBoolean("lineNumberRuler", true);
		Util.savePrefs(prefs);
	}

	private void configureMemoryMonitorActive() {
		// Platform.ui settings
		IEclipsePreferences platformuiprefs = Util.getNode("org.eclipse.ui");
		
		platformuiprefs.putBoolean("SHOW_MEMORY_MONITOR", true);
		Util.savePrefs(platformuiprefs);
	}

	private void configureLineSeparator() {
		// Workspace settings
		IEclipsePreferences runtimeprefs = Util.getNode("org.eclipse.core.runtime"); 
		runtimeprefs.put("line.separator", "\n");
		Util.savePrefs(runtimeprefs);
	}

	private void configureResourceEncoding() {
		// Workspace settings
		IEclipsePreferences resourcesprefs = Util.getNode("org.eclipse.core.resources"); 
		resourcesprefs.put("encoding", "UTF-8");
		Util.savePrefs(resourcesprefs);
	}

	private void configureJDT() {
		// JDT settings
		IEclipsePreferences prefs = Util.getNode("org.eclipse.jdt.ui"); // does
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
		Util.savePrefs(prefs);
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

	
}
