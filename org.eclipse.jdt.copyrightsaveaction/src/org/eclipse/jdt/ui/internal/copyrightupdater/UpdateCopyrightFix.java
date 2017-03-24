/*******************************************************************************
 * Copyright (c) 2007, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.ui.internal.copyrightupdater;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.refactoring.CompilationUnitChange;
import org.eclipse.jdt.ui.cleanup.ICleanUpFix;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * Updates the IBM copyright header to the current year.
 * <strong>For internal use only, use at our own risk!</strong>
 *
 * @since 3.4
 */
public class UpdateCopyrightFix implements ICleanUpFix {
	
	private static final class SearchReplacePattern {
		private final Pattern fSearchPattern;
		private final String fReplaceString;
		
		public SearchReplacePattern(String searchPattern, String replacePattern) {
			fSearchPattern= Pattern.compile(expandVariables(searchPattern));
			fReplaceString= expandVariables(replacePattern);
		}
		
		public Pattern getSearchPattern() {
			return fSearchPattern;
		}

		public String getReplaceString() {
			return fReplaceString;
		}
	}

	public static final String CURRENT_YEAR= new SimpleDateFormat("yyyy").format(new Date()); //$NON-NLS-1$

	private static final Pattern CURRENT_YEAR_PATTERN= Pattern.compile("\\$\\{current_year\\}"); //$NON-NLS-1$
	
	private static final SearchReplacePattern[] SEARCH_REPLACE_PATTERNS= new SearchReplacePattern[] {
		new SearchReplacePattern(
CopyrightUpdateMessages.UpdateCopyrightFix_searchPattern1_searchString, CopyrightUpdateMessages.UpdateCopyrightFix_searchPattern1_replaceString
		),
		new SearchReplacePattern(
CopyrightUpdateMessages.UpdateCopyrightFix_searchPattern2_searchString, CopyrightUpdateMessages.UpdateCopyrightFix_searchPattern2_replaceString
		),
		new SearchReplacePattern(
CopyrightUpdateMessages.UpdateCopyrightFix_searchPattern3_searchString, CopyrightUpdateMessages.UpdateCopyrightFix_searchPattern3_replaceString
		)
	};
	
	public static ICleanUpFix createCleanUp(ICompilationUnit compilationUnit, boolean updateIbmCopyright) throws CoreException {
		if (!updateIbmCopyright)
			return null;
		
		IBuffer buffer= compilationUnit.getBuffer();
		String contents= buffer.getContents();
		
		for (int i= 0; i < SEARCH_REPLACE_PATTERNS.length; i++) {
			SearchReplacePattern pattern= SEARCH_REPLACE_PATTERNS[i];
			Matcher matcher= pattern.getSearchPattern().matcher(contents);
			if (matcher.find(0)) {
				int start= matcher.start();
				int end= matcher.end();
				String substring= contents.substring(start, end);
				
				matcher= pattern.getSearchPattern().matcher(substring);
				String replacement= matcher.replaceFirst(pattern.getReplaceString());
				if (substring.equals(replacement))
					return null;
				
				ReplaceEdit edit= new ReplaceEdit(start, substring.length(), replacement);
				
				CompilationUnitChange change= new CompilationUnitChange("Update Copyright", compilationUnit); //$NON-NLS-1$
				change.setEdit(edit);
				
				return new UpdateCopyrightFix(change);
			}
		}
		
		return null;
	}
	
	private final CompilationUnitChange fChange;

	protected UpdateCopyrightFix(CompilationUnitChange change) {
		fChange= change;
	}

	/**
	 * {@inheritDoc}
	 */
	public CompilationUnitChange createChange(IProgressMonitor progressMonitor) throws CoreException {
		return fChange;
	}
	
	private static String expandVariables(String string) {
		Matcher matcher= CURRENT_YEAR_PATTERN.matcher(string);
		return matcher.replaceAll(CURRENT_YEAR);
	}

}
