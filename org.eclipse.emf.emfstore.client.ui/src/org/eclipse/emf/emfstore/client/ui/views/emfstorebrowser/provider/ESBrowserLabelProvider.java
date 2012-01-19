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

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.Activator;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for the EMFStore Browser View.
 * 
 * @author emueller
 * @author ovonwesend
 */
public class ESBrowserLabelProvider extends AdapterFactoryLabelProvider {

	/**
	 * Default constructor.
	 */
	public ESBrowserLabelProvider() {
		super(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object obj) {
		Object element = obj;
		if (element instanceof ServerInfo) {
			ServerInfo serverInfo = (ServerInfo) element;
			StringBuilder builder = new StringBuilder();
			builder.append(serverInfo.getUrl());
			builder.append(" [");
			builder.append(serverInfo.getName());
			builder.append("]");
			return builder.toString();
		} else if (element instanceof ProjectInfo) {
			ProjectInfo projectInfo = (ProjectInfo) element;
			return projectInfo.getName();
		}

		return super.getText(obj);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object obj) {

		Object element = obj;
		if (element instanceof ServerInfo) {
			return Activator.getImageDescriptor("icons/ServerInfo.gif").createImage();
		} else if (element instanceof ProjectInfo) {
			return Activator.getImageDescriptor("icons/prj_obj.gif").createImage();
		}

		return super.getImage(obj);
	}
}
