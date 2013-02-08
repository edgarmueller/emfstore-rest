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
package org.eclipse.emf.emfstore.internal.server.core.subinterfaces;

import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.internal.server.core.AbstractEmfstoreInterface;
import org.eclipse.emf.emfstore.internal.server.core.AbstractSubEmfstoreInterface;
import org.eclipse.emf.emfstore.internal.server.core.helper.EmfStoreMethod;
import org.eclipse.emf.emfstore.internal.server.core.helper.EmfStoreMethod.MethodId;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalEmfStoreException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.ACUser;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.OrgUnitProperty;

/**
 * The {@link ProjectPropertiesSubInterfaceImpl} class is responsible for handling modifications of project properties.
 * 
 * @author pfeifferc
 */
public class ProjectPropertiesSubInterfaceImpl extends AbstractSubEmfstoreInterface {

	/**
	 * @param parentInterface the parent interface
	 * @throws FatalEmfStoreException if any fatal error occurs
	 */
	public ProjectPropertiesSubInterfaceImpl(AbstractEmfstoreInterface parentInterface) throws FatalEmfStoreException {
		super(parentInterface);
	}

	/**
	 * Adds a property to the specified {@link ACUser}.
	 * 
	 * @param changedProperty the property that has been changed
	 * @param recUser the specified {@link ACUser}
	 * @param projectId the specified {@link ProjectId}
	 * @throws EMFStoreException if any error occurs setting the properties
	 */
	@EmfStoreMethod(MethodId.TRANSMITPROPERTY)
	public void transmitProperty(OrgUnitProperty changedProperty, ACUser recUser, ProjectId projectId)
		throws EMFStoreException {
		sanityCheckObjects(changedProperty, recUser, projectId);
		EList<ACUser> users = getServerSpace().getUsers();
		ACUser user = null;
		for (ACUser serverUser : users) {
			if (serverUser.getIdentifier().equals(recUser.getIdentifier())) {
				user = serverUser;
				break;
			}
		}
		if (user == null) {
			throw new EMFStoreException("The user does not exist on the server. Cannot set the property.");
		}
		for (OrgUnitProperty property : user.getProperties()) {
			if (property.getName().equals(changedProperty.getName()) && isProjectEqual(property, changedProperty)) {
				property.setValue(changedProperty.getValue());
				save();
				return;
			}
		}
		user.getProperties().add(changedProperty);
		save();
	}

	private boolean isProjectEqual(OrgUnitProperty property, OrgUnitProperty changedProperty) {
		if ((property.getProject() == null) && (changedProperty.getProject() == null)) {
			return true;
		}
		if (property.getProject() == null) {
			return false;
		}
		if (changedProperty.getProject() == null) {
			return false;
		}
		return (property.getProject().equals(changedProperty.getProject()));
	}

	private void save() throws EMFStoreException {
		try {
			getServerSpace().save();
		} catch (IOException e) {
			throw new EMFStoreException("Cannot set the property on the server.");
		}
	}
}