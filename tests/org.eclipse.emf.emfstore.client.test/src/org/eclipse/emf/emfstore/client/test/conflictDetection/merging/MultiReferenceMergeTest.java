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
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceConflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceSetConflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts.MultiReferenceSetSetConflict;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceSetOperation;
import org.junit.Test;

public class MultiReferenceMergeTest extends MergeTest {

	@Test
	public void addRemoveSame() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();

		final MergeCase mc = newMergeCase(parent, child);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().add(mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().add(mc.getTheirItem(child));
				mc.getTheirItem(parent).getReferences().remove(mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceConflict.class)
		// my
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", true).andNoOtherMyOps()
			// their
			.theirsIs(MultiReferenceOperation.class).andReturns("isAdd", false).andNoOtherTheirOps();
	}

	@Test
	public void addRemoveManySame() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement decoyChild = getTestElement();
		parent.getReferences().add(decoyChild);

		final MergeCase mc = newMergeCase(parent, child, decoyChild);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().add(mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().add(mc.getTheirItem(child));
				mc.getTheirItem(parent).getReferences()
					.removeAll(asList(mc.getTheirItem(child), mc.getTheirItem(decoyChild)));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceConflict.class)
		// my
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", true).andNoOtherMyOps()
			// their
			.theirsIs(MultiReferenceOperation.class).andReturns("isAdd", false).andNoOtherTheirOps();

	}

	@Test
	public void addRemoveDifferentNC() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement child2 = getTestElement();
		parent.getReferences().add(child2);

		final MergeCase mc = newMergeCase(parent, child, child2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().add(mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().remove(mc.getTheirItem(child2));
			}
		}.run(false);

		mc.hasConflict(null);
	}

	@Test
	public void addRemoveSameWithNoise() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement child2 = getTestElement();

		final MergeCase mc = newMergeCase(parent, child, child2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().add(mc.getMyItem(child));
				mc.getMyItem(parent).getReferences().add(mc.getMyItem(child2));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().add(mc.getTheirItem(child));
				mc.getTheirItem(parent).getReferences().remove(mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceConflict.class)
		// my
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", true).andNoOtherMyOps()
			// their
			.theirsIs(MultiReferenceOperation.class).andReturns("isAdd", false).andNoOtherTheirOps();
	}

	// Many

	@Test
	public void addManyRemoveSame() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement child2 = getTestElement();

		final MergeCase mc = newMergeCase(parent, child, child2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().addAll(asList(mc.getMyItem(child), mc.getMyItem(child2)));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().add(mc.getTheirItem(child));
				mc.getTheirItem(parent).getReferences().remove(mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceConflict.class)
		// my
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", true).andNoOtherMyOps()
			// their
			.theirsIs(MultiReferenceOperation.class).andReturns("isAdd", false).andNoOtherTheirOps();
	}

	@Test
	public void addManyRemoveManySame() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement child2 = getTestElement();

		final MergeCase mc = newMergeCase(parent, child, child2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().addAll(asList(mc.getMyItem(child), mc.getMyItem(child2)));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().add(mc.getTheirItem(child));
				mc.getTheirItem(parent).getReferences().add(mc.getTheirItem(child2));
				mc.getTheirItem(parent).getReferences()
					.removeAll(asList(mc.getTheirItem(child), mc.getTheirItem(child2)));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceConflict.class)
			// my
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", true)
			.andReturns("getReferencedModelElements", getIds(child, child2)).andNoOtherMyOps()
			// their
			.theirsIs(MultiReferenceOperation.class).andReturns("isAdd", false)
			.andReturns("getReferencedModelElements", getIds(child, child2)).andNoOtherTheirOps();
	}

	@Test
	public void addManyRemoveDifferentNC() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement child2 = getTestElement();
		final TestElement child3 = getTestElement();
		parent.getReferences().add(child3);

		final MergeCase mc = newMergeCase(parent, child, child2, child3);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().addAll(asList(mc.getMyItem(child), mc.getMyItem(child2)));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().remove(mc.getTheirItem(child3));
			}
		}.run(false);

		mc.hasConflict(null);
	}

	@Test
	public void addManyRemoveSameWithNoise() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement child2 = getTestElement();
		final TestElement decoy1 = getTestElement();
		final TestElement decoy2 = getTestElement();

		final MergeCase mc = newMergeCase(parent, child, child2, decoy1, decoy2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().addAll(asList(mc.getMyItem(child), mc.getMyItem(child2)));
				mc.getMyItem(parent).getReferences().addAll(asList(mc.getMyItem(decoy1), mc.getMyItem(decoy2)));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().add(mc.getTheirItem(child));
				mc.getTheirItem(parent).getReferences().remove(mc.getTheirItem(child));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceConflict.class)
			// my
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", true)
			.andReturns("getReferencedModelElements", getIds(child, child2)).andNoOtherMyOps()
			// their
			.theirsIs(MultiReferenceOperation.class).andReturns("isAdd", false)
			.andReturns("getReferencedModelElements", getIds(child)).andNoOtherTheirOps();
	}

	// Remove

	@Test
	public void removeVsSet() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		parent.getReferences().add(child);
		final TestElement newChild = getTestElement();

		final MergeCase mc = newMergeCase(parent, child, newChild);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().remove(mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().set(0, mc.getTheirItem(newChild));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceSetConflict.class)
		// my
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", false).andNoOtherMyOps()
			// their
			.theirsIs(MultiReferenceSetOperation.class).andReturns("getIndex", 0).andNoOtherTheirOps();
	}

	@Test
	public void removeManyVsSet() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement child2 = getTestElement();
		parent.getReferences().add(child);
		parent.getReferences().add(child2);
		final TestElement newChild = getTestElement();

		final MergeCase mc = newMergeCase(parent, child, child2, newChild);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().removeAll(asList(mc.getMyItem(child), mc.getTheirItem(child2)));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().set(0, mc.getTheirItem(newChild));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceSetConflict.class)
		// my
			.myIs(MultiReferenceOperation.class).andReturns("isAdd", false).andNoOtherMyOps()
			// their
			.theirsIs(MultiReferenceSetOperation.class).andReturns("getIndex", 0).andNoOtherTheirOps();
	}

	@Test
	public void removeVsSetDifferentNC() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement child2 = getTestElement();
		final TestElement child3 = getTestElement();
		parent.getReferences().add(child);
		parent.getReferences().add(child2);
		parent.getReferences().add(child3);
		final TestElement newChild = getTestElement();

		final MergeCase mc = newMergeCase(parent, child, child2, child3, newChild);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().removeAll(asList(mc.getMyItem(child), mc.getTheirItem(child2)));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().set(2, mc.getTheirItem(newChild));
			}
		}.run(false);

		mc.hasConflict(null);
	}

	// Set

	@Test
	public void setVsSet() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		parent.getReferences().add(child);
		final TestElement newChild = getTestElement();
		final TestElement newChild2 = getTestElement();

		final MergeCase mc = newMergeCase(parent, child, newChild, newChild2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().set(0, mc.getMyItem(newChild));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().set(0, mc.getTheirItem(newChild2));
			}
		}.run(false);

		mc.hasConflict(MultiReferenceSetSetConflict.class)
		// my
			.myIs(MultiReferenceSetOperation.class).andReturns("getIndex", 0).andNoOtherMyOps()
			// their
			.theirsIs(MultiReferenceSetOperation.class).andReturns("getIndex", 0).andNoOtherTheirOps();
	}

	@Test
	public void setVsSetSameNC() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		parent.getReferences().add(child);
		final TestElement newChild = getTestElement();

		final MergeCase mc = newMergeCase(parent, child, newChild);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().set(0, mc.getMyItem(newChild));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().set(0, mc.getTheirItem(newChild));
			}
		}.run(false);

		mc.hasConflict(null);
	}

	@Test
	public void setVsSetDifferentNC() {
		final TestElement parent = getTestElement();
		final TestElement child = getTestElement();
		final TestElement child2 = getTestElement();
		parent.getReferences().add(child);
		parent.getReferences().add(child2);
		final TestElement newChild = getTestElement();
		final TestElement newChild2 = getTestElement();

		final MergeCase mc = newMergeCase(parent, child, child2, newChild, newChild2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).getReferences().set(0, mc.getMyItem(newChild));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(parent).getReferences().set(1, mc.getTheirItem(newChild2));
			}
		}.run(false);

		mc.hasConflict(null);
	}
}