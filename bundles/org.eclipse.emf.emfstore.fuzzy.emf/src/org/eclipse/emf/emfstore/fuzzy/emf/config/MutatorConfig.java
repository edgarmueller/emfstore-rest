/**
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.fuzzy.emf.config;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Mutator Config</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getRootEClass
 * <em>Root EClass</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getMinObjectsCount
 * <em>Min Objects Count</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isIgnoreAndLog
 * <em>Ignore And Log</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isDoNotGenerateRoot
 * <em>Do Not Generate Root</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isUseEcoreUtilDelete
 * <em>Use Ecore Util Delete </em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getEClassesToIgnore
 * <em>EClasses To Ignore</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getEStructuralFeaturesToIgnore
 * <em>EStructural Features To Ignore</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getEPackages
 * <em>EPackages</em>}</li>
 * <li>
 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getMaxDeleteCount
 * <em>Max Delete Count</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getMutatorConfig()
 * @model
 * @generated
 */
public interface MutatorConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Root EClass</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root EClass</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Root EClass</em>' reference.
	 * @see #setRootEClass(EClass)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getMutatorConfig_RootEClass()
	 * @model
	 * @generated
	 */
	EClass getRootEClass();

	/**
	 * Sets the value of the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getRootEClass
	 * <em>Root EClass</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Root EClass</em>' reference.
	 * @see #getRootEClass()
	 * @generated
	 */
	void setRootEClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Min Objects Count</b></em>' attribute.
	 * The default value is <code>"100"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Objects Count</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Min Objects Count</em>' attribute.
	 * @see #setMinObjectsCount(int)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getMutatorConfig_MinObjectsCount()
	 * @model default="100"
	 * @generated
	 */
	int getMinObjectsCount();

	/**
	 * Sets the value of the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getMinObjectsCount
	 * <em>Min Objects Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Min Objects Count</em>' attribute.
	 * @see #getMinObjectsCount()
	 * @generated
	 */
	void setMinObjectsCount(int value);

	/**
	 * Returns the value of the '<em><b>Ignore And Log</b></em>' attribute. The
	 * default value is <code>"false"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ignore And Log</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ignore And Log</em>' attribute.
	 * @see #setIgnoreAndLog(boolean)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getMutatorConfig_IgnoreAndLog()
	 * @model default="false"
	 * @generated
	 */
	boolean isIgnoreAndLog();

	/**
	 * Sets the value of the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isIgnoreAndLog
	 * <em>Ignore And Log</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Ignore And Log</em>' attribute.
	 * @see #isIgnoreAndLog()
	 * @generated
	 */
	void setIgnoreAndLog(boolean value);

	/**
	 * Returns the value of the '<em><b>Do Not Generate Root</b></em>'
	 * attribute. The default value is <code>"false"</code>. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Do Not Generate Root</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Do Not Generate Root</em>' attribute.
	 * @see #setDoNotGenerateRoot(boolean)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getMutatorConfig_DoNotGenerateRoot()
	 * @model default="false"
	 * @generated
	 */
	boolean isDoNotGenerateRoot();

	/**
	 * Sets the value of the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isDoNotGenerateRoot
	 * <em>Do Not Generate Root</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Do Not Generate Root</em>'
	 *            attribute.
	 * @see #isDoNotGenerateRoot()
	 * @generated
	 */
	void setDoNotGenerateRoot(boolean value);

	/**
	 * Returns the value of the '<em><b>Use Ecore Util Delete</b></em>'
	 * attribute. The default value is <code>"false"</code>. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Use Ecore Util Delete</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Use Ecore Util Delete</em>' attribute.
	 * @see #setUseEcoreUtilDelete(boolean)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getMutatorConfig_UseEcoreUtilDelete()
	 * @model default="false"
	 * @generated
	 */
	boolean isUseEcoreUtilDelete();

	/**
	 * Sets the value of the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isUseEcoreUtilDelete
	 * <em>Use Ecore Util Delete</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Use Ecore Util Delete</em>'
	 *            attribute.
	 * @see #isUseEcoreUtilDelete()
	 * @generated
	 */
	void setUseEcoreUtilDelete(boolean value);

	/**
	 * Returns the value of the '<em><b>EClasses To Ignore</b></em>' reference
	 * list. The list contents are of type {@link org.eclipse.emf.ecore.EClass}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EClasses To Ignore</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>EClasses To Ignore</em>' reference list.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getMutatorConfig_EClassesToIgnore()
	 * @model
	 * @generated
	 */
	EList<EClass> getEClassesToIgnore();

	/**
	 * Returns the value of the '<em><b>EStructural Features To Ignore</b></em>'
	 * reference list. The list contents are of type {@link org.eclipse.emf.ecore.EStructuralFeature}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EStructural Features To Ignore</em>' reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>EStructural Features To Ignore</em>'
	 *         reference list.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getMutatorConfig_EStructuralFeaturesToIgnore()
	 * @model
	 * @generated
	 */
	EList<EStructuralFeature> getEStructuralFeaturesToIgnore();

	/**
	 * Returns the value of the '<em><b>EPackages</b></em>' reference list. The
	 * list contents are of type {@link org.eclipse.emf.ecore.EPackage}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EPackages</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>EPackages</em>' reference list.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getMutatorConfig_EPackages()
	 * @model
	 * @generated
	 */
	EList<EPackage> getEPackages();

	/**
	 * Returns the value of the '<em><b>Max Delete Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Delete Count</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Max Delete Count</em>' attribute.
	 * @see #setMaxDeleteCount(Integer)
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#getMutatorConfig_MaxDeleteCount()
	 * @model
	 * @generated
	 */
	Integer getMaxDeleteCount();

	/**
	 * Sets the value of the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getMaxDeleteCount
	 * <em>Max Delete Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Max Delete Count</em>' attribute.
	 * @see #getMaxDeleteCount()
	 * @generated
	 */
	void setMaxDeleteCount(Integer value);

} // MutatorConfig