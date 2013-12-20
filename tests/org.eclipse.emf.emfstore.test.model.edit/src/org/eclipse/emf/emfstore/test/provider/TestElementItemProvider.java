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
package org.eclipse.emf.emfstore.test.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.emf.emfstore.test.model.TestElement;
import org.eclipse.emf.emfstore.test.model.TestmodelFactory;
import org.eclipse.emf.emfstore.test.model.TestmodelPackage;

import org.eclipse.emf.emfstore.test.model.provider.TestmodelEditPlugin;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.emfstore.test.model.TestElement} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TestElementItemProvider
	extends ItemProviderAdapter
	implements
	IEditingDomainItemProvider,
	IStructuredItemContentProvider,
	ITreeItemContentProvider,
	IItemLabelProvider,
	IItemPropertySource
{
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestElementItemProvider(AdapterFactory adapterFactory)
	{
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
	{
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addStringsPropertyDescriptor(object);
			addReferencesPropertyDescriptor(object);
			addReferencePropertyDescriptor(object);
			addOtherReferencePropertyDescriptor(object);
			addDescriptionPropertyDescriptor(object);
			addNonContained_NTo1PropertyDescriptor(object);
			addNonContained_1ToNPropertyDescriptor(object);
			addNonContained_NToMPropertyDescriptor(object);
			addNonContained_MToNPropertyDescriptor(object);
			addContainedElements_NoOppositePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TestElement_name_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TestElement_name_feature", "_UI_TestElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				TestmodelPackage.Literals.TEST_ELEMENT__NAME,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Strings feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStringsPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TestElement_strings_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TestElement_strings_feature", "_UI_TestElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				TestmodelPackage.Literals.TEST_ELEMENT__STRINGS,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the References feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addReferencesPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TestElement_references_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TestElement_references_feature", "_UI_TestElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				TestmodelPackage.Literals.TEST_ELEMENT__REFERENCES,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Reference feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addReferencePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TestElement_reference_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TestElement_reference_feature", "_UI_TestElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				TestmodelPackage.Literals.TEST_ELEMENT__REFERENCE,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Other Reference feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOtherReferencePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TestElement_otherReference_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TestElement_otherReference_feature", "_UI_TestElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				TestmodelPackage.Literals.TEST_ELEMENT__OTHER_REFERENCE,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TestElement_description_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TestElement_description_feature", "_UI_TestElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				TestmodelPackage.Literals.TEST_ELEMENT__DESCRIPTION,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Non Contained NTo1 feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNonContained_NTo1PropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TestElement_nonContained_NTo1_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TestElement_nonContained_NTo1_feature", "_UI_TestElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				TestmodelPackage.Literals.TEST_ELEMENT__NON_CONTAINED_NTO1,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Non Contained 1To N feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNonContained_1ToNPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TestElement_nonContained_1ToN_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TestElement_nonContained_1ToN_feature", "_UI_TestElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				TestmodelPackage.Literals.TEST_ELEMENT__NON_CONTAINED_1TO_N,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Non Contained NTo M feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNonContained_NToMPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TestElement_nonContained_NToM_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TestElement_nonContained_NToM_feature", "_UI_TestElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				TestmodelPackage.Literals.TEST_ELEMENT__NON_CONTAINED_NTO_M,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Non Contained MTo N feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNonContained_MToNPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TestElement_nonContained_MToN_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TestElement_nonContained_MToN_feature", "_UI_TestElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				TestmodelPackage.Literals.TEST_ELEMENT__NON_CONTAINED_MTO_N,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Contained Elements No Opposite feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addContainedElements_NoOppositePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TestElement_containedElements_NoOpposite_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TestElement_containedElements_NoOpposite_feature", "_UI_TestElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				TestmodelPackage.Literals.TEST_ELEMENT__CONTAINED_ELEMENTS_NO_OPPOSITE,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object)
	{
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(TestmodelPackage.Literals.TEST_ELEMENT__CONTAINED_ELEMENTS);
			childrenFeatures.add(TestmodelPackage.Literals.TEST_ELEMENT__CONTAINED_ELEMENT);
			childrenFeatures.add(TestmodelPackage.Literals.TEST_ELEMENT__ELEMENT_MAP);
			childrenFeatures.add(TestmodelPackage.Literals.TEST_ELEMENT__STRING_TO_STRING_MAP);
			childrenFeatures.add(TestmodelPackage.Literals.TEST_ELEMENT__ELEMENT_TO_STRING_MAP);
			childrenFeatures.add(TestmodelPackage.Literals.TEST_ELEMENT__STRING_TO_ELEMENT_MAP);
			childrenFeatures.add(TestmodelPackage.Literals.TEST_ELEMENT__CONTAINED_ELEMENTS2);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child)
	{
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns TestElement.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TestElement")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object)
	{
		String label = ((TestElement) object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_TestElement_type") : //$NON-NLS-1$
			getString("_UI_TestElement_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification)
	{
		updateChildren(notification);

		switch (notification.getFeatureID(TestElement.class))
		{
		case TestmodelPackage.TEST_ELEMENT__NAME:
		case TestmodelPackage.TEST_ELEMENT__STRINGS:
		case TestmodelPackage.TEST_ELEMENT__DESCRIPTION:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS:
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENT:
		case TestmodelPackage.TEST_ELEMENT__ELEMENT_MAP:
		case TestmodelPackage.TEST_ELEMENT__STRING_TO_STRING_MAP:
		case TestmodelPackage.TEST_ELEMENT__ELEMENT_TO_STRING_MAP:
		case TestmodelPackage.TEST_ELEMENT__STRING_TO_ELEMENT_MAP:
		case TestmodelPackage.TEST_ELEMENT__CONTAINED_ELEMENTS2:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
	{
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
			(TestmodelPackage.Literals.TEST_ELEMENT__CONTAINED_ELEMENTS,
				TestmodelFactory.eINSTANCE.createTestElement()));

		newChildDescriptors.add
			(createChildParameter
			(TestmodelPackage.Literals.TEST_ELEMENT__CONTAINED_ELEMENT,
				TestmodelFactory.eINSTANCE.createTestElement()));

		newChildDescriptors.add
			(createChildParameter
			(TestmodelPackage.Literals.TEST_ELEMENT__ELEMENT_MAP,
				TestmodelFactory.eINSTANCE.create(TestmodelPackage.Literals.TEST_ELEMENT_TO_TEST_ELEMENT_MAP)));

		newChildDescriptors.add
			(createChildParameter
			(TestmodelPackage.Literals.TEST_ELEMENT__STRING_TO_STRING_MAP,
				TestmodelFactory.eINSTANCE.create(TestmodelPackage.Literals.STRING_TO_STRING_MAP)));

		newChildDescriptors.add
			(createChildParameter
			(TestmodelPackage.Literals.TEST_ELEMENT__ELEMENT_TO_STRING_MAP,
				TestmodelFactory.eINSTANCE.create(TestmodelPackage.Literals.TEST_ELEMENT_TO_STRING_MAP)));

		newChildDescriptors.add
			(createChildParameter
			(TestmodelPackage.Literals.TEST_ELEMENT__STRING_TO_ELEMENT_MAP,
				TestmodelFactory.eINSTANCE.create(TestmodelPackage.Literals.STRING_TO_TEST_ELEMENT_MAP)));

		newChildDescriptors.add
			(createChildParameter
			(TestmodelPackage.Literals.TEST_ELEMENT__CONTAINED_ELEMENTS2,
				TestmodelFactory.eINSTANCE.createTestElement()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection)
	{
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == TestmodelPackage.Literals.TEST_ELEMENT__CONTAINED_ELEMENTS ||
				childFeature == TestmodelPackage.Literals.TEST_ELEMENT__CONTAINED_ELEMENT ||
				childFeature == TestmodelPackage.Literals.TEST_ELEMENT__CONTAINED_ELEMENTS2;

		if (qualify)
		{
			return getString("_UI_CreateChild_text2", //$NON-NLS-1$
				new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator()
	{
		return TestmodelEditPlugin.INSTANCE;
	}

}
