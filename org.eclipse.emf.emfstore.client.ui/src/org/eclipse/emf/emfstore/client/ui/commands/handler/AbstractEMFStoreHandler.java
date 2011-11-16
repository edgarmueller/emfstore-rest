/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecp.common.util.UiUtil;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Abstract base class for EMFStore related handlers.
 * 
 * @author wesendon
 */
public abstract class AbstractEMFStoreHandler extends AbstractHandler {

	private ProjectSpace projectSpace;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		setProjectSpace(UiUtil.getEventElementByClass(event, ProjectSpace.class));
		doExecute();
		return null;
	}

	/**
	 * Executes the handler.
	 * 
	 * @return an arbitrary result object
	 */
	public abstract Object doExecute();

	/**
	 * Returns the {@link ProjectSpace} this handler is executed upon.
	 * 
	 * @return the project space
	 */
	public ProjectSpace getProjectSpace() {
		return projectSpace;
	}

	/**
	 * Sets the {@link ProjectSpace} this handler should be executed upon.
	 * 
	 * @param projectSpace the project space to be used.
	 */
	public void setProjectSpace(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

	/**
	 * Returns the active {@link Shell}.
	 * 
	 * @return the active shell
	 */
	public Shell getShell() {
		return Display.getCurrent().getActiveShell();
	}
}
