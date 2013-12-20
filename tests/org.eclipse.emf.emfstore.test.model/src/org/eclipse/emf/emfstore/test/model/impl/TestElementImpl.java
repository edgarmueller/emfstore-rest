/**
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 */
package org.eclipse.emf.emfstore.test.model.impl;

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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.eclipse.emf.emfstore.test.model.TestmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getStrings <em>Strings</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getReferences <em>References</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getContainedElements <em>Contained Elements</em>}
 * </li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getReference <em>Reference</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getContainedElement <em>Contained Element</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getOtherReference <em>Other Reference</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getDescription <em>Description</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getContainer <em>Container</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getSrefContainer <em>Sref Container</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getElementMap <em>Element Map</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getStringToStringMap <em>String To String Map
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getElementToStringMap <em>Element To String Map
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getStringToElementMap <em>String To Element Map
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getNonContained_NTo1 <em>Non Contained NTo1</em>}
 * </li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getNonContained_1ToN <em>Non Contained 1To N
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getNonContained_NToM <em>Non Contained NTo M
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getNonContained_MToN <em>Non Contained MTo N
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getContainedElements2 <em>Contained Elements2
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getContainer2 <em>Container2</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.test.model.impl.TestElementImpl#getContainedElements_NoOpposite <em>Contained
 * Elements No Opposite</em>}</li>
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
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStrings() <em>Strings</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getStrings()
	 * @generated
	 * @ordered
	 */
	protected EList<String> strings;

	/**
	 * The cached value of the '{@link #getReferences() <em>References</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<TestElement> references;

	/**
	 * The cached value of the '{@link #getContainedElements() <em>Contained Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getContainedElements()
	 * @generated
	 * @ordered
	 */
	protected EList<TestElement> containedElements;

	/**
	 * The cached value of the '{@link #getReference() <em>Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getReference()
	 * @generated
	 * @ordered
	 */
	protected TestElement reference;

	/**
	 * The cached value of the '{@link #getContainedElement() <em>Contained Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getContainedElement()
	 * @generated
	 * @ordered
	 */
	protected TestElement containedElement;

	/**
	 * The cached value of the '{@link #getOtherReference() <em>Other Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getOtherReference()
	 * @generated
	 * @ordered
	 */
	protected TestElement otherReference;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getElementMap() <em>Element Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getElementMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<TestElement, TestElement> elementMap;

	/**
	 * The cached value of the '{@link #getStringToStringMap() <em>String To String Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getStringToStringMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> stringToStringMap;

	/**
	 * The cached value of the '{@link #getElementToStringMap() <em>Element To String Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getElementToStringMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<TestElement, String> elementToStringMap;

	/**
	 * The cached value of the '{@link #getStringToElementMap() <em>String To Element Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getStringToElementMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, TestElement> stringToElementMap;

	/**
	 * The cached value of the '{@link #getNonContained_NTo1() <em>Non Contained NTo1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getNonContained_NTo1()
	 * @generated
	 * @ordered
	 */
	protected TestElement nonContained_NTo1;

	/**
	 * The cached value of the '{@link #getNonContained_1ToN() <em>Non Contained 1To N</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getNonContained_1ToN()
	 * @generated
	 * @ordered
	 */
	protected EList<TestElement> nonContained_1ToN;

	/**
	 * The cached value of the '{@link #getNonContained_NToM() <em>Non Contained NTo M</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getNonContained_NToM()
	 * @generated
	 * @ordered
	 */
	protected EList<TestElement> nonContained_NToM;

	/**
	 * The cached value of the '{@link #getNonContained_MToN() <em>Non Contained MTo N</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getNonContained_MToN()
	 * @generated
	 * @ordered
	 */
	protected EList<TestElement> nonContained_MToN;

	/**
	 * The cached value of the '{@link #getContainedElements2() <em>Contained Elements2</em>}' containment reference
	 * list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getContainedElements2()
	 * @generated
	 * @ordered
	 */
	protected EList<TestElement> containedElements2;

	/**
	 * The cached value of the '{@link #getContainedElements_NoOpposite() <em>Contained Elements No Opposite</em>}'
	 * containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getContainedElements_NoOpposite()
	 * @generated
	 * @ordered
	 */
	protected EList<TestElement> containedElements_NoOpposite;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TestElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestmodelPackage.Literals.TEST_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		final String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<String> getStrings() {
		if (strings == null)
		{
			strings = new EDataTypeUniqueEList<String>(String.class, this, TestmodelPackage.TEST_ELEMENT__STRINGS);
		}
		return strings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TestElement> getReferences() {
		if (references == null)
		{
			references = new EObjectResolvingEList<TestElement>(TestElement.class, this,
				TestmodelPackage.TEST_ELEMENT__REFERENCES);
		}
		return references;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TestElement> getContainedElements() {
		if (containedElements == null)
		{
			containedElements = new EObjectContainmentWithInverseEList<TestElement>(TestElement.class, this,
				TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS, TestmodelPackage.TEST_ELEMENT__CONTAINER);
		}
		return containedElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestElement getReference() {
		if (reference != null && reference.eIsProxy())
		{
			final InternalEObject oldReference = (InternalEObject) reference;
			reference = (TestElement) eResolveProxy(oldReference);
			if (reference != oldReference)
			{
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TestmodelPackage.TEST_ELEMENT__REFERENCE,
						oldReference, reference));
				}
			}
		}
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestElement basicGetReference() {
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setReference(TestElement newReference) {
		final TestElement oldReference = reference;
		reference = newReference;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__REFERENCE,
				oldReference, reference));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestElement getContainedElement() {
		return containedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetContainedElement(TestElement newContainedElement, NotificationChain msgs) {
		final TestElement oldContainedElement = containedElement;
		containedElement = newContainedElement;
		if (eNotificationRequired())
		{
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT, oldContainedElement, newContainedElement);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setContainedElement(TestElement newContainedElement) {
		if (newContainedElement != containedElement)
		{
			NotificationChain msgs = null;
			if (containedElement != null) {
				msgs = ((InternalEObject) containedElement).eInverseRemove(this,
					TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER, TestElement.class, msgs);
			}
			if (newContainedElement != null) {
				msgs = ((InternalEObject) newContainedElement).eInverseAdd(this,
					TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER, TestElement.class, msgs);
			}
			msgs = basicSetContainedElement(newContainedElement, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT,
				newContainedElement, newContainedElement));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestElement getOtherReference() {
		if (otherReference != null && otherReference.eIsProxy())
		{
			final InternalEObject oldOtherReference = (InternalEObject) otherReference;
			otherReference = (TestElement) eResolveProxy(oldOtherReference);
			if (otherReference != oldOtherReference)
			{
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						TestmodelPackage.TEST_ELEMENT__OTHER_REFERENCE, oldOtherReference, otherReference));
				}
			}
		}
		return otherReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestElement basicGetOtherReference() {
		return otherReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setOtherReference(TestElement newOtherReference) {
		final TestElement oldOtherReference = otherReference;
		otherReference = newOtherReference;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__OTHER_REFERENCE,
				oldOtherReference, otherReference));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDescription(String newDescription) {
		final String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__DESCRIPTION,
				oldDescription, description));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestElement getContainer() {
		if (eContainerFeatureID() != TestmodelPackage.TEST_ELEMENT__CONTAINER) {
			return null;
		}
		return (TestElement) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetContainer(TestElement newContainer, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newContainer, TestmodelPackage.TEST_ELEMENT__CONTAINER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setContainer(TestElement newContainer) {
		if (newContainer != eInternalContainer()
			|| eContainerFeatureID() != TestmodelPackage.TEST_ELEMENT__CONTAINER && newContainer != null)
		{
			if (EcoreUtil.isAncestor(this, newContainer))
			{
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newContainer != null) {
				msgs = ((InternalEObject) newContainer).eInverseAdd(this,
					TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS, TestElement.class, msgs);
			}
			msgs = basicSetContainer(newContainer, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__CONTAINER,
				newContainer, newContainer));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestElement getSrefContainer() {
		if (eContainerFeatureID() != TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER) {
			return null;
		}
		return (TestElement) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetSrefContainer(TestElement newSrefContainer, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newSrefContainer, TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER,
			msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSrefContainer(TestElement newSrefContainer) {
		if (newSrefContainer != eInternalContainer()
			|| eContainerFeatureID() != TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER && newSrefContainer != null)
		{
			if (EcoreUtil.isAncestor(this, newSrefContainer))
			{
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newSrefContainer != null) {
				msgs = ((InternalEObject) newSrefContainer).eInverseAdd(this,
					TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT, TestElement.class, msgs);
			}
			msgs = basicSetSrefContainer(newSrefContainer, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER,
				newSrefContainer, newSrefContainer));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EMap<TestElement, TestElement> getElementMap() {
		if (elementMap == null)
		{
			elementMap = new EcoreEMap<TestElement, TestElement>(
				TestmodelPackage.Literals.TEST_ELEMENT_TO_TEST_ELEMENT_MAP, TestElementToTestElementMapImpl.class,
				this, TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP);
		}
		return elementMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EMap<String, String> getStringToStringMap() {
		if (stringToStringMap == null)
		{
			stringToStringMap = new EcoreEMap<String, String>(TestmodelPackage.Literals.STRING_TO_STRING_MAP,
				StringToStringMapImpl.class, this, TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP);
		}
		return stringToStringMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EMap<TestElement, String> getElementToStringMap() {
		if (elementToStringMap == null)
		{
			elementToStringMap = new EcoreEMap<TestElement, String>(
				TestmodelPackage.Literals.TEST_ELEMENT_TO_STRING_MAP, TestElementToStringMapImpl.class, this,
				TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP);
		}
		return elementToStringMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EMap<String, TestElement> getStringToElementMap() {
		if (stringToElementMap == null)
		{
			stringToElementMap = new EcoreEMap<String, TestElement>(
				TestmodelPackage.Literals.STRING_TO_TEST_ELEMENT_MAP, StringToTestElementMapImpl.class, this,
				TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP);
		}
		return stringToElementMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestElement getNonContained_NTo1() {
		if (nonContained_NTo1 != null && nonContained_NTo1.eIsProxy())
		{
			final InternalEObject oldNonContained_NTo1 = (InternalEObject) nonContained_NTo1;
			nonContained_NTo1 = (TestElement) eResolveProxy(oldNonContained_NTo1);
			if (nonContained_NTo1 != oldNonContained_NTo1)
			{
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO1, oldNonContained_NTo1, nonContained_NTo1));
				}
			}
		}
		return nonContained_NTo1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestElement basicGetNonContained_NTo1() {
		return nonContained_NTo1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetNonContained_NTo1(TestElement newNonContained_NTo1, NotificationChain msgs) {
		final TestElement oldNonContained_NTo1 = nonContained_NTo1;
		nonContained_NTo1 = newNonContained_NTo1;
		if (eNotificationRequired())
		{
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO1, oldNonContained_NTo1, newNonContained_NTo1);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setNonContained_NTo1(TestElement newNonContained_NTo1) {
		if (newNonContained_NTo1 != nonContained_NTo1)
		{
			NotificationChain msgs = null;
			if (nonContained_NTo1 != null) {
				msgs = ((InternalEObject) nonContained_NTo1).eInverseRemove(this,
					TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_1TO_N, TestElement.class, msgs);
			}
			if (newNonContained_NTo1 != null) {
				msgs = ((InternalEObject) newNonContained_NTo1).eInverseAdd(this,
					TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_1TO_N, TestElement.class, msgs);
			}
			msgs = basicSetNonContained_NTo1(newNonContained_NTo1, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO1,
				newNonContained_NTo1, newNonContained_NTo1));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TestElement> getNonContained_1ToN() {
		if (nonContained_1ToN == null)
		{
			nonContained_1ToN = new EObjectWithInverseResolvingEList<TestElement>(TestElement.class, this,
				TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_1TO_N, TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO1);
		}
		return nonContained_1ToN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TestElement> getNonContained_NToM() {
		if (nonContained_NToM == null)
		{
			nonContained_NToM = new EObjectWithInverseResolvingEList.ManyInverse<TestElement>(TestElement.class, this,
				TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO_M, TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_MTO_N);
		}
		return nonContained_NToM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TestElement> getNonContained_MToN() {
		if (nonContained_MToN == null)
		{
			nonContained_MToN = new EObjectWithInverseResolvingEList.ManyInverse<TestElement>(TestElement.class, this,
				TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_MTO_N, TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO_M);
		}
		return nonContained_MToN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TestElement> getContainedElements2() {
		if (containedElements2 == null)
		{
			containedElements2 = new EObjectContainmentWithInverseEList<TestElement>(TestElement.class, this,
				TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2, TestmodelPackage.TEST_ELEMENT__CONTAINER2);
		}
		return containedElements2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestElement getContainer2() {
		if (eContainerFeatureID() != TestmodelPackage.TEST_ELEMENT__CONTAINER2) {
			return null;
		}
		return (TestElement) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetContainer2(TestElement newContainer2, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newContainer2, TestmodelPackage.TEST_ELEMENT__CONTAINER2, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setContainer2(TestElement newContainer2) {
		if (newContainer2 != eInternalContainer()
			|| eContainerFeatureID() != TestmodelPackage.TEST_ELEMENT__CONTAINER2 && newContainer2 != null)
		{
			if (EcoreUtil.isAncestor(this, newContainer2))
			{
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newContainer2 != null) {
				msgs = ((InternalEObject) newContainer2).eInverseAdd(this,
					TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2, TestElement.class, msgs);
			}
			msgs = basicSetContainer2(newContainer2, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.TEST_ELEMENT__CONTAINER2,
				newContainer2, newContainer2));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TestElement> getContainedElements_NoOpposite()
	{
		if (containedElements_NoOpposite == null)
		{
			containedElements_NoOpposite = new EObjectContainmentEList<TestElement>(TestElement.class, this,
				TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS_NO_OPPOSITE);
		}
		return containedElements_NoOpposite;
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
		switch (featureID)
		{
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getContainedElements())
				.basicAdd(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT:
			if (containedElement != null) {
				msgs = ((InternalEObject) containedElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT, null, msgs);
			}
			return basicSetContainedElement((TestElement) otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__CONTAINER:
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			return basicSetContainer((TestElement) otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER:
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			return basicSetSrefContainer((TestElement) otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO1:
			if (nonContained_NTo1 != null) {
				msgs = ((InternalEObject) nonContained_NTo1).eInverseRemove(this,
					TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_1TO_N, TestElement.class, msgs);
			}
			return basicSetNonContained_NTo1((TestElement) otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_1TO_N:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getNonContained_1ToN())
				.basicAdd(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO_M:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getNonContained_NToM())
				.basicAdd(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_MTO_N:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getNonContained_MToN())
				.basicAdd(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getContainedElements2()).basicAdd(otherEnd,
				msgs);
		case TestmodelPackage.TEST_ELEMENT__CONTAINER2:
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			return basicSetContainer2((TestElement) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS:
			return ((InternalEList<?>) getContainedElements()).basicRemove(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT:
			return basicSetContainedElement(null, msgs);
		case TestmodelPackage.TEST_ELEMENT__CONTAINER:
			return basicSetContainer(null, msgs);
		case TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER:
			return basicSetSrefContainer(null, msgs);
		case TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP:
			return ((InternalEList<?>) getElementMap()).basicRemove(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP:
			return ((InternalEList<?>) getStringToStringMap()).basicRemove(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP:
			return ((InternalEList<?>) getElementToStringMap()).basicRemove(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP:
			return ((InternalEList<?>) getStringToElementMap()).basicRemove(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO1:
			return basicSetNonContained_NTo1(null, msgs);
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_1TO_N:
			return ((InternalEList<?>) getNonContained_1ToN()).basicRemove(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO_M:
			return ((InternalEList<?>) getNonContained_NToM()).basicRemove(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_MTO_N:
			return ((InternalEList<?>) getNonContained_MToN()).basicRemove(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
			return ((InternalEList<?>) getContainedElements2()).basicRemove(otherEnd, msgs);
		case TestmodelPackage.TEST_ELEMENT__CONTAINER2:
			return basicSetContainer2(null, msgs);
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS_NO_OPPOSITE:
			return ((InternalEList<?>) getContainedElements_NoOpposite()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID())
		{
		case TestmodelPackage.TEST_ELEMENT__CONTAINER:
			return eInternalContainer().eInverseRemove(this, TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS,
				TestElement.class, msgs);
		case TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER:
			return eInternalContainer().eInverseRemove(this, TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT,
				TestElement.class, msgs);
		case TestmodelPackage.TEST_ELEMENT__CONTAINER2:
			return eInternalContainer().eInverseRemove(this, TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2,
				TestElement.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
		case TestmodelPackage.TEST_ELEMENT__NAME:
			return getName();
		case TestmodelPackage.TEST_ELEMENT__STRINGS:
			return getStrings();
		case TestmodelPackage.TEST_ELEMENT__REFERENCES:
			return getReferences();
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS:
			return getContainedElements();
		case TestmodelPackage.TEST_ELEMENT__REFERENCE:
			if (resolve) {
				return getReference();
			}
			return basicGetReference();
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT:
			return getContainedElement();
		case TestmodelPackage.TEST_ELEMENT__OTHER_REFERENCE:
			if (resolve) {
				return getOtherReference();
			}
			return basicGetOtherReference();
		case TestmodelPackage.TEST_ELEMENT__DESCRIPTION:
			return getDescription();
		case TestmodelPackage.TEST_ELEMENT__CONTAINER:
			return getContainer();
		case TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER:
			return getSrefContainer();
		case TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP:
			if (coreType) {
				return getElementMap();
			}
			return getElementMap().map();
		case TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP:
			if (coreType) {
				return getStringToStringMap();
			}
			return getStringToStringMap().map();
		case TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP:
			if (coreType) {
				return getElementToStringMap();
			}
			return getElementToStringMap().map();
		case TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP:
			if (coreType) {
				return getStringToElementMap();
			}
			return getStringToElementMap().map();
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO1:
			if (resolve) {
				return getNonContained_NTo1();
			}
			return basicGetNonContained_NTo1();
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_1TO_N:
			return getNonContained_1ToN();
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO_M:
			return getNonContained_NToM();
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_MTO_N:
			return getNonContained_MToN();
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
			return getContainedElements2();
		case TestmodelPackage.TEST_ELEMENT__CONTAINER2:
			return getContainer2();
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS_NO_OPPOSITE:
			return getContainedElements_NoOpposite();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
		case TestmodelPackage.TEST_ELEMENT__NAME:
			setName((String) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__STRINGS:
			getStrings().clear();
			getStrings().addAll((Collection<? extends String>) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__REFERENCES:
			getReferences().clear();
			getReferences().addAll((Collection<? extends TestElement>) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS:
			getContainedElements().clear();
			getContainedElements().addAll((Collection<? extends TestElement>) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__REFERENCE:
			setReference((TestElement) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT:
			setContainedElement((TestElement) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__OTHER_REFERENCE:
			setOtherReference((TestElement) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__DESCRIPTION:
			setDescription((String) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINER:
			setContainer((TestElement) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER:
			setSrefContainer((TestElement) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP:
			((EStructuralFeature.Setting) getElementMap()).set(newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP:
			((EStructuralFeature.Setting) getStringToStringMap()).set(newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP:
			((EStructuralFeature.Setting) getElementToStringMap()).set(newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP:
			((EStructuralFeature.Setting) getStringToElementMap()).set(newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO1:
			setNonContained_NTo1((TestElement) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_1TO_N:
			getNonContained_1ToN().clear();
			getNonContained_1ToN().addAll((Collection<? extends TestElement>) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO_M:
			getNonContained_NToM().clear();
			getNonContained_NToM().addAll((Collection<? extends TestElement>) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_MTO_N:
			getNonContained_MToN().clear();
			getNonContained_MToN().addAll((Collection<? extends TestElement>) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
			getContainedElements2().clear();
			getContainedElements2().addAll((Collection<? extends TestElement>) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINER2:
			setContainer2((TestElement) newValue);
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS_NO_OPPOSITE:
			getContainedElements_NoOpposite().clear();
			getContainedElements_NoOpposite().addAll((Collection<? extends TestElement>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID)
		{
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
			setReference((TestElement) null);
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT:
			setContainedElement((TestElement) null);
			return;
		case TestmodelPackage.TEST_ELEMENT__OTHER_REFERENCE:
			setOtherReference((TestElement) null);
			return;
		case TestmodelPackage.TEST_ELEMENT__DESCRIPTION:
			setDescription(DESCRIPTION_EDEFAULT);
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINER:
			setContainer((TestElement) null);
			return;
		case TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER:
			setSrefContainer((TestElement) null);
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
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO1:
			setNonContained_NTo1((TestElement) null);
			return;
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_1TO_N:
			getNonContained_1ToN().clear();
			return;
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO_M:
			getNonContained_NToM().clear();
			return;
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_MTO_N:
			getNonContained_MToN().clear();
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
			getContainedElements2().clear();
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINER2:
			setContainer2((TestElement) null);
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS_NO_OPPOSITE:
			getContainedElements_NoOpposite().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID)
		{
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
		case TestmodelPackage.TEST_ELEMENT__CONTAINER:
			return getContainer() != null;
		case TestmodelPackage.TEST_ELEMENT__SREF_CONTAINER:
			return getSrefContainer() != null;
		case TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP:
			return elementMap != null && !elementMap.isEmpty();
		case TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP:
			return stringToStringMap != null && !stringToStringMap.isEmpty();
		case TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP:
			return elementToStringMap != null && !elementToStringMap.isEmpty();
		case TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP:
			return stringToElementMap != null && !stringToElementMap.isEmpty();
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO1:
			return nonContained_NTo1 != null;
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_1TO_N:
			return nonContained_1ToN != null && !nonContained_1ToN.isEmpty();
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_NTO_M:
			return nonContained_NToM != null && !nonContained_NToM.isEmpty();
		case TestmodelPackage.TEST_ELEMENT__NON_CONTAINED_MTO_N:
			return nonContained_MToN != null && !nonContained_MToN.isEmpty();
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
			return containedElements2 != null && !containedElements2.isEmpty();
		case TestmodelPackage.TEST_ELEMENT__CONTAINER2:
			return getContainer2() != null;
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS_NO_OPPOSITE:
			return containedElements_NoOpposite != null && !containedElements_NoOpposite.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", strings: "); //$NON-NLS-1$
		result.append(strings);
		result.append(", description: "); //$NON-NLS-1$
		result.append(description);
		result.append(')');
		return result.toString();
	}

} // TestElementImpl
