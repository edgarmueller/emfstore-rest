/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * jsommerfeldt
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
		return toInternalAPI().getStructuralFeature();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isValid()
	 */
	public boolean isValid() {
		return toInternalAPI().isValid();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getValidationMessage()
	 */
	public String getValidationMessage() {
		return toInternalAPI().getValidationMessage();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isAttributeNotification()
	 */
	public boolean isAttributeNotification() {
		return toInternalAPI().isAttributeNotification();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isReferenceNotification()
	 */
	public boolean isReferenceNotification() {
		return toInternalAPI().isReferenceNotification();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getAttribute()
	 */
	public EAttribute getAttribute() {
		return toInternalAPI().getAttribute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getReference()
	 */
	public EReference getReference() {
		return toInternalAPI().getReference();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isTransient()
	 */
	public boolean isTransient() {
		return toInternalAPI().isTransient();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isAddEvent()
	 */
	public boolean isAddEvent() {
		return toInternalAPI().isAddEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isRemoveEvent()
	 */
	public boolean isRemoveEvent() {
		return toInternalAPI().isRemoveEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isSetEvent()
	 */
	public boolean isSetEvent() {
		return toInternalAPI().isSetEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isAddManyEvent()
	 */
	public boolean isAddManyEvent() {
		return toInternalAPI().isAddManyEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isRemoveManyEvent()
	 */
	public boolean isRemoveManyEvent() {
		return toInternalAPI().isRemoveManyEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isMoveEvent()
	 */
	public boolean isMoveEvent() {
		return toInternalAPI().isMoveEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#hasNext()
	 */
	public boolean hasNext() {
		return toInternalAPI().hasNext();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getEventType()
	 */
	public int getEventType() {
		return toInternalAPI().getEventType();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getFeature()
	 */
	public Object getFeature() {
		return toInternalAPI().getFeature();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getFeatureID(java.lang.Class)
	 */
	public int getFeatureID(Class<?> expectedClass) {
		return toInternalAPI().getFeatureID(expectedClass);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewBooleanValue()
	 */
	public boolean getNewBooleanValue() {
		return toInternalAPI().getNewBooleanValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewByteValue()
	 */
	public byte getNewByteValue() {
		return toInternalAPI().getNewByteValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewCharValue()
	 */
	public char getNewCharValue() {
		return toInternalAPI().getNewCharValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewDoubleValue()
	 */
	public double getNewDoubleValue() {
		return toInternalAPI().getNewDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewFloatValue()
	 */
	public float getNewFloatValue() {
		return toInternalAPI().getNewFloatValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewIntValue()
	 */
	public int getNewIntValue() {
		return toInternalAPI().getNewIntValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewLongValue()
	 */
	public long getNewLongValue() {
		return toInternalAPI().getNewLongValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewShortValue()
	 */
	public short getNewShortValue() {
		return toInternalAPI().getNewShortValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewStringValue()
	 */
	public String getNewStringValue() {
		return toInternalAPI().getNewStringValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewValue()
	 */
	public Object getNewValue() {
		return toInternalAPI().getNewValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNewModelElementValue()
	 */
	public EObject getNewModelElementValue() {
		return toInternalAPI().getNewModelElementValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNotifier()
	 */
	public Object getNotifier() {
		return toInternalAPI().getNotifier();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldBooleanValue()
	 */
	public boolean getOldBooleanValue() {
		return toInternalAPI().getOldBooleanValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldByteValue()
	 */
	public byte getOldByteValue() {
		return toInternalAPI().getOldByteValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldCharValue()
	 */
	public char getOldCharValue() {
		return toInternalAPI().getOldCharValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldDoubleValue()
	 */
	public double getOldDoubleValue() {
		return toInternalAPI().getOldDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldFloatValue()
	 */
	public float getOldFloatValue() {
		return toInternalAPI().getOldFloatValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldIntValue()
	 */
	public int getOldIntValue() {
		return toInternalAPI().getOldIntValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldLongValue()
	 */
	public long getOldLongValue() {
		return toInternalAPI().getOldLongValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldShortValue()
	 */
	public short getOldShortValue() {
		return toInternalAPI().getOldShortValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldStringValue()
	 */
	public String getOldStringValue() {
		return toInternalAPI().getOldStringValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldValue()
	 */
	public Object getOldValue() {
		return toInternalAPI().getOldValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getOldModelElementValue()
	 */
	public EObject getOldModelElementValue() {
		return toInternalAPI().getOldModelElementValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getPosition()
	 */
	public int getPosition() {
		return toInternalAPI().getPosition();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isReset()
	 */
	public boolean isReset() {
		return toInternalAPI().isReset();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#isTouch()
	 */
	public boolean isTouch() {
		return toInternalAPI().isTouch();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#wasSet()
	 */
	public boolean wasSet() {
		return toInternalAPI().wasSet();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNotifierModelElement()
	 */
	public EObject getNotifierModelElement() {
		return toInternalAPI().getNotifierModelElement();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ESNotificationInfo#getNotificationType()
	 */
	public Class<? extends Notification> getNotificationType() {
		return toInternalAPI().getNotificationType();
	}

}
