/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.CreateTagDialog;
import org.eclipse.emf.emfstore.internal.common.APIUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.ESHistoryInfo;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESTagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESVersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * UI controller for creating a tag for a project.
 * 
 * @author emueller
 * 
 */
public class UIAddTagController extends AbstractEMFStoreUIController<Void> {

	private final ESHistoryInfo historyInfo;
	private final ESLocalProject localProject;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the shell that will be used to create the tag
	 * @param localProject
	 *            the {@link ESLocalProject} for which to create the tag for
	 * @param historyInfo
	 *            the {@link ESHistoryInfo} object that is needed to determine the version for which to create a tag
	 */
	public UIAddTagController(Shell shell, ESLocalProject localProject, ESHistoryInfo historyInfo) {
		super(shell);
		this.localProject = localProject;
		this.historyInfo = historyInfo;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor monitor) throws ESException {

		CreateTagDialog dialog = createDialog(getShell(), localProject.getBranches(monitor));

		String tagName = StringUtils.EMPTY;
		BranchInfo branchInfo = null;

		while (StringUtils.isBlank(tagName) || branchInfo == null) {

			if (dialog.open() != Window.OK) {
				// cancel pressed
				return null;
			}

			tagName = dialog.getTagName();
			branchInfo = dialog.getResult();

			if (branchInfo == null) {
				MessageDialog.openWarning(getShell(), "No branch selected",
					"No branch has been selected. Please select the branch should be tagged.");
			} else if (StringUtils.isBlank(tagName)) {
				MessageDialog.openWarning(getShell(), "Empty tag name",
					"No tag name has been given. Please enter the name of the tag.");
			}
		}

		String branchName = branchInfo.getName();
		ESPrimaryVersionSpec primaryVersion = historyInfo.getPrimarySpec();
		ESTagVersionSpec tag = ESVersionSpec.FACTORY.createTAG(tagName, branchName);

		try {
			localProject.addTag(primaryVersion, tag, monitor);
		} catch (ESException e) {
			WorkspaceUtil.logException(e.getMessage(), e);
			MessageDialog.openError(getShell(), Messages.UIAddTagController_ErrorTitle,
				Messages.UIAddTagController_ErrorReason + e.getMessage());
			return null;
		}

		// also add tag to the selected history info
		historyInfo.getTagSpecs().add(tag);

		return null;
	}

	private CreateTagDialog createDialog(Shell shell, List<ESBranchInfo> branches) {
		List<BranchInfo> internalBranches = APIUtil.toInternal(branches);
		return new CreateTagDialog(shell, internalBranches);
	}

}