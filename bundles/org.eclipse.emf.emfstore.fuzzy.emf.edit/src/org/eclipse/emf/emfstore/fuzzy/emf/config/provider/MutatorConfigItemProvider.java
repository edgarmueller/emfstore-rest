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
package org.eclipse.emf.emfstore.fuzzy.emf.config.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
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
import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage;
import org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class MutatorConfigItemProvider extends ItemProviderAdapter implements
	IEditingDomainItemProvider, IStructuredItemContentProvider,
	ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MutatorConfigItemProvider(AdapterFactory adapterFactory) {
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

			addRootEClassPropertyDescriptor(object);
			addMinObjectsCountPropertyDescriptor(object);
			addIgnoreAndLogPropertyDescriptor(object);
			addDoNotGenerateRootPropertyDescriptor(object);
			addUseEcoreUtilDeletePropertyDescriptor(object);
			addEClassesToIgnorePropertyDescriptor(object);
			addEStructuralFeaturesToIgnorePropertyDescriptor(object);
			addEPackagesPropertyDescriptor(object);
			addMaxDeleteCountPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Root EClass feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addRootEClassPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory)
				.getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_MutatorConfig_rootEClass_feature"),
			getString("_UI_PropertyDescriptor_description",
				"_UI_MutatorConfig_rootEClass_feature",
				"_UI_MutatorConfig_type"),
			ConfigPackage.Literals.MUTATOR_CONFIG__ROOT_ECLASS, true,
			false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Min Objects Count feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMinObjectsCountPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory)
				.getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_MutatorConfig_minObjectsCount_feature"),
			getString("_UI_PropertyDescriptor_description",
				"_UI_MutatorConfig_minObjectsCount_feature",
				"_UI_MutatorConfig_type"),
			ConfigPackage.Literals.MUTATOR_CONFIG__MIN_OBJECTS_COUNT, true,
			false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
			null, null));
	}

	/**
	 * This adds a property descriptor for the Ignore And Log feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addIgnoreAndLogPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory)
				.getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_MutatorConfig_ignoreAndLog_feature"),
			getString("_UI_PropertyDescriptor_description",
				"_UI_MutatorConfig_ignoreAndLog_feature",
				"_UI_MutatorConfig_type"),
			ConfigPackage.Literals.MUTATOR_CONFIG__IGNORE_AND_LOG, true,
			false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null,
			null));
	}

	/**
	 * This adds a property descriptor for the Do Not Generate Root feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addDoNotGenerateRootPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory)
				.getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_MutatorConfig_doNotGenerateRoot_feature"),
			getString("_UI_PropertyDescriptor_description",
				"_UI_MutatorConfig_doNotGenerateRoot_feature",
				"_UI_MutatorConfig_type"),
			ConfigPackage.Literals.MUTATOR_CONFIG__DO_NOT_GENERATE_ROOT,
			true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
			null, null));
	}

	/**
	 * This adds a property descriptor for the Use Ecore Util Delete feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addUseEcoreUtilDeletePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory)
				.getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_MutatorConfig_useEcoreUtilDelete_feature"),
			getString("_UI_PropertyDescriptor_description",
				"_UI_MutatorConfig_useEcoreUtilDelete_feature",
				"_UI_MutatorConfig_type"),
			ConfigPackage.Literals.MUTATOR_CONFIG__USE_ECORE_UTIL_DELETE,
			true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
			null, null));
	}

	/**
	 * This adds a property descriptor for the EClasses To Ignore feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addEClassesToIgnorePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory)
				.getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_MutatorConfig_eClassesToIgnore_feature"),
			getString("_UI_PropertyDescriptor_description",
				"_UI_MutatorConfig_eClassesToIgnore_feature",
				"_UI_MutatorConfig_type"),
			ConfigPackage.Literals.MUTATOR_CONFIG__ECLASSES_TO_IGNORE,
			true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the EStructural Features To Ignore
	 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addEStructuralFeaturesToIgnorePropertyDescriptor(
		Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
					.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_MutatorConfig_eStructuralFeaturesToIgnore_feature"),
				getString(
					"_UI_PropertyDescriptor_description",
					"_UI_MutatorConfig_eStructuralFeaturesToIgnore_feature",
					"_UI_MutatorConfig_type"),
				ConfigPackage.Literals.MUTATOR_CONFIG__ESTRUCTURAL_FEATURES_TO_IGNORE,
				true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the EPackages feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addEPackagesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory)
				.getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_MutatorConfig_ePackages_feature"),
			getString("_UI_PropertyDescriptor_description",
				"_UI_MutatorConfig_ePackages_feature",
				"_UI_MutatorConfig_type"),
			ConfigPackage.Literals.MUTATOR_CONFIG__EPACKAGES, true, false,
			true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Max Delete Count feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMaxDeleteCountPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory)
				.getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_MutatorConfig_maxDeleteCount_feature"),
			getString("_UI_PropertyDescriptor_description",
				"_UI_MutatorConfig_maxDeleteCount_feature",
				"_UI_MutatorConfig_type"),
			ConfigPackage.Literals.MUTATOR_CONFIG__MAX_DELETE_COUNT, true,
			false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null,
			null));
	}

	/**
	 * This returns MutatorConfig.gif. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object,
			getResourceLocator().getImage("full/obj16/MutatorConfig"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		MutatorConfig mutatorConfig = (MutatorConfig) object;
		return getString("_UI_MutatorConfig_type") + " "
			+ mutatorConfig.getMinObjectsCount();
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

		switch (notification.getFeatureID(MutatorConfig.class)) {
		case ConfigPackage.MUTATOR_CONFIG__MIN_OBJECTS_COUNT:
		case ConfigPackage.MUTATOR_CONFIG__IGNORE_AND_LOG:
		case ConfigPackage.MUTATOR_CONFIG__DO_NOT_GENERATE_ROOT:
		case ConfigPackage.MUTATOR_CONFIG__USE_ECORE_UTIL_DELETE:
		case ConfigPackage.MUTATOR_CONFIG__MAX_DELETE_COUNT:
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

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ConfigEditPlugin.INSTANCE;
	}

}