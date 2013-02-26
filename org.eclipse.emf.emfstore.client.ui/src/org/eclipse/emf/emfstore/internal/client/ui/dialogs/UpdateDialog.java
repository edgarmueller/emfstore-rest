/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Zardosht Hoiaie
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.dialogs;

import java.util.List;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.ui.Activator;
import org.eclipse.emf.emfstore.internal.client.ui.views.changes.TabbedChangesComposite;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESChangePackageImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the update dialog. It shows just a ChangesTreeComposite.
 * 
 * @author Hodaie
 * @author emueller
 */
public class UpdateDialog extends EMFStoreTitleAreaDialog {

	private List<ChangePackage> changes;
	private ProjectSpace projectSpace;
	private Image updateImage;
	private final ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping;

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param projectSpace
	 *            the project space that should be updated
	 * @param changes
	 *            the list of changes
	 * @param idToEObjectMapping
	 *            a mapping of EObjects to their respective IDs
	 */
	public UpdateDialog(Shell parentShell, ESLocalProject localProject,
		List<ChangePackage> changes,
		ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping) {
		super(parentShell);
		this.idToEObjectMapping = idToEObjectMapping;
		this.setShellStyle(this.getShellStyle() | SWT.RESIZE);
		this.changes = changes;
		this.projectSpace = ((ESLocalProjectImpl) localProject).getInternalAPIImpl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite contents = new Composite(parent, SWT.NONE);
		contents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		contents.setLayout(new GridLayout(1, false));

		// changes tree
		if (changes != null) {
			TabbedChangesComposite changesComposite = new TabbedChangesComposite(
				contents, SWT.BORDER, changes, projectSpace.getProject(),
				idToEObjectMapping, true);
			// changesComposite.setReverseNodes(false);
			changesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true, 2, 1));
		}

		String projectName = "";
		// show number of changes on dialog title
		if (projectSpace.getProjectName() != null
			&& projectSpace.getProjectName().length() > 0) {
			projectName = " for project \"" + projectSpace.getProjectName()
				+ "\"";
		}
		setTitle("Incoming changes from server" + projectName);
		int operationCount = 0;
		int rootCount = 0;
		for (ChangePackage esChangePackage : changes) {
			ChangePackage changePackage = ((ESChangePackageImpl) esChangePackage)
				.getInternalAPIImpl();
			rootCount += changePackage.getOperations().size();
			operationCount += changePackage.getSize();
		}
		setMessage("Number of versions: " + changes.size()
			+ ", Number of composite changes: " + rootCount
			+ ", Number of overall changes: " + operationCount);

		return contents;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Update");
		updateImage = Activator.getImageDescriptor("icons/arrow_up.png")
			.createImage();
		newShell.setImage(updateImage);
	}

	@Override
	public boolean close() {
		updateImage.dispose();
		return super.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void okPressed() {
		// TODO final implementation
		super.okPressed();
	}

}