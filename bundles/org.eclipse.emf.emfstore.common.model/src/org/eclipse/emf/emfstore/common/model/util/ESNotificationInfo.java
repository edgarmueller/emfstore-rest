/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Julian Sommerfeldt - initial API and implementation
 * Edgar Mueller - API annotations
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.model.util;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.emfstore.internal.common.model.util.NotificationInfo;

/**
 * A wrapper type around {@link NotificationInfo} that adds
 * some convenience methods.
 * 
 * @author jsommerfeldt
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ESNotificationInfo {

	/**
	 * @return the structural feature affected
	 */
	EStructuralFeature getStructuralFeature();

	/**
	 * @return the valid
	 */
	boolean isValid();

	/**
	 * @return the validationMessage
	 */
	String getValidationMessage();

	/**
	 * @return whether this notification signals a change of an attribute value
	 */
	boolean isAttributeNotification();

	/**
	 * @return whether this notification signals a change of a reference value
	 */
	boolean isReferenceNotification();

	/**
	 * @return the EAttribute if the notification relates to an attribute, null otherwise
	 */
	EAttribute getAttribute();

	/**
	 * @return the EReference if the notification relates to a reference feature, null otherwise
	 */
	EReference getReference();

	/**
	 * @return true if the changed feature is marked transient, false otherwise
	 */
	boolean isTransient();

	/**
	 * @return true if the event is of type Notification.ADD, false otherwise
	 */
	boolean isAddEvent();

	/**
	 * @return true if the event is of type Notification.REMOVE, false otherwise
	 */

	boolean isRemoveEvent();

	/**
	 * @return true if the event is of type Notification.SET, false otherwise
	 */

	boolean isSetEvent();

	/**
	 * @return true if the event is of type Notification.ADD_MANY, false otherwise
	 */

	boolean isAddManyEvent();

	/**
	 * @return true if the event is of type Notification.REMOVE_MANY, false otherwise
	 */

	boolean isRemoveManyEvent();

	/**
	 * @return true if the event is of type Notification.MOVE, false otherwise
	 */
	boolean isMoveEvent();

	/**
	 * @return true if this notification is followed by more notifications in a chain, false if this is the last
	 *         notification of a chain
	 */

	boolean hasNext();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getEventType()
	 */
	int getEventType();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getFeature()
	 */
	Object getFeature();

	/**
	 * @param expectedClass @see org.eclipse.emf.common.notify.Notification#getFeatureID(java.lang.Class)
	 * @return @see org.eclipse.emf.common.notify.Notification#getFeatureID(java.lang.Class)
	 */
	int getFeatureID(Class<?> expectedClass);

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewBooleanValue()
	 */

	boolean getNewBooleanValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewByteValue()
	 */
	byte getNewByteValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewCharValue()
	 */
	char getNewCharValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewDoubleValue()
	 */

	double getNewDoubleValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewFloatValue()
	 */
	float getNewFloatValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewIntValue()
	 */
	int getNewIntValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewLongValue()
	 */
	long getNewLongValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewShortValue()
	 */
	short getNewShortValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewStringValue()
	 */
	String getNewStringValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewValue()
	 */
	Object getNewValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewValue()
	 */
	EObject getNewModelElementValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNotifier()
	 */
	Object getNotifier();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldBooleanValue()
	 */
	boolean getOldBooleanValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldByteValue()
	 */
	byte getOldByteValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldCharValue()
	 */
	char getOldCharValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldDoubleValue()
	 */
	double getOldDoubleValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldFloatValue()
	 */
	float getOldFloatValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldIntValue()
	 */
	int getOldIntValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldLongValue()
	 */
	long getOldLongValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldShortValue()
	 */
	short getOldShortValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldStringValue()
	 */
	String getOldStringValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldValue()
	 */
	Object getOldValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldValue()
	 */
	EObject getOldModelElementValue();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getPosition()
	 */
	int getPosition();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#isReset()
	 */
	boolean isReset();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#isTouch()
	 */
	boolean isTouch();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#wasSet()
	 */
	boolean wasSet();

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNotifier()
	 */
	EObject getNotifierModelElement();

	/**
	 * Returns the type of the {@link Notification}.
	 * 
	 * @return a {@link Notification} type
	 */
	Class<? extends Notification> getNotificationType();
}
