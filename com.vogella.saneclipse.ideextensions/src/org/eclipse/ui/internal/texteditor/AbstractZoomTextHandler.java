/*******************************************************************************
 * Copyright (c) 2015 Red Hat Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mickael Istria (Red Hat Inc.) - 469918 Zoom In/Out
 *******************************************************************************/
package org.eclipse.ui.internal.texteditor;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.themes.WorkbenchThemeManager;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.part.AbstractMultiEditor;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public abstract class AbstractZoomTextHandler extends AbstractHandler {

	private int fontDiff;
	
	protected AbstractZoomTextHandler(int fontDiff) {
		this.fontDiff = fontDiff;
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AbstractTextEditor textEditor = getActiveTextEditor();
		if (textEditor == null) {
			return null;
		}
		FontRegistry fontRegistry = textEditor.getSite().getWorkbenchWindow().getWorkbench().getThemeManager().getCurrentTheme().getFontRegistry();
		FontData[] initialFontData = fontRegistry.getFontData(JFaceResources.TEXT_FONT);
		int destFontSize = initialFontData[0].getHeight() + this.fontDiff;
		if (destFontSize <= 0) {
			return null;
		}
		FontDescriptor newFontDescriptor = FontDescriptor.createFrom(initialFontData).setHeight(destFontSize);
		fontRegistry.put(JFaceResources.TEXT_FONT, newFontDescriptor.getFontData());
		PrefUtil.getInternalPreferenceStore().setValue(JFaceResources.TEXT_FONT, PreferenceConverter.getStoredRepresentation(newFontDescriptor.getFontData()));
		PrefUtil.savePrefs();
		IEventBroker eventBroker = textEditor.getSite().getWorkbenchWindow().getWorkbench().getService(IEventBroker.class);
		eventBroker.send(WorkbenchThemeManager.Events.THEME_REGISTRY_MODIFIED, null);
		return newFontDescriptor; 
	}
	
	private AbstractTextEditor getActiveTextEditor() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IEditorPart editor = workbench.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		AbstractTextEditor textEditor = null;
		if (editor instanceof AbstractTextEditor) {
			textEditor = (AbstractTextEditor)editor;
		} else if ((editor instanceof AbstractMultiEditor) && ((AbstractMultiEditor)editor).getActiveEditor() instanceof AbstractTextEditor) {
			textEditor = (AbstractTextEditor) ((AbstractMultiEditor)editor).getActiveEditor();
		} else if ((editor instanceof MultiPageEditorPart) && ((MultiPageEditorPart)editor).getSelectedPage() instanceof AbstractTextEditor) {
			textEditor = (AbstractTextEditor) ((MultiPageEditorPart)editor).getSelectedPage();
		}
		return textEditor;
	}
	
	@Override
	public boolean isEnabled() {
		return getActiveTextEditor() != null;
	}

}