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
package org.eclipse.emf.emfstore.client.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.emfstore.client.model.ModelPackage;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Workspace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.client.model.impl.WorkspaceImpl#getProjectSpaces <em>Project Spaces </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.client.model.impl.WorkspaceImpl#getServerInfos <em>Server Infos</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.client.model.impl.WorkspaceImpl#getUsersessions <em>Usersessions </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.client.model.impl.WorkspaceImpl#getHANSWURST <em>HANSWURST</em>}</li>
 * </ul>
 * </p>
 * 
 */
public class WorkspaceImpl extends WorkspaceBase {

	/**
	 * The cached value of the '{@link #getProjectSpaces() <em>Project Spaces</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getProjectSpaces()
	 * @generated
	 * @ordered
	 */
	protected EList<ProjectSpace> projectSpaces;

	/**
	 * The cached value of the '{@link #getServerInfos() <em>Server Infos</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getServerInfos()
	 * @generated
	 * @ordered
	 */
	protected EList<ServerInfo> serverInfos;

	/**
	 * The cached value of the '{@link #getUsersessions() <em>Usersessions</em>} ' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getUsersessions()
	 * @generated
	 * @ordered
	 */
	protected EList<Usersession> usersessions;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected WorkspaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ModelPackage.WORKSPACE__PROJECT_SPACES:
			return getProjectSpaces();
		case ModelPackage.WORKSPACE__SERVER_INFOS:
			return getServerInfos();
		case ModelPackage.WORKSPACE__USERSESSIONS:
			return getUsersessions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.WORKSPACE__PROJECT_SPACES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getProjectSpaces()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.WORKSPACE__PROJECT_SPACES:
			return ((InternalEList<?>) getProjectSpaces()).basicRemove(otherEnd, msgs);
		case ModelPackage.WORKSPACE__SERVER_INFOS:
			return ((InternalEList<?>) getServerInfos()).basicRemove(otherEnd, msgs);
		case ModelPackage.WORKSPACE__USERSESSIONS:
			return ((InternalEList<?>) getUsersessions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ModelPackage.WORKSPACE__PROJECT_SPACES:
			return projectSpaces != null && !projectSpaces.isEmpty();
		case ModelPackage.WORKSPACE__SERVER_INFOS:
			return serverInfos != null && !serverInfos.isEmpty();
		case ModelPackage.WORKSPACE__USERSESSIONS:
			return usersessions != null && !usersessions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ModelPackage.WORKSPACE__PROJECT_SPACES:
			getProjectSpaces().clear();
			getProjectSpaces().addAll((Collection<? extends ProjectSpace>) newValue);
			return;
		case ModelPackage.WORKSPACE__SERVER_INFOS:
			getServerInfos().clear();
			getServerInfos().addAll((Collection<? extends ServerInfo>) newValue);
			return;
		case ModelPackage.WORKSPACE__USERSESSIONS:
			getUsersessions().clear();
			getUsersessions().addAll((Collection<? extends Usersession>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.WORKSPACE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ModelPackage.WORKSPACE__PROJECT_SPACES:
			getProjectSpaces().clear();
			return;
		case ModelPackage.WORKSPACE__SERVER_INFOS:
			getServerInfos().clear();
			return;
		case ModelPackage.WORKSPACE__USERSESSIONS:
			getUsersessions().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ProjectSpace> getProjectSpaces() {
		if (projectSpaces == null) {
			projectSpaces = new EObjectContainmentWithInverseEList.Resolving<ProjectSpace>(ProjectSpace.class, this,
				ModelPackage.WORKSPACE__PROJECT_SPACES, ModelPackage.PROJECT_SPACE__WORKSPACE);
		}
		return projectSpaces;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ServerInfo> getServerInfos() {
		if (serverInfos == null) {
			serverInfos = new EObjectContainmentEList.Resolving<ServerInfo>(ServerInfo.class, this,
				ModelPackage.WORKSPACE__SERVER_INFOS);
		}
		return serverInfos;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Usersession> getUsersessions() {
		if (usersessions == null) {
			usersessions = new EObjectContainmentEList.Resolving<Usersession>(Usersession.class, this,
				ModelPackage.WORKSPACE__USERSESSIONS);
		}
		return usersessions;
	}

} // WorkspaceImpl
