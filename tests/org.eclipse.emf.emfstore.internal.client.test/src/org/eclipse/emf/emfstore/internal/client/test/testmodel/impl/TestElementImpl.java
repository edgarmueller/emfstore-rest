/**
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.internal.client.test.testmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElementContainer;
import org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getStrings <em>Strings</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getReferences <em>References</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getContainedElements <em>Contained Elements</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getReference <em>Reference</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getContainedElement <em>Contained Element</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getOtherReference <em>Other Reference</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getContainedElements2 <em>Contained Elements2</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getContainer <em>Container</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getElementMap <em>Element Map</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getStringToStringMap <em>String To String Map</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getElementToStringMap <em>Element To String Map</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.impl.TestElementImpl#getStringToElementMap <em>String To Element Map</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TestElementImpl extends EObjectImpl implements TestElement {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStrings() <em>Strings</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrings()
	 * @generated
	 * @ordered
	 */
	protected EList<String> strings;

	/**
	 * The cached value of the '{@link #getReferences() <em>References</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<TestElement> references;

	/**
	 * The cached value of the '{@link #getContainedElements() <em>Contained Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainedElements()
	 * @generated
	 * @ordered
	 */
	protected EList<TestElement> containedElements;

	/**
	 * The cached value of the '{@link #getReference() <em>Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReference()
	 * @generated
	 * @ordered
	 */
	protected TestElement reference;

	/**
	 * The cached value of the '{@link #getContainedElement() <em>Contained Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainedElement()
	 * @generated
	 * @ordered
	 */
	protected TestElement containedElement;

	/**
	 * The cached value of the '{@link #getOtherReference() <em>Other Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherReference()
	 * @generated
	 * @ordered
	 */
	protected TestElement otherReference;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContainedElements2() <em>Contained Elements2</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainedElements2()
	 * @generated
	 * @ordered
	 */
	protected EList<TestElement> containedElements2;

	/**
	 * The cached value of the '{@link #getElementMap() <em>Element Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<TestElement, TestElement> elementMap;

	/**
	 * The cached value of the '{@link #getStringToStringMap() <em>String To String Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringToStringMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> stringToStringMap;

	/**
	 * The cached value of the '{@link #getElementToStringMap() <em>Element To String Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementToStringMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<TestElement, String> elementToStringMap;

	/**
	 * The cached value of the '{@link #getStringToElementMap() <em>String To Element Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringToElementMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, TestElement> stringToElementMap;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestmodelPackage.Literals.TEST_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getStrings() {
		if (strings == null) {
			strings = new EDataTypeUniqueEList<String>(String.class, this, TestmodelPackage.TEST_ELEMENT__STRINGS);
		}
		return strings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TestElement> getReferences() {
		if (references == null) {
			references = new EObjectResolvingEList<TestElement>(TestElement.class, this, TestmodelPackage.TEST_ELEMENT__REFERENCES);
		}
		return references;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TestElement> getContainedElements() {
		if (containedElements == null) {
			containedElements = new EObjectContainmentEList<TestElement>(TestElement.class, this, TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS);
		}
		return containedElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement getReference() {
		if (reference != null && reference.eIsProxy()) {
			InternalEObject oldReference = (InternalEObject)reference;
			reference = (TestElement)eResolveProxy(oldReference);
			if (reference != oldReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TestmodelPackage.TEST_ELEMENT__REFERENCE, oldReference, reference));
			}
		}
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement basicGetReference() {
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReference(TestElement newReference) {
		TestElement oldReference = reference;
		reference = newReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__REFERENCE, oldReference, reference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement getContainedElement() {
		return containedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContainedElement(TestElement newContainedElement, NotificationChain msgs) {
		TestElement oldContainedElement = containedElement;
		containedElement = newContainedElement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT, oldContainedElement, newContainedElement);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainedElement(TestElement newContainedElement) {
		if (newContainedElement != containedElement) {
			NotificationChain msgs = null;
			if (containedElement != null)
				msgs = ((InternalEObject)containedElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT, null, msgs);
			if (newContainedElement != null)
				msgs = ((InternalEObject)newContainedElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT, null, msgs);
			msgs = basicSetContainedElement(newContainedElement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT, newContainedElement, newContainedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement getOtherReference() {
		if (otherReference != null && otherReference.eIsProxy()) {
			InternalEObject oldOtherReference = (InternalEObject)otherReference;
			otherReference = (TestElement)eResolveProxy(oldOtherReference);
			if (otherReference != oldOtherReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TestmodelPackage.TEST_ELEMENT__OTHER_REFERENCE, oldOtherReference, otherReference));
			}
		}
		return otherReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElement basicGetOtherReference() {
		return otherReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOtherReference(TestElement newOtherReference) {
		TestElement oldOtherReference = otherReference;
		otherReference = newOtherReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__OTHER_REFERENCE, oldOtherReference, otherReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TestElement> getContainedElements2() {
		if (containedElements2 == null) {
			containedElements2 = new EObjectContainmentEList<TestElement>(TestElement.class, this, TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2);
		}
		return containedElements2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElementContainer getContainer() {
		if (eContainerFeatureID() != TestmodelPackage.TEST_ELEMENT__CONTAINER) return null;
		return (TestElementContainer)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContainer(TestElementContainer newContainer, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newContainer, TestmodelPackage.TEST_ELEMENT__CONTAINER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainer(TestElementContainer newContainer) {
		if (newContainer != eInternalContainer() || (eContainerFeatureID() != TestmodelPackage.TEST_ELEMENT__CONTAINER && newContainer != null)) {
			if (EcoreUtil.isAncestor(this, newContainer))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newContainer != null)
				msgs = ((InternalEObject)newContainer).eInverseAdd(this, TestmodelPackage.TEST_ELEMENT_CONTAINER__ELEMENTS, TestElementContainer.class, msgs);
			msgs = basicSetContainer(newContainer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__CONTAINER, newContainer, newContainer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<TestElement, TestElement> getElementMap() {
		if (elementMap == null) {
			elementMap = new EcoreEMap<TestElement,TestElement>(TestmodelPackage.Literals.TEST_ELEMENT_TO_TEST_ELEMENT_MAP, TestElementToTestElementMapImpl.class, this, TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP);
		}
		return elementMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getStringToStringMap() {
		if (stringToStringMap == null) {
			stringToStringMap = new EcoreEMap<String,String>(TestmodelPackage.Literals.STRING_TO_STRING_MAP, StringToStringMapImpl.class, this, TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP);
		}
		return stringToStringMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<TestElement, String> getElementToStringMap() {
		if (elementToStringMap == null) {
			elementToStringMap = new EcoreEMap<TestElement,String>(TestmodelPackage.Literals.TEST_ELEMENT_TO_STRING_MAP, TestElementToStringMapImpl.class, this, TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP);
		}
		return elementToStringMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, TestElement> getStringToElementMap() {
		if (stringToElementMap == null) {
			stringToElementMap = new EcoreEMap<String,TestElement>(TestmodelPackage.Literals.STRING_TO_TEST_ELEMENT_MAP, StringToTestElementMapImpl.class, this, TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP);
		}
		return stringToElementMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TestmodelPackage.TEST_ELEMENT__CONTAINER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetContainer((TestElementContainer)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS:
				return ((InternalEList<?>)getContainedElements()).basicRemove(otherEnd, msgs);
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT:
				return basicSetContainedElement(null, msgs);
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
				return ((InternalEList<?>)getContainedElements2()).basicRemove(otherEnd, msgs);
			case TestmodelPackage.TEST_ELEMENT__CONTAINER:
				return basicSetContainer(null, msgs);
			case TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP:
				return ((InternalEList<?>)getElementMap()).basicRemove(otherEnd, msgs);
			case TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP:
				return ((InternalEList<?>)getStringToStringMap()).basicRemove(otherEnd, msgs);
			case TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP:
				return ((InternalEList<?>)getElementToStringMap()).basicRemove(otherEnd, msgs);
			case TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP:
				return ((InternalEList<?>)getStringToElementMap()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case TestmodelPackage.TEST_ELEMENT__CONTAINER:
				return eInternalContainer().eInverseRemove(this, TestmodelPackage.TEST_ELEMENT_CONTAINER__ELEMENTS, TestElementContainer.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TestmodelPackage.TEST_ELEMENT__NAME:
				return getName();
			case TestmodelPackage.TEST_ELEMENT__STRINGS:
				return getStrings();
			case TestmodelPackage.TEST_ELEMENT__REFERENCES:
				return getReferences();
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS:
				return getContainedElements();
			case TestmodelPackage.TEST_ELEMENT__REFERENCE:
				if (resolve) return getReference();
				return basicGetReference();
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT:
				return getContainedElement();
			case TestmodelPackage.TEST_ELEMENT__OTHER_REFERENCE:
				if (resolve) return getOtherReference();
				return basicGetOtherReference();
			case TestmodelPackage.TEST_ELEMENT__DESCRIPTION:
				return getDescription();
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
				return getContainedElements2();
			case TestmodelPackage.TEST_ELEMENT__CONTAINER:
				return getContainer();
			case TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP:
				if (coreType) return getElementMap();
				else return getElementMap().map();
			case TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP:
				if (coreType) return getStringToStringMap();
				else return getStringToStringMap().map();
			case TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP:
				if (coreType) return getElementToStringMap();
				else return getElementToStringMap().map();
			case TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP:
				if (coreType) return getStringToElementMap();
				else return getStringToElementMap().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TestmodelPackage.TEST_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__STRINGS:
				getStrings().clear();
				getStrings().addAll((Collection<? extends String>)newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__REFERENCES:
				getReferences().clear();
				getReferences().addAll((Collection<? extends TestElement>)newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS:
				getContainedElements().clear();
				getContainedElements().addAll((Collection<? extends TestElement>)newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__REFERENCE:
				setReference((TestElement)newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT:
				setContainedElement((TestElement)newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__OTHER_REFERENCE:
				setOtherReference((TestElement)newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
				getContainedElements2().clear();
				getContainedElements2().addAll((Collection<? extends TestElement>)newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__CONTAINER:
				setContainer((TestElementContainer)newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP:
				((EStructuralFeature.Setting)getElementMap()).set(newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP:
				((EStructuralFeature.Setting)getStringToStringMap()).set(newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP:
				((EStructuralFeature.Setting)getElementToStringMap()).set(newValue);
				return;
			case TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP:
				((EStructuralFeature.Setting)getStringToElementMap()).set(newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TestmodelPackage.TEST_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TestmodelPackage.TEST_ELEMENT__STRINGS:
				getStrings().clear();
				return;
			case TestmodelPackage.TEST_ELEMENT__REFERENCES:
				getReferences().clear();
				return;
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS:
				getContainedElements().clear();
				return;
			case TestmodelPackage.TEST_ELEMENT__REFERENCE:
				setReference((TestElement)null);
				return;
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT:
				setContainedElement((TestElement)null);
				return;
			case TestmodelPackage.TEST_ELEMENT__OTHER_REFERENCE:
				setOtherReference((TestElement)null);
				return;
			case TestmodelPackage.TEST_ELEMENT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
				getContainedElements2().clear();
				return;
			case TestmodelPackage.TEST_ELEMENT__CONTAINER:
				setContainer((TestElementContainer)null);
				return;
			case TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP:
				getElementMap().clear();
				return;
			case TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP:
				getStringToStringMap().clear();
				return;
			case TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP:
				getElementToStringMap().clear();
				return;
			case TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP:
				getStringToElementMap().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TestmodelPackage.TEST_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TestmodelPackage.TEST_ELEMENT__STRINGS:
				return strings != null && !strings.isEmpty();
			case TestmodelPackage.TEST_ELEMENT__REFERENCES:
				return references != null && !references.isEmpty();
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS:
				return containedElements != null && !containedElements.isEmpty();
			case TestmodelPackage.TEST_ELEMENT__REFERENCE:
				return reference != null;
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT:
				return containedElement != null;
			case TestmodelPackage.TEST_ELEMENT__OTHER_REFERENCE:
				return otherReference != null;
			case TestmodelPackage.TEST_ELEMENT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
				return containedElements2 != null && !containedElements2.isEmpty();
			case TestmodelPackage.TEST_ELEMENT__CONTAINER:
				return getContainer() != null;
			case TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP:
				return elementMap != null && !elementMap.isEmpty();
			case TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP:
				return stringToStringMap != null && !stringToStringMap.isEmpty();
			case TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP:
				return elementToStringMap != null && !elementToStringMap.isEmpty();
			case TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP:
				return stringToElementMap != null && !stringToElementMap.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", strings: ");
		result.append(strings);
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}

} //TestElementImpl
