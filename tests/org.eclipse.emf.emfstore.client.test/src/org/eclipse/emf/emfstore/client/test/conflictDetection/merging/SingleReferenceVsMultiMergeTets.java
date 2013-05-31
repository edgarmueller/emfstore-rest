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
package org.eclipse.emf.emfstore.client.test.conflictDetection.merging;

import static java.util.Arrays.asList;

import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts.DeletionConflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceSingleConflict;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;
import org.junit.Test;

/**
 * These only conflict through <b>containment</b> side effects.
 * 
 * @author wesendon
 */
public class SingleReferenceVsMultiMergeTets extends MergeTest {

	@Test
	public void setVsMultiAdd() {
		final TestElement parent = getTestElement();
		final TestElement secondparent = getTestElement();
		final TestElement child = getTestElement();

		final MergeCase mc = newMergeCase(parent, secondparent, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).setContainedElement(child);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(secondparent).getContainedElements().add(mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceSingleConflict.class)
			// My
			.myIs(SingleReferenceOperation.class).andNoOtherMyOps()
			// Theirs
			.theirsIs(MultiReferenceOperation.class).andNoOtherTheirOps();
	}

	@Test
	public void setVsMultiAddNC() {
		final TestElement parent = getTestElement();
		final TestElement secondparent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement secondChild = getTestElement();

		final MergeCase mc = newMergeCase(parent, secondparent, child, secondChild);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).setContainedElement(child);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(secondparent).getContainedElements().add(mc.getTheirItem(secondChild));
			}
		}.run(false);

		mc.hasConflict(null);
	}

	@Test
	public void setVsMultiAddMany() {
		final TestElement parent = getTestElement();
		final TestElement secondparent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement secondChild = getTestElement();

		final MergeCase mc = newMergeCase(parent, secondparent, child, secondChild);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).setContainedElement(child);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(secondparent).getContainedElements()
					.addAll(asList(mc.getTheirItem(child), mc.getTheirItem(secondChild)));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceSingleConflict.class)
			// My
			.myIs(SingleReferenceOperation.class).andNoOtherMyOps()
			// Theirs
			.theirsIs(MultiReferenceOperation.class).andNoOtherTheirOps();
	}

	@Test
	public void setVsMultiSet() {
		final TestElement parent = getTestElement();
		final TestElement secondparent = getTestElement();
		secondparent.getContainedElements().add(getTestElement());
		final TestElement child = getTestElement();

		final MergeCase mc = newMergeCase(parent, secondparent, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).setContainedElement(child);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(secondparent).getContainedElements().set(0, mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(DeletionConflict.class)
			// My
			.myIs(SingleReferenceOperation.class).andNoOtherMyOps()
			// Theirs
			.theirsIs(CreateDeleteOperation.class);
	}

}