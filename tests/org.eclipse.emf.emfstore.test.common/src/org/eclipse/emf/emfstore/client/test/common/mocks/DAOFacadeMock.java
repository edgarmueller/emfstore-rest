package org.eclipse.emf.emfstore.client.test.common.mocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACGroup;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.dao.ACDAOFacade;

public class DAOFacadeMock implements ACDAOFacade {
	
	private List<ACUser> users;
	private List<ACGroup> groups;
	private List<ProjectHistory> projects;
	
	public DAOFacadeMock() {
		users = new ArrayList<ACUser>();
		groups = new ArrayList<ACGroup>();
		projects = new ArrayList<ProjectHistory>();
	}
	

	public List<ACUser> getUsers() {
		return users;
	}

	public List<ACGroup> getGroups() {
		return groups;
	}

	public void add(ACUser user) {
		users.add(user);
	}

	public void add(ACGroup group) {
		groups.add(group);
	}

	public void remove(ACUser user) {
		users.remove(user);
	}

	public void remove(ACGroup group) {
		groups.remove(group);
	}


	public void save() throws IOException {
		
	}


	public void add(ProjectHistory history) {
		projects.add(history);
	}


	public List<ProjectHistory> getProjects() {
		return projects;
	}


	public void remove(ProjectHistory history) {
		projects.remove(history);
	}


}
