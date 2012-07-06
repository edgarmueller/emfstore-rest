package org.eclipse.emf.emfstore.client.test.conflictDetection.merging;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.SingleReferenceConflict;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.server.model.versioning.operations.SingleReferenceOperation;
import org.junit.Test;

public class SingleReferenceMergeTest extends MergeTest {

	@Test
	public void setSameTarget() {
		final TestElement target = createTestElement("target");
		final TestElement myLink = createTestElement("myLink");
		final TestElement theirLink = createTestElement("theirLink");

		final MergeCase mc = newMergeCase(target, myLink, theirLink);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(target).setReference(myLink);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(target).setReference(mc.getTheirItem(theirLink));
			}
		}.run(false);

		mc.hasConflict(SingleReferenceConflict.class)
		// My
			.myIs(SingleReferenceOperation.class).andReturns("getNewValue", getId(myLink)).andNoOtherMyOps()
			// Theirs
			.theirsIs(SingleReferenceOperation.class).andReturns("getNewValue", getId(theirLink)).andNoOtherTheirOps();
	}

	@Test
	public void setSameDifferentTarget() {
		final TestElement target = createTestElement();
		final TestElement secondTarget = createTestElement();
		final TestElement link = createTestElement();

		final MergeCase mc = newMergeCase(target, secondTarget, link);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(target).setReference(link);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(secondTarget).setReference(mc.getTheirItem(link));
			}
		}.run(false);

		mc.hasConflict(null);
	}

	@Test
	public void setSameTargetWithNoise() {
		final TestElement target = createTestElement();
		final TestElement decoy = createTestElement();
		final TestElement myLink = createTestElement();
		final TestElement theirLink = createTestElement();

		final MergeCase mc = newMergeCase(target, decoy, myLink, theirLink);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(decoy).setReference(myLink);
				mc.getMyItem(target).setReference(myLink);
				mc.getMyItem(decoy).setOtherReference(myLink);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(target).setOtherReference(mc.getTheirItem(theirLink));
				mc.getTheirItem(target).setReference(mc.getTheirItem(theirLink));
			}
		}.run(false);

		mc.hasConflict(SingleReferenceConflict.class)
		// My
			.myIs(SingleReferenceOperation.class).andReturns("getNewValue", getId(myLink)).andNoOtherMyOps()
			// Theirs
			.theirsIs(SingleReferenceOperation.class).andReturns("getNewValue", getId(theirLink)).andNoOtherTheirOps();
	}

	/**
	 * CONTAINMENT TESTs
	 */

	@Test
	public void setSameTargetContainment() {
		final TestElement target = createTestElement("target");
		final TestElement myLink = createTestElement("myLink");
		final TestElement theirLink = createTestElement("theirLink");

		final MergeCase mc = newMergeCase(target, myLink, theirLink);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(target).setContainedElement(myLink);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(target).setContainedElement(mc.getTheirItem(theirLink));
			}
		}.run(false);

		mc.hasConflict(SingleReferenceConflict.class)
		// My
			.myIs(SingleReferenceOperation.class).andReturns("getNewValue", getId(myLink)).andNoOtherMyOps()
			// Theirs
			.theirsIs(SingleReferenceOperation.class).andReturns("getNewValue", getId(theirLink)).andNoOtherTheirOps();
	}

	@Test
	public void setToDifferentParents() {
		final TestElement parent = createTestElement();
		final TestElement secondParent = createTestElement();
		final TestElement child = createTestElement();

		final MergeCase mc = newMergeCase(parent, secondParent, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).setContainedElement(child);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(secondParent).setContainedElement(mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(SingleReferenceConflict.class)
		//
			.myIs(SingleReferenceOperation.class).andReturns("getNewValue", getId(child)).andNoOtherMyOps()
			// Theirs
			.theirsIs(SingleReferenceOperation.class).andReturns("getNewValue", getId(child)).andNoOtherTheirOps();
	}

	@Test
	public void setToParentAndGrandparent() {
		final TestElement grandParent = createTestElement();
		final TestElement parent = createTestElement();
		final TestElement child = createTestElement();

		final MergeCase mc = newMergeCase(grandParent, parent, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).setContainedElement(child);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(grandParent).setContainedElement(mc.getTheirItem(parent));
			}
		}.run(false);

		mc.hasConflict(null);
	}

}
