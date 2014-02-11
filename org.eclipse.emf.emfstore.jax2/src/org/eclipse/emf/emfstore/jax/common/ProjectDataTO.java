package org.eclipse.emf.emfstore.jax.common;

import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * class for encapsulating the params of create(Empty)Project into one XML Document using JAXB
 * 
 * @author Pascal
 *
 */
@XmlRootElement
public class ProjectDataTO {
	
	private String name;
	private String description;
	/**
	 * store a LogMessage as a String
	 */
	private String logMessage;
	/**
	 * store a Project as a String
	 */
	private String project;
	
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
		
		try {
			if(logMessage != null) {
				this.logMessage = ModelUtil.eObjectToString(logMessage);
			} if(project != null) {
				this.project = ModelUtil.eObjectToString(project);
			}
		} catch (SerializationException e) {
			e.printStackTrace();
		}
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
		try {
			if(logMessage != null) {
				this.logMessage = ModelUtil.eObjectToString(logMessage);
			}
		} catch (SerializationException e) {
			e.printStackTrace();
		}
		this.project = null;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	/**
	 * @return the logMessage as a LogMessage representation
	 */
	public LogMessage getLogMessage() {
		if(logMessage == null) {
			return null;
		}
		
		try {
			return (LogMessage) ModelUtil.stringToEObject(logMessage);
		} catch (SerializationException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the project as Project representation
	 */
	public Project getProject() {
		if(project == null) {
			return null;
		}
		
		try {
			return (Project) ModelUtil.stringToEObject(project);
		} catch (SerializationException e) {
			e.printStackTrace();
			return null;
		}
	}
}
