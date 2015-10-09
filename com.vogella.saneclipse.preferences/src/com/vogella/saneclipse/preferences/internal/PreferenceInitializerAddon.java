package com.vogella.saneclipse.preferences.internal;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;

public class PreferenceInitializerAddon {

	private boolean preferencesInitialized;

	@Inject
	@Optional
	public void name(@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) Event event) {
		if(!preferencesInitialized) {
			preferencesInitialized = true;
			configureJDTUi();
			configureJDTCore();
			configurePDEUi();
			configureIde();
			configureWorkbench();
			configurePerformanceMonitoring();
			configureDebug();
			configureEditor();
			configureXMLEditor();
			configureMemoryMonitorActive();
		}
	}

	private void configureJDTUi() {
		// JDT settings
		IEclipsePreferences prefs = Util.getNode("org.eclipse.jdt.ui"); // does all
		if(null == prefs) {
			return;
		}
		
		prefs.putInt("content_assist_autoactivation_delay", 0);
		// commented out until https://bugs.eclipse.org/bugs/show_bug.cgi?id=453125 is solved
		// prefs.put("content_assist_autoactivation_triggers_java",
		//		".abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_");
		prefs.put("content_assist_favorite_static_members", "org.hamcrest.Matchers.*;org.junit.Assert.*;org.mockito.Mockito.*");
		prefs.put("org.eclipse.jdt.ui.typefilter.enabled", "java.awt.*;javax.swing.*;");
		prefs.putBoolean("content_assist_insert_completion", false);
		prefs.putBoolean("enclosingBrackets", true);
		prefs.putBoolean("smart_semicolon", true);
		prefs.putBoolean("smart_opening_brace", true);
		prefs.putBoolean("escapeStrings", true);
		prefs.put("org.eclipse.jdt.ui.text.custom_code_templates", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><templates><template autoinsert=\"false\" context=\"catchblock_context\" deleted=\"false\" description=\"Code in new catch blocks\" enabled=\"true\" id=\"org.eclipse.jdt.ui.text.codetemplates.catchblock\" name=\"catchblock\">${exception_var}.printStackTrace();</template><template autoinsert=\"false\" context=\"methodbody_context\" deleted=\"false\" description=\"Code in created method stubs\" enabled=\"true\" id=\"org.eclipse.jdt.ui.text.codetemplates.methodbody\" name=\"methodbody\">${body_statement}</template><template autoinsert=\"false\" context=\"constructorbody_context\" deleted=\"false\" description=\"Code in created constructor stubs\" enabled=\"true\" id=\"org.eclipse.jdt.ui.text.codetemplates.constructorbody\" name=\"constructorbody\">${body_statement}</template></templates>");
		
		configureSaveActions(prefs);
		Util.savePrefs(prefs);
	}

	private void configureJDTCore() {
		// JDT settings
		IEclipsePreferences prefs = Util.getNode("org.eclipse.jdt.core"); // does all
		if(null == prefs) {
			return;
		}
		
		prefs.put("org.eclipse.jdt.core.compiler.problem.noEffectAssignment", "error");
		prefs.put("org.eclipse.jdt.core.compiler.problem.missingOverrideAnnotation", "warning");
		prefs.put("org.eclipse.jdt.core.compiler.problem.comparingIdentical", "error");
		prefs.put("org.eclipse.jdt.core.compiler.problem.missingHashCodeMethod", "warning");
		prefs.put("org.eclipse.jdt.core.compiler.problem.potentiallyUnclosedCloseable", "warning");
		prefs.put("org.eclipse.jdt.core.compiler.problem.redundantSpecificationOfTypeArguments", "warning");
		
		configureSaveActions(prefs);
		Util.savePrefs(prefs);
	}

	private void configurePDEUi() {
		IEclipsePreferences prefs = Util.getNode("org.eclipse.pde.ui");
		if(null == prefs) {
			return;
		}
		
		prefs.putBoolean("Preferences.MainPage.addToJavaSearch", true);
	}

	private void configureDebug() {
		IEclipsePreferences prefs = Util.getNode("org.eclipse.debug.ui");
		if(null == prefs) {
			return;
		}
		
		prefs.put("org.eclipse.debug.ui.switch_perspective_on_suspend", "always");
		prefs.put("preferredDetailPanes", "DefaultDetailPane:DefaultDetailPane|");
		prefs.put("org.eclipse.debug.ui.save_dirty_editors_before_launch", "always|");
		prefs.put("org.eclipse.debug.ui.UseContextualLaunch", "false|");
		Util.savePrefs(prefs);
	}

	private void configureIde() {
		IEclipsePreferences prefs = Util.getNode("org.eclipse.ui.ide");
		if(null == prefs) {
			return;
		}
		
		prefs.putBoolean("EXIT_PROMPT_ON_CLOSE_LAST_WINDOW", false);
		prefs.putInt("MAX_RECENT_WORKSPACES", 10);
		Util.savePrefs(prefs);
	}
	
	private void configureWorkbench() {
		IEclipsePreferences prefs = Util.getNode("org.eclipse.ui.workbench");
		if(null == prefs) {
			return;
		}
		
		prefs.putBoolean("RUN_IN_BACKGROUND", true);
		prefs.put("PLUGINS_NOT_ACTIVATED_ON_STARTUP", "org.eclipse.equinox.p2.ui.sdk.scheduler;org.eclipse.m2e.discovery;");
		Util.savePrefs(prefs);
	}
	
	private void configurePerformanceMonitoring() {
		IEclipsePreferences prefs = Util.getNode("org.eclipse.ui.monitoring");
		if(null == prefs) {
			return;
		}
		
		prefs.putBoolean("monitoring_enabled", true);
		prefs.putInt("long_event_error_threshold", 800);
		Util.savePrefs(prefs);
	}
	
	private void configureEditor() {
		IEclipsePreferences prefs = Util.getNode("org.eclipse.ui.editors");
		if(null == prefs) {
			return;
		}
		
		prefs.putBoolean("lineNumberRuler", true);
		Util.savePrefs(prefs);
	}
	
	private void configureXMLEditor() {
		IEclipsePreferences prefs = Util.getNode("org.eclipse.wst.xml.core");
		if(null == prefs) {
			return;
		}
		
		prefs.putInt("lineWidth", 120);
		Util.savePrefs(prefs);
	}

	private void configureMemoryMonitorActive() {
		// Platform.ui settings
		IEclipsePreferences platformuiprefs = Util.getNode("org.eclipse.ui");
		if(null == platformuiprefs) {
			return;
		}
		
		platformuiprefs.putBoolean("SHOW_MEMORY_MONITOR", true);
		Util.savePrefs(platformuiprefs);
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
