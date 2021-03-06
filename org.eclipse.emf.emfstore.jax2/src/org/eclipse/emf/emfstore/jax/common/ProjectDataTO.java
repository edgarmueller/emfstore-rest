package org.eclipse.emf.emfstore.jax.common;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.emfstore.internal.common.ResourceFactoryRegistry;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * class for encapsulating the params of create(Empty)Project into one XML Document using JAXB
 * 
 * @author Pascal
 *
 */
@XmlRootElement
public class ProjectDataTO extends TO {
	
	@XmlElement
	private String name;
	@XmlElement
	private String description;
	/**
	 * store a LogMessage as a String
	 */
	@XmlElement
	private String logMessage;
	/**
	 * store a Project as a String
	 */
	@XmlElement
	private String project;
	
	/**
	 * empty constructor needed for JAXB
	 */
	public ProjectDataTO() {
		
	}
	
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
	//			this.logMessage = ModelUtil.eObjectToString(logMessage);
				this.logMessage = serializeEObjectToString(logMessage);
			} if(project != null) {
	//			this.project = ModelUtil.eObjectToString(project);
				this.project = serializeEObjectToString(project);
			}
		} catch (SerializationException e) {
			e.printStackTrace();
		}
		
	}

//	/**
//	 * @param name
//	 * @param description
//	 * @param logMessage
//	 */
//	public ProjectDataTO(String name, String description, LogMessage logMessage) {
//		super();
//		this.name = name;
//		this.description = description;
//		try {
//			if(logMessage != null) {
//				this.logMessage = ModelUtil.eObjectToString(logMessage);
//			}
//		} catch (SerializationException e) {
//			e.printStackTrace();
//		}
//		this.project = null;
//	}
	
	

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
			return (LogMessage) deserializeStringToEObject(logMessage);
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
			return (Project) deserializeStringToEObject(project);
		} catch (SerializationException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
