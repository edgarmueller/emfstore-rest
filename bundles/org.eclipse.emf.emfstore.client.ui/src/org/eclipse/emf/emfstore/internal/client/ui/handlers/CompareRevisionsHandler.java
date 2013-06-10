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
package org.eclipse.emf.emfstore.internal.client.ui.handlers;

import java.util.List;

import org.eclipse.emf.emfstore.internal.client.ui.controller.UICompareRevisionsController;
import org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for comparing two project revisions in the {@link HistoryBrowserView}.
 * 
 * @author Engelmann
 * @author Stute
 * @author jsommerfeldt
 */
public class CompareRevisionsHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		HistoryBrowserView view = (HistoryBrowserView) HandlerUtil.getActivePart(getEvent());
		IStructuredSelection object = (IStructuredSelection) view.getSite().getSelectionProvider().getSelection();

		@SuppressWarnings("unchecked")
		List<HistoryInfo> list = object.toList();

		new UICompareRevisionsController(getShell(), list.get(0).getPrimarySpec().toAPI(),
			list.get(1).getPrimarySpec().toAPI(), view.getProjectSpace().toAPI())
			.execute();
	}

}
