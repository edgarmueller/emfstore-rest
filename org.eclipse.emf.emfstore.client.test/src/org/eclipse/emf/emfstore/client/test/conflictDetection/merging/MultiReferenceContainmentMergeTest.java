package org.eclipse.emf.emfstore.client.test.conflictDetection.merging;

import static java.util.Arrays.asList;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceSetConflict;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceSetSetConflict;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.MultiReferenceSetOperation;
import org.junit.Test;

public class MultiReferenceContainmentMergeTest extends MergeTest {

	@Test
	public void addSameToDifferent() {
		final TestElement parent = createTestElement();
		final TestElement parent2 = createTestElement();
		final TestElement child = createTestElement();

		final MergeCase mc = newMergeCase(parent, parent2, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getContainedElements().add(mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent2).getContainedElements().add(mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceConflict.class)
		// My
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", true).andNoOtherMyOps()
			// Theirs
			.theirsIs(MultiReferenceOperation.class).andReturns("isAdd", true).andNoOtherTheirOps();
	}

	@Test
	public void addSameToSameNC() {
		final TestElement parent = createTestElement();
		final TestElement child = createTestElement();

		final MergeCase mc = newMergeCase(parent, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getContainedElements().add(mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getContainedElements().add(mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(null);
	}

	@Test
	public void addSameManyToDifferent() {
		final TestElement parent = createTestElement();
		final TestElement parent2 = createTestElement();
		final TestElement child = createTestElement();
		final TestElement child2 = createTestElement();

		final MergeCase mc = newMergeCase(parent, parent2, child, child2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getContainedElements().addAll(asList(mc.getMyItem(child), mc.getMyItem(child2)));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent2).getContainedElements()
					.addAll(asList(mc.getTheirItem(child), mc.getTheirItem(child2)));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceConflict.class)
		// My
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", true).andNoOtherMyOps()
			// Theirs
			.theirsIs(MultiReferenceOperation.class).andReturns("isAdd", true).andNoOtherTheirOps();
	}

	@Test
	public void addSameManyToSameNC() {
		final TestElement parent = createTestElement();
		final TestElement child = createTestElement();
		final TestElement child2 = createTestElement();

		final MergeCase mc = newMergeCase(parent, child, child2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getContainedElements().addAll(asList(mc.getMyItem(child), mc.getMyItem(child2)));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getContainedElements()
					.addAll(asList(mc.getTheirItem(child), mc.getTheirItem(child2)));
			}
		}.run(false);

		mc.hasConflict(null);
	}

	@Test
	public void addRemoveSame() {
		final TestElement parent = createTestElement();
		final TestElement parent2 = createTestElement();
		final TestElement parent3 = createTestElement();
		final TestElement child = createTestElement();

		final MergeCase mc = newMergeCase(parent, parent2, parent3, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getContainedElements().add(mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				TestElement theirChild = mc.getTheirItem(child);
				// add first in order to remove
				mc.getTheirItem(parent2).getContainedElements().add(theirChild);
				mc.getTheirItem(parent2).getContainedElements().remove(theirChild);
				// avoid deletion operation, so readd in containment tree
				mc.addTheirs(theirChild);
			}
		}.run(false);

		mc.hasConflict(MultiReferenceConflict.class)
		// My
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", true).andNoOtherMyOps()
			// Theirs
			.theirsIs(MultiReferenceOperation.class).andReturns("isAdd", false).andNoOtherTheirOps();
	}

	@Test
	public void addSetSameToDifferent() {
		final TestElement parent = createTestElement();
		final TestElement parent2 = createTestElement();
		final TestElement child = createTestElement();
		final TestElement decoy = createTestElement();
		parent2.getContainedElements().add(decoy);

		final MergeCase mc = newMergeCase(parent, parent2, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getContainedElements().add(mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent2).getContainedElements().set(0, mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceSetConflict.class)
		// My
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", true).andNoOtherMyOps()
			// Theirs
			.theirsIs(MultiReferenceSetOperation.class).andNoOtherTheirOps();
	}

	@Test
	public void addSetSameToSameNC() {
		final TestElement parent = createTestElement();
		final TestElement child = createTestElement();
		final TestElement decoy = createTestElement();
		parent.getContainedElements().add(decoy);

		final MergeCase mc = newMergeCase(parent, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				TestElement myDecoy = mc.getMyItem(decoy);
				mc.getMyItem(parent).getContainedElements().remove(myDecoy);
				mc.add(myDecoy);
				mc.getMyItem(parent).getContainedElements().add(mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getContainedElements().set(0, mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(null);
	}

	@Test
	public void setSetSameToDifferent() {
		final TestElement parent = createTestElement();
		final TestElement parent2 = createTestElement();
		final TestElement child = createTestElement();
		final TestElement decoy = createTestElement();
		final TestElement decoy2 = createTestElement();
		parent.getContainedElements().add(decoy);
		parent2.getContainedElements().add(decoy2);

		final MergeCase mc = newMergeCase(parent, parent2, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getContainedElements().set(0, mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent2).getContainedElements().set(0, mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceSetSetConflict.class)
		// My
			.myIs(MultiReferenceSetOperation.class).andNoOtherMyOps()
			// Theirs
			.theirsIs(MultiReferenceSetOperation.class).andNoOtherTheirOps();
	}

	@Test
	public void setSetSameToSameNC() {
		final TestElement parent = createTestElement();
		final TestElement child = createTestElement();
		final TestElement decoy = createTestElement();
		parent.getContainedElements().add(decoy);

		final MergeCase mc = newMergeCase(parent, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getContainedElements().set(0, mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getContainedElements().set(0, mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(null);
	}

}
