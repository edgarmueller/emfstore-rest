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
package org.eclipse.emf.emfstore.server.model.versioning.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.emfstore.server.model.versioning.RangeQuery;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.emfstore.server.model.versioning.RangeQuery} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class RangeQueryItemProvider extends HistoryQueryItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider,
		ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RangeQueryItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addUpperLimitPropertyDescriptor(object);
			addLowerLimitPropertyDescriptor(object);
			addIncludeIncomingPropertyDescriptor(object);
			addIncludeOutgoingPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Upper Limit feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addUpperLimitPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_RangeQuery_upperLimit_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_RangeQuery_upperLimit_feature",
						"_UI_RangeQuery_type"),
				VersioningPackage.Literals.RANGE_QUERY__UPPER_LIMIT, true,
				false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Lower Limit feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addLowerLimitPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_RangeQuery_lowerLimit_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_RangeQuery_lowerLimit_feature",
						"_UI_RangeQuery_type"),
				VersioningPackage.Literals.RANGE_QUERY__LOWER_LIMIT, true,
				false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Include Incoming feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addIncludeIncomingPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_RangeQuery_includeIncoming_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_RangeQuery_includeIncoming_feature",
						"_UI_RangeQuery_type"),
				VersioningPackage.Literals.RANGE_QUERY__INCLUDE_INCOMING, true,
				false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null,
				null));
	}

	/**
	 * This adds a property descriptor for the Include Outgoing feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addIncludeOutgoingPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_RangeQuery_includeOutgoing_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_RangeQuery_includeOutgoing_feature",
						"_UI_RangeQuery_type"),
				VersioningPackage.Literals.RANGE_QUERY__INCLUDE_OUTGOING, true,
				false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null,
				null));
	}

	/**
	 * This returns RangeQuery.gif. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object,
				getResourceLocator().getImage("full/obj16/RangeQuery"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		RangeQuery rangeQuery = (RangeQuery) object;
		return getString("_UI_RangeQuery_type") + " "
				+ rangeQuery.isIncludeChangePackages();
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(RangeQuery.class)) {
		case VersioningPackage.RANGE_QUERY__UPPER_LIMIT:
		case VersioningPackage.RANGE_QUERY__LOWER_LIMIT:
		case VersioningPackage.RANGE_QUERY__INCLUDE_INCOMING:
		case VersioningPackage.RANGE_QUERY__INCLUDE_OUTGOING:
			fireNotifyChanged(new ViewerNotification(notification,
					notification.getNotifier(), false, true));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(
			Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
