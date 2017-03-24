/*******************************************************************************
 * Copyright (c) 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.ui.internal.copyrightupdater;

import org.eclipse.osgi.util.NLS;

/**
 * Helper class to get NLSed messages.
 * 
 * @since 3.5
 */
final class CopyrightUpdateMessages extends NLS {

	private static final String BUNDLE_NAME= CopyrightUpdateMessages.class.getName();

	private CopyrightUpdateMessages() {
		// Do not instantiate
	}

	public static String CopyrightTabPage_groupName;
	public static String CopyrightTabPage_checkboxText;
	
	public static String UpdateCopyrightFix_searchPattern1_replaceString;
	public static String UpdateCopyrightFix_searchPattern1_searchString;
	public static String UpdateCopyrightFix_searchPattern2_replaceString;
	public static String UpdateCopyrightFix_searchPattern2_searchString;
	public static String UpdateCopyrightFix_searchPattern3_replaceString;
	public static String UpdateCopyrightFix_searchPattern3_searchString;


	static {
		NLS.initializeMessages(BUNDLE_NAME, CopyrightUpdateMessages.class);
	}
}