/*******************************************************************************
 * Copyright (c) 2007, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.ui.internal.copyrightupdater;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import org.eclipse.jdt.ui.cleanup.CleanUpOptions;
import org.eclipse.jdt.ui.cleanup.ICleanUpConfigurationUI;

public class CopyrightTabPage implements ICleanUpConfigurationUI {

	public static final String ID= "org.eclipse.jdt.ui.cleanup.tabpage.copyright"; //$NON-NLS-1$
	
	private CleanUpOptions fOptions;

	public CopyrightTabPage() {
		super();
	}
	
	/*
	 * @see org.eclipse.jdt.internal.ui.preferences.cleanup.ICleanUpTabPage#setOptions(org.eclipse.jdt.internal.ui.fix.CleanUpOptions)
	 */
	public void setOptions(CleanUpOptions options) {
		fOptions= options;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.preferences.cleanup.ICleanUpTabPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public Composite createContents(Composite parent) {
		Composite result= new Composite(parent, SWT.NONE);
		result.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout= new GridLayout(1, false);
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		result.setLayout(layout);

		
		Group group= new Group(result, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(1, false));
		group.setText(CopyrightUpdateMessages.CopyrightTabPage_groupName);
		
		final Button updateCheckbox= new Button(group, SWT.CHECK);
		updateCheckbox.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		updateCheckbox.setText(CopyrightUpdateMessages.CopyrightTabPage_checkboxText);
		updateCheckbox.setSelection(fOptions.isEnabled(CopyrightUpdaterCleanUp.UPDATE_IBM_COPYRIGHT_TO_CURRENT_YEAR));
		updateCheckbox.addSelectionListener(new SelectionAdapter() {
			/*
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				fOptions.setOption(CopyrightUpdaterCleanUp.UPDATE_IBM_COPYRIGHT_TO_CURRENT_YEAR, updateCheckbox.getSelection() ? CleanUpOptions.TRUE : CleanUpOptions.FALSE);
			}
		});
		
		return result;
	}
	
	/*
	 * @see org.eclipse.jdt.internal.ui.preferences.cleanup.ICleanUpTabPage#getCleanUpCount()
	 */
	public int getCleanUpCount() {
		return 1;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.preferences.cleanup.ICleanUpTabPage#getSelectedCleanUpCount()
	 */
	public int getSelectedCleanUpCount() {
		return fOptions.isEnabled(CopyrightUpdaterCleanUp.UPDATE_IBM_COPYRIGHT_TO_CURRENT_YEAR) ? 1 : 0;
	}
	
	/*
	 * @see org.eclipse.jdt.internal.ui.preferences.cleanup.ICleanUpTabPage#getPreview()
	 */
	/**
	 * {@inheritDoc}
	 */
	public String getPreview() {
		StringBuffer buf= new StringBuffer();
		
		buf.append("/*******************************************************************************\n"); //$NON-NLS-1$
		if (fOptions.isEnabled(CopyrightUpdaterCleanUp.UPDATE_IBM_COPYRIGHT_TO_CURRENT_YEAR)) {
			buf.append(" * Copyright (c) 2005, ").append(UpdateCopyrightFix.CURRENT_YEAR).append(" IBM Corporation and others.\n"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			buf.append(" * Copyright (c) 2005 IBM Corporation and others.\n"); //$NON-NLS-1$
		}
		buf.append(" * All rights reserved. This  program and the accompanying materials\n"); //$NON-NLS-1$
		buf.append(" * are made available under the terms of the Eclipse Public License v1.0\n"); //$NON-NLS-1$
		buf.append(" * which accompanies this distribution, and is available at\n"); //$NON-NLS-1$
		buf.append(" * http://www.eclipse.org/legal/epl-v10.html\n"); //$NON-NLS-1$
		buf.append(" *\n"); //$NON-NLS-1$
		buf.append(" * Contributors:\n"); //$NON-NLS-1$
		buf.append(" *     IBM Corporation - initial API and implementation\n"); //$NON-NLS-1$
		buf.append(" *******************************************************************************/\n"); //$NON-NLS-1$
		
		return buf.toString();
	}

}
