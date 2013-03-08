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

import java.util.Arrays;

import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts.MultiAttributeConflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts.MultiAttributeSetConflict;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts.MultiAttributeSetSetConflict;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiAttributeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiAttributeSetOperation;
import org.junit.Test;

/**
 * Merge testcases for {@link MultiAttributeOperation} and {@link MultiAttributeSetOperation}.
 * 
 * @author wesendon
 */
public class MultiAttributeMergeTest extends MergeTest {

	@Test
	public void addFirstVsRemove() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().add(1, "X");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().remove(1);
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeConflict.class)
			// My
			.myIs(MultiAttributeOperation.class).andReturns("isAdd", true)
			// Theirs
			.theirsIs(MultiAttributeOperation.class).andReturns("isAdd", false);
	}

	@Test
	public void addVsRemove() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().add("X");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().remove(1);
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeConflict.class)
			// My
			.myIs(MultiAttributeOperation.class).andReturns("isAdd", true)
			// Theirs
			.theirsIs(MultiAttributeOperation.class).andReturns("isAdd", false);
	}

	@Test
	public void addManyVsRemove() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().addAll(1, Arrays.asList("x", "y"));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().remove(1);
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeConflict.class)
			// My
			.myIs(MultiAttributeOperation.class).andReturns("isAdd", true)
			// Theirs
			.theirsIs(MultiAttributeOperation.class).andReturns("isAdd", false);
	}

	@Test
	public void addManyVsRemoveMany() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().addAll(1, Arrays.asList("x", "y"));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().removeAll(Arrays.asList("b", "c"));
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeConflict.class)
			// My
			.myIs(MultiAttributeOperation.class).andReturns("isAdd", true)
			// Theirs
			.theirsIs(MultiAttributeOperation.class).andReturns("isAdd", false);
	}

	@Test
	public void removeVsRemove() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().remove(1);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().remove(2);
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeConflict.class)
			// My
			.myIs(MultiAttributeOperation.class).andReturns("isAdd", false)
			// Theirs
			.theirsIs(MultiAttributeOperation.class).andReturns("isAdd", false);
	}

	@Test
	public void removeVsRemoveSameNc() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().remove(1);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().remove(1);
			}
		}.run(false);

		// TODO: false positive, fix later
		mergeCase.hasConflict(MultiAttributeConflict.class);
	}

	@Test
	public void removeManyVsRemoveMany() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().removeAll(Arrays.asList("a", "b"));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().removeAll(Arrays.asList("b", "c"));
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeConflict.class)
			// My
			.myIs(MultiAttributeOperation.class).andReturns("isAdd", false)
			// Theirs
			.theirsIs(MultiAttributeOperation.class).andReturns("isAdd", false);
	}

	/**
	 * Remove and Set on the same element.
	 */
	@Test
	public void removeVsSet() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().remove(1);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().set(1, "X");
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeSetConflict.class)
			// My
			.myIs(MultiAttributeOperation.class).andReturns("isAdd", false)
			// Theirs
			.theirsIs(MultiAttributeSetOperation.class).andReturns("getIndex", 1).andReturns("getNewValue", "X");
	}

	/**
	 * Remove on a lower remove index than Set.
	 */
	@Test
	public void removeVsSetLowerIndex() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().remove(0);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().set(1, "X");
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeSetConflict.class)
			// My
			.myIs(MultiAttributeOperation.class).andReturns("isAdd", false)
			// Theirs
			.theirsIs(MultiAttributeSetOperation.class).andReturns("getIndex", 1).andReturns("getNewValue", "X");
	}

	/**
	 * Remove on a higher remove index than Set. That's a conflict.
	 */
	@Test
	public void removeVsSetHigherIndexNC() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().remove(1);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().set(0, "X");
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeSetConflict.class);
	}

	/**
	 * Remove multiple elements individually vs. Set. Individually removing causes multiple remove operations.
	 */
	@Test
	public void multipleRemoveVsSet() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().remove(1);
				mergeCase.getMyItem(element).getStrings().remove(1);
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().set(1, "X");
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeSetConflict.class)
			// My first
			.myIs(MultiAttributeOperation.class).andReturns("isAdd", false)
			// My Second
			.myOtherContains(MultiAttributeOperation.class).andReturns("isAdd", false).andNoOtherMyOps()
			// Their
			.theirsIs(MultiAttributeSetOperation.class).andReturns("getIndex", 1).andReturns("getNewValue", "X")
			.andNoOtherTheirOps();
	}

	/**
	 * Remove multiple elements in one operation vs Set.
	 */
	@Test
	public void multiRemoveVsSet() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().removeAll(asList("b", "c"));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().set(1, "X");
			}
		}.run(false);

		mergeCase
			.hasConflict(MultiAttributeSetConflict.class)
			// My first
			.myIs(MultiAttributeOperation.class).andReturns("isAdd", false)
			.andReturns("getReferencedValues", asList("b", "c")).andNoOtherMyOps()
			// Their
			.theirsIs(MultiAttributeSetOperation.class).andReturns("getIndex", 1).andReturns("getNewValue", "X")
			.andNoOtherTheirOps();
	}

	/**
	 * Remove multiple elements in one operation vs Set, with a lower remove index.
	 */
	@Test
	public void multiRemoveVsSetLowerIndex() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().removeAll(asList("a", "b"));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().set(2, "X");
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeSetConflict.class)
			// My first
			.myIs(MultiAttributeOperation.class).andReturns("isAdd", false)
			.andReturns("getReferencedValues", asList("a", "b")).andNoOtherMyOps()
			// Their
			.theirsIs(MultiAttributeSetOperation.class).andReturns("getNewValue", "X").andNoOtherTheirOps();
	}

	/**
	 * Remove multiple elements in one operation vs Set, with a higher remove index. (NC)
	 */
	@Test
	public void multiRemoveVsSetHigherIndexNC() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().removeAll(asList("b", "c"));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().set(0, "X");
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeSetConflict.class);
	}

	@Test
	public void setVsSet() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().set(1, "Y");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().set(1, "X");
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeSetSetConflict.class)
			// My
			.myIs(MultiAttributeSetOperation.class).andReturns("getNewValue", "Y")
			// Theirs
			.theirsIs(MultiAttributeSetOperation.class).andReturns("getNewValue", "X");
	}

	@Test
	public void setVsSetNC() {
		final TestElement element = getTestElement();
		element.getStrings().add("a");
		element.getStrings().add("b");
		element.getStrings().add("c");

		final MergeCase mergeCase = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getMyItem(element).getStrings().set(1, "Y");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mergeCase.getTheirItem(element).getStrings().set(2, "X");
			}
		}.run(false);

		mergeCase.hasConflict(MultiAttributeSetSetConflict.class);
	}
}