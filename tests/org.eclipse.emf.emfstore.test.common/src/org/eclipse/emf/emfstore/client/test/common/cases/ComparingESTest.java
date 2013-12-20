/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.cases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.MessageFormat;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.emfstore.client.util.ESVoidCallable;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.junit.After;
import org.junit.Before;

/**
 * @author emueller
 * 
 */
public class ComparingESTest extends ESTest {

	private static final String CLONED_PROJECT_NAME = "ClonedProject"; //$NON-NLS-1$
	private ProjectSpaceBase clonedProjectSpace;
	private boolean isCompareAtEnd;

	public void disableCompareAtEnd() {
		isCompareAtEnd = false;
	}

	public ProjectSpaceBase getClonedProjectSpace() {
		return clonedProjectSpace;
	}

	@Override
	@Before
	public void before() {
		super.before();
		isCompareAtEnd = true;
		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				final WorkspaceBase workspace = (WorkspaceBase) ESWorkspaceProviderImpl.getInstance().getWorkspace()
					.toInternalAPI();
				workspace.cloneProject(CLONED_PROJECT_NAME, getProject());
				clonedProjectSpace = (ProjectSpaceBase) workspace.cloneProject(CLONED_PROJECT_NAME, getProject());
				assertTrue(ModelUtil.areEqual(getProject(), clonedProjectSpace.getProject()));
			}
		});
	}

	/**
	 * Clear all operations from project space.
	 */
	@Override
	public void clearOperations() {
		RunESCommand.run(new Callable<Void>() {
			public Void call() throws Exception {
				if (isCompareAtEnd) {
					clonedProjectSpace.applyOperations(getProjectSpace().getOperations(), false);
					clonedProjectSpace
						.applyOperations(getProjectSpace().getOperationManager().clearOperations(), false);
				} else {
					getProjectSpace().getOperationManager().clearOperations();
				}
				getProjectSpace().getOperations().clear();
				return null;
			}
		});

	}

	@Override
	@After
	public void after() {
		boolean areEqual = false;
		getProjectSpace().save();

		String projectString = StringUtils.EMPTY;
		String clonedProjectString = StringUtils.EMPTY;

		if (isCompareAtEnd) {
			clonedProjectSpace.applyOperations(getProjectSpace().getOperations(), true);

			try {
				projectString = ModelUtil.eObjectToString(getProjectSpace().getProject());
				clonedProjectString = ModelUtil.eObjectToString(clonedProjectSpace.getProject());
				areEqual = ModelUtil.areEqual(getProject(), clonedProjectSpace.getProject());
			} catch (final SerializationException ex) {
				fail(ex.getMessage());
			}
			clonedProjectSpace.save();

			assertTrue(
				MessageFormat.format("Projects are not equal.\n\n{0}\n\n{1}", projectString, clonedProjectString), areEqual); //$NON-NLS-1$
		}
		super.after();
	}

}
