/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.dsl;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.emfstore.test.model.TestmodelPackage;

public final class TestElementFeatures {

	private TestElementFeatures() {

	}

	public static EStructuralFeature name() {
		return TestmodelPackage.eINSTANCE.getTestElement_Name();
	}

	public static EStructuralFeature container() {
		return TestmodelPackage.eINSTANCE.getTestElement_Container();
	}

	public static EStructuralFeature containedElements() {
		return TestmodelPackage.eINSTANCE.getTestElement_ContainedElements();
	}

	public static EStructuralFeature containedElement() {
		return TestmodelPackage.eINSTANCE.getTestElement_ContainedElement();
	}

	public static EStructuralFeature srefContainer() {
		return TestmodelPackage.eINSTANCE.getTestElement_SrefContainer();
	}

	public static EStructuralFeature containedElements2() {
		return TestmodelPackage.eINSTANCE.getTestElement_ContainedElements2();
	}

	public static EStructuralFeature container2() {
		return TestmodelPackage.eINSTANCE.getTestElement_Container2();
	}

	public static EStructuralFeature nonContainedNToM() {
		return TestmodelPackage.eINSTANCE.getTestElement_NonContained_NToM();
	}

	public static EStructuralFeature nonContainedMToN() {
		return TestmodelPackage.eINSTANCE.getTestElement_NonContained_MToN();
	}

	public static EStructuralFeature nonContained1ToN() {
		return TestmodelPackage.eINSTANCE.getTestElement_NonContained_1ToN();
	}

	public static EStructuralFeature nonContainedNTo1() {
		return TestmodelPackage.eINSTANCE.getTestElement_NonContained_NTo1();
	}

	public static EStructuralFeature description() {
		return TestmodelPackage.eINSTANCE.getTestElement_Description();
	}

}
