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
package org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.provider;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.observers.LoginObserver;
import org.eclipse.emf.emfstore.client.model.observers.LogoutObserver;
import org.eclipse.emf.emfstore.client.ui.Activator;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * @see ILightweightLabelDecorator
 */
public class ESBrowserLabelDecorator extends LabelProvider implements ILightweightLabelDecorator, LoginObserver,
	LogoutObserver {

	/**
	 * {@inheritDoc} Decorates the label of a {@link ServerInfo} object
	 * according to its login state.
	 */
	public void decorate(Object element, IDecoration decoration) {

		if (element instanceof ServerInfo) {
			ServerInfo server = (ServerInfo) element;

			if (server.getLastUsersession() != null && server.getLastUsersession().isLoggedIn()) {
				decoration.addOverlay(Activator.getImageDescriptor("icons/bullet_green.png"), IDecoration.BOTTOM_RIGHT);
			} else {
				decoration
					.addOverlay(Activator.getImageDescriptor("icons/bullet_delete.png"), IDecoration.BOTTOM_RIGHT);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	public void loginCompleted(Usersession session) {
		update();
	}

	public void logoutCompleted(Usersession session) {
		update();
	}

	private void update() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				PlatformUI.getWorkbench().getDecoratorManager()
					.update("org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.LoginDecorator");
			}
		});
	}
}
