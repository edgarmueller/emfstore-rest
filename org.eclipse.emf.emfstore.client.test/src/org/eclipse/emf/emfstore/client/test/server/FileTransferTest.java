package org.eclipse.emf.emfstore.client.test.server;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.emfstore.client.test.Activator;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.filetransfer.FileTransferManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.server.exceptions.FileTransferException;
import org.junit.Test;

public class FileTransferTest extends WorkspaceTest {

	@Test
	public void testReinitWorkspace() throws FileTransferException, IOException {
		Configuration.setAutoSave(false);
		FileTransferManager transferManager = ((ProjectSpaceBase) getProjectSpace()).getFileTransferManager();
		transferManager.addFile(new File(FileLocator.toFileURL(Activator.getDefault().getBundle().getEntry("."))
			.getPath() + "TestProjects/REGISPeelSessions.zip"));
		// transferManager.dispose();
	}
}
