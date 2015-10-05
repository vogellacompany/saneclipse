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

import org.eclipse.swt.graphics.FontData;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;

import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.AbstractMultiEditor;
import org.eclipse.ui.part.MultiPageEditorPart;

import org.eclipse.ui.texteditor.AbstractTextEditor;

public abstract class AbstractZoomTextHandler extends AbstractHandler {

	private int fontDiff;

	protected AbstractZoomTextHandler(int fontDiff) {
		this.fontDiff = fontDiff;
	}

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
		PlatformUI.getPreferenceStore().setValue(JFaceResources.TEXT_FONT, PreferenceConverter.getStoredRepresentation(newFontDescriptor.getFontData()));
		return newFontDescriptor;
	}

	private AbstractTextEditor getActiveTextEditor() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return null;
		}
		IPartService partService = activeWorkbenchWindow.getPartService();
		if (partService == null) {
			return null;
		}
		IWorkbenchPart part = partService.getActivePart();
		AbstractTextEditor textEditor = null;
		if (part instanceof AbstractTextEditor) {
			textEditor = (AbstractTextEditor)part;
		} else if ((part instanceof AbstractMultiEditor) && ((AbstractMultiEditor)part).getActiveEditor() instanceof AbstractTextEditor) {
			textEditor = (AbstractTextEditor) ((AbstractMultiEditor)part).getActiveEditor();
		} else if ((part instanceof MultiPageEditorPart) && ((MultiPageEditorPart)part).getSelectedPage() instanceof AbstractTextEditor) {
			textEditor = (AbstractTextEditor) ((MultiPageEditorPart)part).getSelectedPage();
		}
		return textEditor;
	}

	public boolean isEnabled() {
		return getActiveTextEditor() != null;
	}
}