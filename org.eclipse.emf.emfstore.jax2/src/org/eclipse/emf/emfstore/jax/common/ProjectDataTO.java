package org.eclipse.emf.emfstore.jax.common;

import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;

/**
 * used as HTTP entity for createEmptyProject() and createProject()
 * 
 * @author Pascal
 *
 */
public class ProjectDataTO {
	
	private String name;
	private String description;
	private LogMessage logMessage;
	private Project project;
	
	/**
	 * @param name
	 * @param description
	 * @param logMessage
	 * @param project
	 */
	public ProjectDataTO(String name, String description,
			LogMessage logMessage, Project project) {
		super();
		this.name = name;
		this.description = description;
		this.logMessage = logMessage;
		this.project = project;
	}

	/**
	 * @param name
	 * @param description
	 * @param logMessage
	 */
	public ProjectDataTO(String name, String description, LogMessage logMessage) {
		super();
		this.name = name;
		this.description = description;
		this.logMessage = logMessage;
		this.project = null;
	}
}
