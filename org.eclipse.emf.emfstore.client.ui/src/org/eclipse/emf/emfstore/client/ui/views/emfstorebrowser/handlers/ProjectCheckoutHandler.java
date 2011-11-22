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
package org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecp.common.util.DialogHandler;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.commands.CheckoutCommand;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for a project checkout.
 * 
 * @author Shterev
 */
public class ProjectCheckoutHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		TreeNode node = (TreeNode) obj;
		ProjectInfo projectInfo = (ProjectInfo) node.getValue();
		ServerInfo serverInfo = (ServerInfo) node.getParent().getValue();

		new CheckoutCommand(serverInfo, projectInfo).run();

		// open the navigator if closed
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		String viewId = "org.eclipse.emf.ecp.navigator.viewer";
		try {
			page.showView(viewId);
		} catch (PartInitException e) {
			DialogHandler.showExceptionDialog(e);
		}
		return null;
	}
}
