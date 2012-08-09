/*******************************************************************************
 * Copyright (c) 2008-2012 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.views.scm.SCMLabelProvider;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;

/**
 * @author aleaum
 * 
 */
public class HistorySCMLabelProvider extends SCMLabelProvider {

	/**
	 * Creates a new {@link HistorySCMLabelProvider} for the given project.
	 * 
	 * @param project The project to represent.
	 */
	public HistorySCMLabelProvider(Project project) {
		super(project);
	}

	@Override
	protected String getText(HistoryInfo historyInfo) {
		if (historyInfo.getPrimerySpec() != null && historyInfo.getPrimerySpec().getIdentifier() == -1) {
			return LOCAL_REVISION;
		}

		String baseVersion = "";
		if (historyInfo.getPrimerySpec().getIdentifier() == WorkspaceManager.getProjectSpace(getProject())
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
		builder.append(historyInfo.getPrimerySpec().getIdentifier());
		return builder.toString();
	}

	@Override
	public String getToolTipText(Object element) {
		return getText(element);
	}

}
