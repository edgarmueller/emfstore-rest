/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.observerbus.assets;

public class BImpl implements B {

	public int returnTwo() {
		return 2;
	}

	public void setMSGToFoo(CImpl tester) {
		tester.msg = "foo";
	}

	public String returnFoobarOrException() {
		throw new IllegalArgumentException();
	}

}
