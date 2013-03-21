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
package org.eclipse.emf.emfstore.internal.common.model.impl.util;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo;
import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo;

/**
 * @author jsommerfeldt
 * 
 */
public class ESNotificationInfoImpl extends AbstractAPIImpl<ESNotificationInfo, NotificationInfo> implements
	ESNotificationInfo {

	/**
	 * @param internalType The internal type.
	 */
	public ESNotificationInfoImpl(NotificationInfo internalType) {
		super(internalType);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getStructuralFeature()
	 */
	public EStructuralFeature getStructuralFeature() {
		return getInternalAPIImpl().getStructuralFeature();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isValid()
	 */
	public boolean isValid() {
		return getInternalAPIImpl().isValid();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getValidationMessage()
	 */
	public String getValidationMessage() {
		return getInternalAPIImpl().getValidationMessage();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isAttributeNotification()
	 */
	public boolean isAttributeNotification() {
		return getInternalAPIImpl().isAttributeNotification();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isReferenceNotification()
	 */
	public boolean isReferenceNotification() {
		return getInternalAPIImpl().isReferenceNotification();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getAttribute()
	 */
	public EAttribute getAttribute() {
		return getInternalAPIImpl().getAttribute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getReference()
	 */
	public EReference getReference() {
		return getInternalAPIImpl().getReference();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isTransient()
	 */
	public boolean isTransient() {
		return getInternalAPIImpl().isTransient();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isAddEvent()
	 */
	public boolean isAddEvent() {
		return getInternalAPIImpl().isAddEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isRemoveEvent()
	 */
	public boolean isRemoveEvent() {
		return getInternalAPIImpl().isRemoveEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isSetEvent()
	 */
	public boolean isSetEvent() {
		return getInternalAPIImpl().isSetEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isAddManyEvent()
	 */
	public boolean isAddManyEvent() {
		return getInternalAPIImpl().isAddManyEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isRemoveManyEvent()
	 */
	public boolean isRemoveManyEvent() {
		return getInternalAPIImpl().isRemoveManyEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isMoveEvent()
	 */
	public boolean isMoveEvent() {
		return getInternalAPIImpl().isMoveEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#hasNext()
	 */
	public boolean hasNext() {
		return getInternalAPIImpl().hasNext();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getEventType()
	 */
	public int getEventType() {
		return getInternalAPIImpl().getEventType();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getFeature()
	 */
	public Object getFeature() {
		return getInternalAPIImpl().getFeature();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getFeatureID(java.lang.Class)
	 */
	public int getFeatureID(Class<?> expectedClass) {
		return getInternalAPIImpl().getFeatureID(expectedClass);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewBooleanValue()
	 */
	public boolean getNewBooleanValue() {
		return getInternalAPIImpl().getNewBooleanValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewByteValue()
	 */
	public byte getNewByteValue() {
		return getInternalAPIImpl().getNewByteValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewCharValue()
	 */
	public char getNewCharValue() {
		return getInternalAPIImpl().getNewCharValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewDoubleValue()
	 */
	public double getNewDoubleValue() {
		return getInternalAPIImpl().getNewDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewFloatValue()
	 */
	public float getNewFloatValue() {
		return getInternalAPIImpl().getNewFloatValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewIntValue()
	 */
	public int getNewIntValue() {
		return getInternalAPIImpl().getNewIntValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewLongValue()
	 */
	public long getNewLongValue() {
		return getInternalAPIImpl().getNewLongValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewShortValue()
	 */
	public short getNewShortValue() {
		return getInternalAPIImpl().getNewShortValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewStringValue()
	 */
	public String getNewStringValue() {
		return getInternalAPIImpl().getNewStringValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewValue()
	 */
	public Object getNewValue() {
		return getInternalAPIImpl().getNewValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewModelElementValue()
	 */
	public EObject getNewModelElementValue() {
		return getInternalAPIImpl().getNewModelElementValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNotifier()
	 */
	public Object getNotifier() {
		return getInternalAPIImpl().getNotifier();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldBooleanValue()
	 */
	public boolean getOldBooleanValue() {
		return getInternalAPIImpl().getOldBooleanValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldByteValue()
	 */
	public byte getOldByteValue() {
		return getInternalAPIImpl().getOldByteValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldCharValue()
	 */
	public char getOldCharValue() {
		return getInternalAPIImpl().getOldCharValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldDoubleValue()
	 */
	public double getOldDoubleValue() {
		return getInternalAPIImpl().getOldDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldFloatValue()
	 */
	public float getOldFloatValue() {
		return getInternalAPIImpl().getOldFloatValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldIntValue()
	 */
	public int getOldIntValue() {
		return getInternalAPIImpl().getOldIntValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldLongValue()
	 */
	public long getOldLongValue() {
		return getInternalAPIImpl().getOldLongValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldShortValue()
	 */
	public short getOldShortValue() {
		return getInternalAPIImpl().getOldShortValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldStringValue()
	 */
	public String getOldStringValue() {
		return getInternalAPIImpl().getOldStringValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldValue()
	 */
	public Object getOldValue() {
		return getInternalAPIImpl().getOldValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldModelElementValue()
	 */
	public EObject getOldModelElementValue() {
		return getInternalAPIImpl().getOldModelElementValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getPosition()
	 */
	public int getPosition() {
		return getInternalAPIImpl().getPosition();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isReset()
	 */
	public boolean isReset() {
		return getInternalAPIImpl().isReset();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isTouch()
	 */
	public boolean isTouch() {
		return getInternalAPIImpl().isTouch();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#wasSet()
	 */
	public boolean wasSet() {
		return getInternalAPIImpl().wasSet();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNotifierModelElement()
	 */
	public EObject getNotifierModelElement() {
		return getInternalAPIImpl().getNotifierModelElement();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNotificationType()
	 */
	public Class<? extends Notification> getNotificationType() {
		return getInternalAPIImpl().getNotificationType();
	}

}
