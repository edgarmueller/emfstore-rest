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
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.epackages.EPackageRegistryHelper;
import org.eclipse.emf.emfstore.internal.client.ui.epackages.EPackageTreeSelectionDialog;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class UIRegisterEPackageController.
 * 
 * @author Tobias Verhoeven
 */
public class UIRegisterEPackageController extends
		AbstractEMFStoreUIController<Void> {

	private ServerInfo serverInfo;

	/**
	 * Instantiates a new UIRegisterPackageController.
	 * 
	 * @param shell
	 *            shell
	 * @param serverInfo
	 *            the server info
	 */
	public UIRegisterEPackageController(Shell shell, ESServer serverInfo) {
		super(shell);
		this.serverInfo = ((ESServerImpl) serverInfo).getInternalAPIImpl();
	}

	/**
	 * Register a new EPackage which can be selected with a SelectionDialog.
	 * 
	 * @param serverInfo
	 *            server info
	 * @throws ESException
	 *             if any error in the EmfStore occurs
	 */
	public void registerEPackage(ServerInfo serverInfo) throws ESException {
		EPackageTreeSelectionDialog dialog = new EPackageTreeSelectionDialog(
				EPackageRegistryHelper.getAvailablePackages(true));
		dialog.open();
		final EPackage pkg = dialog.getSelectedEPackage();
		if (pkg != null) {
			new ServerCall<Void>(serverInfo.getLastUsersession()) {
				@Override
				protected Void run() throws ESException {
					getConnectionManager()
							.registerEPackage(getSessionId(), pkg);
					return null;
				}
			}.execute();
		}
	}

	@Override
	public Void doRun(IProgressMonitor monitor) {
		try {
			this.registerEPackage(serverInfo);
		} catch (final ESException e) {
			RunInUI.run(new Callable<Void>() {

				public Void call() throws Exception {
					WorkspaceUtil.logException(e.getMessage(), e);
					MessageDialog.openError(getShell(), "Registration failed",
							e.getMessage());
					return null;
				}
			});
		}
		return null;
	}

}