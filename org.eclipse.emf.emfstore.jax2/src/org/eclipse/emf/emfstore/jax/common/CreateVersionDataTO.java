package org.eclipse.emf.emfstore.jax.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;

@XmlRootElement
public class CreateVersionDataTO extends TO {
	
	/**
	 * baseVersionSpec as String object
	 */
	@XmlElement
	private String baseVersionSpec;
	/**
	 * changePackage as String object
	 */
	@XmlElement
	private String changePackage;
	/**
	 * sourceVersion as String object
	 */
	@XmlElement
	private String sourceVersion;
	/**
	 * logMessage as String object
	 */
	@XmlElement
	private String logMessage;
	
	//empty constructor needed for JAXB
	public CreateVersionDataTO() {
		
	}

	/**
	 * @param baseVersionSpec
	 * @param changePackage
	 * @param sourceVersion
	 * @param logMessage
	 */
	public CreateVersionDataTO(PrimaryVersionSpec baseVersionSpec, ChangePackage changePackage,
			PrimaryVersionSpec sourceVersion, LogMessage logMessage) {
		super();
		
		if(baseVersionSpec != null) {
			this.baseVersionSpec = baseVersionSpec.getBranch();
		}
		if(sourceVersion != null) {
			this.sourceVersion = sourceVersion.getBranch();
		}
		try {
			if(changePackage != null) {
				this.changePackage = serializeEObjectToString(changePackage);
			}
			if(logMessage != null) {
				this.logMessage = serializeEObjectToString(logMessage);
			}
		} catch (SerializationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return baseVersionSpec as PrimaryVersionSpec object
	 */
	public PrimaryVersionSpec getBaseVersionSpec() {
		PrimaryVersionSpec primaryVersionSpec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
		primaryVersionSpec.setBranch(baseVersionSpec);
		return primaryVersionSpec;
	}
	
	/**
	 * 
	 * @return changePackage as ChangePackage object
	 */
	public ChangePackage getChangePackage() {
		try {
			return (ChangePackage) deserializeStringToEObject(changePackage);
		} catch (SerializationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @return sourceVersion as SourceVersion object
	 */
	public PrimaryVersionSpec getSourceVersion() {
		PrimaryVersionSpec primaryVersionSpec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
		primaryVersionSpec.setBranch(sourceVersion);
		return primaryVersionSpec;
	}
	
	/**
	 * 
	 * @return logMessage as LogMessage object
	 */
	public LogMessage getLogMessage() {
		try {
			return (LogMessage) deserializeStringToEObject(logMessage);
		} catch (SerializationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
