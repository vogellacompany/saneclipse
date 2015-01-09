/*******************************************************************************
 * Copyright (c) 2007, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation (adapted from JDT's SWTContextType)
 *******************************************************************************/
package com.vogella.saneclipse.templates;

import org.eclipse.jdt.internal.corext.template.java.AbstractJavaContextType;
import org.eclipse.jdt.internal.corext.template.java.JavaContext;

/**
 * The context type for templates inside e4 code.
 * The same class is used for several context types:
 * <dl>
 * <li>templates for all Java code locations</li>
 * <li>templates for member locations</li>
 * <li>templates for statement locations</li>
 * </dl>
 */
@SuppressWarnings("restriction")
public class JFaceContextType extends AbstractJavaContextType {

	/**
	 * The context type id for templates working on all Java code locations in JFace projects
	 */
	public static final String ID_ALL = "jface"; //$NON-NLS-1$

	/**
	 * The context type id for templates working on member locations in JFace projects
	 */
	public static final String ID_MEMBERS = "jface-members"; //$NON-NLS-1$

	/**
	 * The context type id for templates working on statement locations in JFace projects
	 */
	public static final String ID_STATEMENTS = "jface-statements"; //$NON-NLS-1$

	@Override
	protected void initializeContext(JavaContext context) {
		if (!getId().equals(JFaceContextType.ID_ALL)) { // a specific context must also allow the templates that work
			// everywhere
			context.addCompatibleContextType(JFaceContextType.ID_ALL);
		}
	}
}
