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
package org.eclipse.emf.emfstore.client.ui.epackages;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

/**
 * The Class EPackageTreeSelectionDialog.
 * 
 * @author Tobias Verhoeven
 */
public class EPackageTreeSelectionDialog extends ElementTreeSelectionDialog {

	/**
	 * Instantiates a new e package tree selection dialog.
	 * 
	 * @param modelElements the model elements
	 */
	public EPackageTreeSelectionDialog(Set<EPackage> modelElements) {
		super(null, new AdapterFactoryLabelProvider(new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE)), new EPackageTreeContentProvider());
		setTitle("Select EPackage");
		setMessage("Available EPackages");
		this.setComparator(new ViewerComparator());
		setInput(modelElements);
	}

	/**
	 * Returns the selected EPackage.
	 * 
	 * @return the selected EPackage or null if nothing was selected.
	 */
	public EPackage getSelectedEPackage() {
		EPackage result = null;
		if (getResult() != null && getResult().length > 0 && getResult()[0] != null) {
			result = (EPackage) getResult()[0];
		}
		return result;
	}

	/**
	 * EPackageTreeContentProvider - TreeContentProvider for EPackages.
	 */
	private static class EPackageTreeContentProvider extends AdapterFactoryContentProvider {

		public EPackageTreeContentProvider() {
			super(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		}

		private Set<EPackage> rootPackages;

		private void extractRootPackages(Set<EPackage> packages) {
			for (EPackage pkg : packages) {
				extractAllSuperPackages(pkg, packages);
			}
		}

		private void extractAllSuperPackages(EPackage ePackage, Set<EPackage> packages) {
			EPackage eSuperPackage = ePackage.getESuperPackage();
			if (eSuperPackage == null) {
				if (packages.contains(ePackage)) {
					rootPackages.add(ePackage);
				}
				return;
			}
			extractAllSuperPackages(eSuperPackage, packages);
		}

		/**
		 * {@inheritDoc} Return an array of sub-packages of Model package.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Object[] getElements(Object inputElement) {
			if (rootPackages == null) {
				rootPackages = new HashSet<EPackage>();
				this.extractRootPackages((Set<EPackage>) inputElement);
			}
			return rootPackages.toArray();
		}

		/**
		 * {@inheritDoc} Shows the children only when argument is an EPackage. Also doesn't show the Children that are.
		 * abstract or not ModelElement.
		 */
		@Override
		public Object[] getChildren(Object object) {
			if (object instanceof EPackage) {
				return ((EPackage) object).getESubpackages().toArray();
			}
			return null;
		}

		/**
		 * {@inheritDoc} If argument is an EClass return false. This is to prevent showing of the plus sign beside an.
		 * EClass in TreeViewer
		 */
		@Override
		public boolean hasChildren(Object object) {
			if (object instanceof EPackage) {
				if (!((EPackage) object).getESubpackages().isEmpty()) {
					return true;
				}
			}
			return false;
		}
	}

}