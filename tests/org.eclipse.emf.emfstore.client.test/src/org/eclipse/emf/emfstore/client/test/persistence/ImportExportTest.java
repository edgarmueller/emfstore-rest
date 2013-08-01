/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.persistence;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESProject;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.internal.client.importexport.ExportImportControllerExecutor;
import org.eclipse.emf.emfstore.internal.client.importexport.ExportImportControllerFactory;
import org.eclipse.emf.emfstore.internal.client.importexport.impl.ExportImportDataUnits;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.junit.Test;

public class ImportExportTest extends WorkspaceTest {

	@Override
	protected void configureCompareAtEnd() {
		setCompareAtEnd(false);
	}

	@Test
	public void testExportImportChangesController() throws IOException {
		ProjectSpace clonedProjectSpace = ModelUtil.clone(getProjectSpace());
		createTestElement("A");
		Assert.assertTrue(getProjectSpace().getOperations().size() > 0);

		// TODO: assert file extension is correct

		File temp = File.createTempFile("changes", ExportImportDataUnits.Change.getExtension());
		new ExportImportControllerExecutor(temp, new NullProgressMonitor())
			.execute(ExportImportControllerFactory.Export.getExportChangesController(getProjectSpace()));

		// TODO: assert file was written

		new ExportImportControllerExecutor(temp, new NullProgressMonitor())
			.execute(ExportImportControllerFactory.Import.getImportChangesController(clonedProjectSpace));

		Assert.assertTrue(ModelUtil.areEqual(getProjectSpace().getProject(), clonedProjectSpace.getProject()));
	}

	@Test
	public void testExportImportProjectController() throws IOException {
		createTestElement("A");
		Assert.assertTrue(getProjectSpace().getOperations().size() > 0);

		// TODO: assert file extension is correct

		File temp = File.createTempFile("project", ExportImportDataUnits.Project.getExtension());
		new ExportImportControllerExecutor(temp, new NullProgressMonitor())
			.execute(ExportImportControllerFactory.Export.getExportProjectController(getProjectSpace()));

		// TODO: assert file was written

		new ExportImportControllerExecutor(temp, new NullProgressMonitor())
			.execute(ExportImportControllerFactory.Import.getImportProjectController("importedProject"));

		ProjectSpace newProjectSpace = null;

		for (ESProject project : ESWorkspaceProviderImpl.getInstance().getWorkspace().getLocalProjects()) {
			if (project.getProjectName().equals("importedProject")) {
				newProjectSpace = getProjectSpace();
				break;
			}
		}

		// TODO: are the imported IDs supposed to be the same as in the original project?
		// Assert.assertTrue(ModelUtil.areEqual(getProjectSpace().getProject(), newProjectSpace.getProject()));
	}

	@Test
	public void testExportImportProjectSpaceController() throws IOException {
		createTestElement("A");
		Assert.assertTrue(getProjectSpace().getOperations().size() > 0);

		// TODO: assert file extension is correct

		File temp = File.createTempFile("projectSpace", ExportImportDataUnits.ProjectSpace.getExtension());
		new ExportImportControllerExecutor(temp, new NullProgressMonitor())
			.execute(ExportImportControllerFactory.Export.getExportProjectSpaceController(getProjectSpace()));

		// TODO: assert file was written

		new ExportImportControllerExecutor(temp, new NullProgressMonitor())
			.execute(ExportImportControllerFactory.Import.getImportProjectSpaceController());

		Assert.assertEquals(2, ESWorkspaceProviderImpl.getInstance().getWorkspace().getLocalProjects().size());

		ESLocalProject a = ESWorkspaceProviderImpl.getInstance().getWorkspace().getLocalProjects().get(0);
		ESLocalProject b = ESWorkspaceProviderImpl.getInstance().getWorkspace().getLocalProjects().get(1);

		// TODO: are the imported IDs supposed to be the same as in the original project?
		// Assert.assertTrue(ModelUtil.areEqual(a.getProject(), b.getProject()));
	}
}
