/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * chodnick
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.conflictdetection.test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.client.test.common.cases.ESTest;
import org.eclipse.emf.emfstore.client.util.ESVoidCallable;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.emf.emfstore.internal.common.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ChangeConflictSet;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucket;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictDetector;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * Abstract super class for operation tests, contains setup.
 * 
 * @author chodnick
 */
public abstract class ConflictDetectionTest extends ESTest {

	/**
	 * Convenience method for conflict detection.
	 * 
	 * @param opA operation
	 * @param opB operation
	 * @return boolean
	 */
	protected boolean doConflict(AbstractOperation opA, AbstractOperation opB) {
		final ConflictDetector conflictDetector = new ConflictDetector();
		final ChangePackage changePackage1 = VersioningFactory.eINSTANCE.createChangePackage();
		changePackage1.getOperations().add(opA);
		final ChangePackage changePackage2 = VersioningFactory.eINSTANCE.createChangePackage();
		changePackage2.getOperations().add(opB);

		final ChangeConflictSet conflictSet = conflictDetector.calculateConflicts(Arrays.asList(changePackage1),
			Arrays.asList(changePackage2),
			ModelFactory.eINSTANCE.createProject());
		return conflictSet.getConflictBuckets().size() > 0;
	}

	public Set<AbstractOperation> getConflicts(final List<AbstractOperation> ops1, final List<AbstractOperation> ops2,
		Project project) {
		
		final ChangePackage changePackage1 = VersioningFactory.eINSTANCE.createChangePackage();
		final ChangePackage changePackage2 = VersioningFactory.eINSTANCE.createChangePackage();
		
		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				changePackage1.getOperations().addAll(ops1);
				changePackage2.getOperations().addAll(ops2);				
			}
		});
		
		final ChangeConflictSet conflicts = new ConflictDetector().calculateConflicts(Arrays.asList(changePackage1),
			Arrays.asList(changePackage2), project);
		final LinkedHashSet<AbstractOperation> result = new LinkedHashSet<AbstractOperation>();
		for (final ConflictBucket conflictBucket : conflicts.getConflictBuckets()) {
			final Set<AbstractOperation> myOperations = conflictBucket.getMyOperations();
			result.addAll(myOperations);
		}
		return result;
	}

	public Set<AbstractOperation> getConflicts(List<AbstractOperation> ops1, List<AbstractOperation> ops2) {
		return getConflicts(ops1, ops2, ModelFactory.eINSTANCE.createProject());
	}

	public <T extends AbstractOperation> AbstractOperation myCheckAndGetOperation(
		final Class<? extends AbstractOperation> clazz) {
		return new EMFStoreCommandWithResult<AbstractOperation>() {
			@Override
			protected AbstractOperation doRun() {
				return checkAndGetOperation(clazz);
			}
		}.run(false);
	}

	

}
