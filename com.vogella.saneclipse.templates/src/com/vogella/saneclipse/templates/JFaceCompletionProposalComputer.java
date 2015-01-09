package com.vogella.saneclipse.templates;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.text.java.AbstractTemplateCompletionProposalComputer;
import org.eclipse.jdt.internal.ui.text.template.contentassist.TemplateEngine;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.TemplateContextType;

@SuppressWarnings("restriction")
public class JFaceCompletionProposalComputer extends AbstractTemplateCompletionProposalComputer {

	/**
	 * The name of <code>org.eclipse.jface.viewers.Viewer</code> used to detect
	 * if a project uses JFace.
	 */
	private static final String JFace_TYPE_NAME = "org.eclipse.jface.viewers.Viewer"; //$NON-NLS-1$

	/**
	 * Listener that resets the cached java project if its build path changes.
	 */
	private final class BuildPathChangeListener implements IElementChangedListener {

		@Override
		public void elementChanged(ElementChangedEvent event) {
			final IJavaProject javaProject = getCachedJavaProject();
			if (javaProject == null) {
				return;
			}

			final IJavaElementDelta[] children = event.getDelta().getChangedChildren();
			for (int i = 0; i < children.length; i++) {
				final IJavaElementDelta child = children[i];
				if (javaProject.equals(child.getElement())) {
					if (isClasspathChange(child)) {
						setCachedJavaProject(null);
					}
				}
			}
		}

		/**
		 * Does the delta indicate a classpath change?
		 *
		 * @param delta the delta to inspect
		 * @return true if classpath has changed
		 */
		private boolean isClasspathChange(IJavaElementDelta delta) {
			final int flags = delta.getFlags();
			if (isClasspathChangeFlag(flags)) {
				return true;
			}

			if ((flags & IJavaElementDelta.F_CHILDREN) != 0) {
				final IJavaElementDelta[] children = delta.getAffectedChildren();
				for (int i = 0; i < children.length; i++) {
					if (isClasspathChangeFlag(children[i].getFlags())) {
						return true;
					}
				}
			}

			return false;
		}

		/**
		 * Do the flags indicate a classpath change?
		 *
		 * @param flags the flags to inspect
		 * @return true if the flag flags a classpath change
		 */
		private boolean isClasspathChangeFlag(int flags) {
			if ((flags & IJavaElementDelta.F_RESOLVED_CLASSPATH_CHANGED) != 0) {
				return true;
			}

			if ((flags & IJavaElementDelta.F_ADDED_TO_CLASSPATH) != 0) {
				return true;
			}

			if ((flags & IJavaElementDelta.F_REMOVED_FROM_CLASSPATH) != 0) {
				return true;
			}

			if ((flags & IJavaElementDelta.F_ARCHIVE_CONTENT_CHANGED) != 0) {
				return true;
			}

			return false;
		}
	}

	/**
	 * Engine used to compute the proposals for this computer
	 */
	private final TemplateEngine jFaceTemplateEngine;
	private final TemplateEngine jFaceMembersTemplateEngine;
	private final TemplateEngine jFaceStatementsTemplateEngine;

	/**
	 * The Java project of the compilation unit for which a template
	 * engine has been computed last time if any
	 */
	private IJavaProject cachedJavaProject;
	/**
	 * Is org.eclipse.e4.core.services.IDisposable on class path of <code>fJavaProject</code>. Invalid
	 * if <code>fJavaProject</code> is <code>false</code>.
	 */
	private boolean isJFaceInClasspath;

	public JFaceCompletionProposalComputer() {
		final ContextTypeRegistry templateContextRegistry = JavaPlugin.getDefault().getTemplateContextRegistry();
		jFaceTemplateEngine = createTemplateEngine(templateContextRegistry, JFaceContextType.ID_ALL);
		jFaceMembersTemplateEngine = createTemplateEngine(templateContextRegistry, JFaceContextType.ID_MEMBERS);
		jFaceStatementsTemplateEngine = createTemplateEngine(templateContextRegistry, JFaceContextType.ID_STATEMENTS);

		JavaCore.addElementChangedListener(new BuildPathChangeListener());
	}

	private static TemplateEngine createTemplateEngine(ContextTypeRegistry templateContextRegistry, String contextTypeId) {
		final TemplateContextType contextType = templateContextRegistry.getContextType(contextTypeId);
		Assert.isNotNull(contextType);
		return new TemplateEngine(contextType);
	}

	@Override
	protected TemplateEngine computeCompletionEngine(JavaContentAssistInvocationContext context) {
		final ICompilationUnit unit = context.getCompilationUnit();
		if (unit == null) {
			return null;
		}

		final IJavaProject javaProject = unit.getJavaProject();
		if (javaProject == null) {
			return null;
		}

		if (isJFaceOnClasspath(javaProject)) {
			final CompletionContext coreContext = context.getCoreContext();
			if (coreContext != null) {
				final int tokenLocation = coreContext.getTokenLocation();
				if ((tokenLocation & CompletionContext.TL_MEMBER_START) != 0) {
					return jFaceMembersTemplateEngine;
				}
				if ((tokenLocation & CompletionContext.TL_STATEMENT_START) != 0) {
					return jFaceStatementsTemplateEngine;
				}
			}
			return jFaceTemplateEngine;
		}

		return null;
	}

	/**
	 * Tells whether E4 is on the given project's class path.
	 *
	 * @param javaProject the Java project
	 * @return <code>true</code> if the given project's class path
	 */
	private synchronized boolean isJFaceOnClasspath(IJavaProject javaProject) {
		if (!javaProject.equals(cachedJavaProject)) {
			cachedJavaProject = javaProject;
			try {
				final IType type = javaProject.findType(JFace_TYPE_NAME);
				isJFaceInClasspath = type != null;
			} catch (final JavaModelException e) {
				isJFaceInClasspath = false;
			}
		}
		return isJFaceInClasspath;
	}

	/**
	 * Returns the cached Java project.
	 *
	 * @return the cached Java project or <code>null</code> if none
	 */
	private synchronized IJavaProject getCachedJavaProject() {
		return cachedJavaProject;
	}

	/**
	 * Set the cached Java project.
	 *
	 * @param project or <code>null</code> to reset the cache
	 */
	private synchronized void setCachedJavaProject(IJavaProject project) {
		cachedJavaProject = project;
	}

}
