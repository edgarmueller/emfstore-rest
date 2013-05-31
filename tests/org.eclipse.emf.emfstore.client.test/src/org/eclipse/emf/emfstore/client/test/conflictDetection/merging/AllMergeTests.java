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
package org.eclipse.emf.emfstore.client.test.conflictDetection.merging;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AttributeMergeTest.class, MultiAttributeMergeTest.class, SingleReferenceMergeTest.class,
	SingleReferenceVsMultiMergeTets.class, MultiReferenceMergeTest.class, MultiReferenceContainmentMergeTest.class,
	DeleteMergeTest.class, CompositeMergeTest.class })
public class AllMergeTests {

}
