package org.eclipse.emf.emfstore.client.test.common.util;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.test.common.util.ProjectUtil;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

public class Times {
	
	private ESLocalProject localProject;

	public Times(ESLocalProject localProject) {
		this.localProject = localProject;
		
	}

	public ESLocalProject times(int n) throws ESException {
		for (int i = 0; i < n; i++) {
			localProject = ProjectUtil.commit(ProjectUtil.addElement(localProject, Create.testElement()));
		}
		return localProject;
	}
	
}