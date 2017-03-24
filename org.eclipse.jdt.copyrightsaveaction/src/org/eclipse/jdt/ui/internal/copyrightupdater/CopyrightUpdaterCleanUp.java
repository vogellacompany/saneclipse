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

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;

import org.eclipse.jdt.ui.cleanup.CleanUpContext;
import org.eclipse.jdt.ui.cleanup.CleanUpOptions;
import org.eclipse.jdt.ui.cleanup.CleanUpRequirements;
import org.eclipse.jdt.ui.cleanup.ICleanUp;
import org.eclipse.jdt.ui.cleanup.ICleanUpFix;

/**
 * @since 3.4
 */
public class CopyrightUpdaterCleanUp implements ICleanUp {

	public static final String UPDATE_IBM_COPYRIGHT_TO_CURRENT_YEAR= "cleanup.update_ibm_copyright_to_current_year"; //$NON-NLS-1$

	private CleanUpOptions fOptions;

	public CopyrightUpdaterCleanUp() {
	}
	
	/* 
	 * @see org.eclipse.jdt.internal.ui.fix.ICleanUp#getRequirements()
	 */
	public CleanUpRequirements getRequirements() {
		return new CleanUpRequirements(false, false, false, null);
	}
	
	/* 
	 * @see org.eclipse.jdt.internal.ui.fix.ICleanUp#setOptions(org.eclipse.jdt.internal.ui.fix.CleanUpOptions)
	 */
	public void setOptions(CleanUpOptions options) {
		fOptions= options;
	}

	/**
	 * {@inheritDoc}
	 */
	public ICleanUpFix createFix(CleanUpContext context) throws CoreException {
		if (!fOptions.isEnabled(UPDATE_IBM_COPYRIGHT_TO_CURRENT_YEAR))
			return null;
		
		return UpdateCopyrightFix.createCleanUp(context.getCompilationUnit(), true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String[] getStepDescriptions() {
		ArrayList result= new ArrayList();
		
		if (fOptions.isEnabled(UPDATE_IBM_COPYRIGHT_TO_CURRENT_YEAR))
			result.add("Update IBM Copyright to current year"); //$NON-NLS-1$
		
		return (String[]) result.toArray(new String[result.size()]);
	}
	
	/* 
	 * @see org.eclipse.jdt.internal.ui.fix.ICleanUp#checkPostConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RefactoringStatus checkPostConditions(IProgressMonitor monitor) throws CoreException {
		return new RefactoringStatus();
	}
	
	/* 
	 * @see org.eclipse.jdt.internal.ui.fix.ICleanUp#checkPreConditions(org.eclipse.jdt.core.IJavaProject, org.eclipse.jdt.core.ICompilationUnit[], org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RefactoringStatus checkPreConditions(IJavaProject project, ICompilationUnit[] compilationUnits, IProgressMonitor monitor) throws CoreException {
		return new RefactoringStatus();
	}
	
}
