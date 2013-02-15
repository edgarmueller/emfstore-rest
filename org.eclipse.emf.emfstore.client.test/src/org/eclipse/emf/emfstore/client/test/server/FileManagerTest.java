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
package org.eclipse.emf.emfstore.client.test.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.emfstore.client.test.model.requirement.RequirementFactory;
import org.eclipse.emf.emfstore.client.test.model.requirement.UseCase;
import org.eclipse.emf.emfstore.internal.client.model.filetransfer.FileDownloadStatus;
import org.eclipse.emf.emfstore.internal.client.model.filetransfer.FileDownloadStatus.Status;
import org.eclipse.emf.emfstore.internal.server.model.FileIdentifier;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Test;

/**
 * Tests the file manager.
 * 
 * @author mkoegel
 * 
 */
public class FileManagerTest extends TransmissionTests {

	@Test
	public void testTransfer() throws ESException, IOException, InterruptedException {
		File file = File.createTempFile("foo", "tmp");
		file.deleteOnExit();
		LogMessage msg = VersioningFactory.eINSTANCE.createLogMessage();
		FileIdentifier id = getProjectSpace1().addFile(file);
		// dummy change, addFile is not recognized as a change
		final UseCase useCase = RequirementFactory.eINSTANCE.createUseCase();
		getProjectSpace1().getProject().addModelElement(useCase);
		getProjectSpace1().commit(msg, null, null);

		getProjectSpace2().update();
		FileDownloadStatus status = getProjectSpace2().getFile(id);
		assertTrue(status != null);
		// wait for file to be completely transferred
		while (status.getStatus() != Status.FINISHED) {
			Thread.sleep(500);
		}
		FileInputStream fileInputStream = new FileInputStream(file);
		FileInputStream fileInputStream2 = new FileInputStream(status.getTransferredFile());
		try {
			assertEquals(IOUtils.toByteArray(fileInputStream), IOUtils.toByteArray(fileInputStream2));
		} finally {
			fileInputStream.close();
			fileInputStream2.close();
		}
	}
}