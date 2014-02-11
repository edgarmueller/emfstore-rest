package org.eclipse.emf.emfstore.jax.common;

import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;

/**
 * used to encapsulate the method params of createEmptyProject() and createProject() into one POJO. This will be (de-)serialized by MessageBodyReader/-Writer
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
