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
package org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview;

import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.client.ui.views.scm.SCMLabelProvider;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;

/**
 * @author aleaum
 * 
 */
public class HistorySCMLabelProvider extends SCMLabelProvider {

	/**
	 * Creates a new {@link HistorySCMLabelProvider} for the given project.
	 * 
	 */
	public HistorySCMLabelProvider() {
		super();
	}

	@Override
	protected String getText(HistoryInfo historyInfo) {
		if (historyInfo.getPrimarySpec() != null && historyInfo.getPrimarySpec().getIdentifier() == -1) {
			return LOCAL_REVISION;
		}

		String baseVersion = "";
		if (historyInfo.getPrimarySpec().getIdentifier() == WorkspaceProvider.getProjectSpace(getProject())
			.getBaseVersion().getIdentifier()) {
			baseVersion = "*";
		}
		StringBuilder builder = new StringBuilder();

		if (!historyInfo.getTagSpecs().isEmpty()) {
			builder.append("[");
			for (TagVersionSpec versionSpec : historyInfo.getTagSpecs()) {
				builder.append(versionSpec.getName());
				builder.append(",");
			}
			builder.replace(builder.length() - 1, builder.length(), "] ");
		}

		builder.append(baseVersion);
		builder.append("Version ");
		builder.append(historyInfo.getPrimarySpec().getIdentifier());
		return builder.toString();
	}

	@Override
	public String getToolTipText(Object element) {
		return getText(element);
	}

}