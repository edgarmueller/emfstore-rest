package org.eclipse.emf.emfstore.client.test.conflictDetection.merging;

import static java.util.Arrays.asList;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceSetSingleConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceSingleConflict;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiReferenceSetOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.SingleReferenceOperation;
import org.junit.Test;

/**
 * These only conflict through <b>containment</b> side effects.
 * 
 * @author wesendon
 */
public class SingleReferenceVsMultiMergeTets extends MergeTest {

	@Test
	public void setVsMultiAdd() {
		final TestElement parent = createTestElement();
		final TestElement secondparent = createTestElement();
		final TestElement child = createTestElement();

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
		final TestElement parent = createTestElement();
		final TestElement secondparent = createTestElement();
		final TestElement child = createTestElement();
		final TestElement secondChild = createTestElement();

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
		final TestElement parent = createTestElement();
		final TestElement secondparent = createTestElement();
		final TestElement child = createTestElement();
		final TestElement secondChild = createTestElement();

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
		final TestElement parent = createTestElement();
		final TestElement secondparent = createTestElement();
		secondparent.getContainedElements().add(createTestElement());
		final TestElement child = createTestElement();

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

		mc.hasConflict(MultiReferenceSetSingleConflict.class)
		// My
			.myIs(SingleReferenceOperation.class).andNoOtherMyOps()
			// Theirs
			.theirsIs(MultiReferenceSetOperation.class).andNoOtherTheirOps();
	}

}
