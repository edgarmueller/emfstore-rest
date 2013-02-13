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
package org.eclipse.emf.emfstore.internal.client.ui.views.emfstorebrowser.provider;

import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.model.observer.ESLoginObserver;
import org.eclipse.emf.emfstore.client.model.observer.ESLogoutObserver;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.ui.Activator;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.widgets.Display;

/**
 * @see ILightweightLabelDecorator
 */
public class ESBrowserLabelDecorator extends LabelProvider implements ILightweightLabelDecorator, ESLoginObserver,
	ESLogoutObserver {

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
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
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
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observer.ESLoginObserver#loginCompleted(org.eclipse.emf.emfstore.internal.client.model.Usersession)
	 */
	public void loginCompleted(ESUsersession session) {
		update(session);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.observer.ESLogoutObserver#logoutCompleted(org.eclipse.emf.emfstore.internal.client.model.Usersession)
	 */
	public void logoutCompleted(ESUsersession session) {
		update(session);
	}

	private void update(final ESUsersession usersession) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				fireLabelProviderChanged(new LabelProviderChangedEvent(ESBrowserLabelDecorator.this, usersession
					.getServer()));
			}
		});
	}
}