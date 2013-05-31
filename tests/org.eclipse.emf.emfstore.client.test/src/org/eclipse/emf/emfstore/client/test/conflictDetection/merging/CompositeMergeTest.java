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

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.internal.client.model.CompositeOperationHandle;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts.CompositeConflict;
import org.eclipse.emf.emfstore.internal.client.model.exceptions.InvalidHandleException;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AttributeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;
import org.junit.Test;

public class CompositeMergeTest extends MergeTest {

	private void end(CompositeOperationHandle handle, ModelElementId id) {
		assertTrue(id != null);
		try {
			handle.end("", "", id);
		} catch (InvalidHandleException e) {
			throw new AssertionError(e);
		}
	}

	@Test
	public void attCompVsAtt() {
		final TestElement element = getTestElement();
		final MergeCase mc = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getMyProjectSpace().beginCompositeOperation();

				mc.getMyItem(element).setName("Blub");

				end(handle, mc.getMyId(element));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getTheirItem(element).setName("Foobar");
			}
		}.run(false);

		mc.hasConflict(CompositeConflict.class)
		// my
			.myIs(CompositeOperation.class).andNoOtherMyOps()
			// theirs
			.theirsIs(AttributeOperation.class).andNoOtherTheirOps();
	}

	@Test
	public void attVsAttComp() {
		final TestElement element = getTestElement();
		final MergeCase mc = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(element).setName("Blub");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getTheirProjectSpace().beginCompositeOperation();

				mc.getTheirItem(element).setName("Foobar");

				ModelElementId theirId = mc.getTheirId(element);
				end(handle, theirId);
			}

		}.run(false);

		mc.hasConflict(CompositeConflict.class)
		// my
			.myIs(AttributeOperation.class).andNoOtherMyOps()
			// theirs
			.theirsIs(CompositeOperation.class).andNoOtherTheirOps();
	}

	@Test
	public void attCompVsDiffAttNC() {
		final TestElement element = getTestElement();

		final MergeCase mc = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(element).setName("Blub");
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getTheirProjectSpace().beginCompositeOperation();

				mc.getTheirItem(element).setDescription("Foobar");

				end(handle, mc.getTheirId(element));
			}

		}.run(false);

		mc.hasConflict(null);
	}

	@Test
	public void singleCompVsSingle() {
		final TestElement element = getTestElement();
		final TestElement link = getTestElement();
		final TestElement link2 = getTestElement();

		final MergeCase mc = newMergeCase(element, link, link2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(element).setReference(mc.getMyItem(link));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getTheirProjectSpace().beginCompositeOperation();

				mc.getTheirItem(element).setReference(mc.getTheirItem(link2));

				end(handle, mc.getTheirId(element));
			}

		}.run(false);

		mc.hasConflict(CompositeConflict.class)
		// my
			.myIs(SingleReferenceOperation.class).andNoOtherMyOps()
			// theirs
			.theirsIs(CompositeOperation.class).andNoOtherTheirOps();
	}

	@Test
	public void compVsSingleAndAtt() {
		final TestElement element = getTestElement();
		final TestElement link = getTestElement();
		final TestElement link2 = getTestElement();

		final MergeCase mc = newMergeCase(element, link, link2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(element).setName("blub");
				mc.getMyItem(element).setReference(mc.getMyItem(link));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getTheirProjectSpace().beginCompositeOperation();

				mc.getTheirItem(element).setName("foobar");
				mc.getTheirItem(element).setReference(mc.getTheirItem(link2));

				end(handle, mc.getTheirId(element));
			}

		}.run(false);

		mc.hasConflict(CompositeConflict.class)
		// my
			.myIs(SingleReferenceOperation.class).myOtherContains(AttributeOperation.class).andNoOtherMyOps()
			// theirs
			.theirsIs(CompositeOperation.class).andNoOtherTheirOps();
	}

	@Test
	public void compVsSingleContainment() {
		final TestElement parent = getTestElement();
		final TestElement parent2 = getTestElement();
		final TestElement child = getTestElement();

		final MergeCase mc = newMergeCase(parent, child, parent2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				mc.getMyItem(parent).setContainedElement(mc.getMyItem(child));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getTheirProjectSpace().beginCompositeOperation();

				mc.getTheirItem(parent2).setContainedElement(mc.getTheirItem(child));

				end(handle, mc.getTheirId(parent2));
			}

		}.run(false);

		mc.hasConflict(CompositeConflict.class)
		// my
			.myIs(SingleReferenceOperation.class).andNoOtherMyOps()
			// theirs
			.theirsIs(CompositeOperation.class).andNoOtherTheirOps();
	}

	@Test
	public void compVsCompAtt() {
		final TestElement element = getTestElement();

		final MergeCase mc = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getMyProjectSpace().beginCompositeOperation();

				mc.getMyItem(element).setName("Blub");

				end(handle, mc.getMyId(element));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getTheirProjectSpace().beginCompositeOperation();

				mc.getTheirItem(element).setName("Foobar");

				end(handle, mc.getTheirId(element));
			}

		}.run(false);

		mc.hasConflict(CompositeConflict.class)
		// my
			.myIs(CompositeOperation.class).andNoOtherMyOps()
			// theirs
			.theirsIs(CompositeOperation.class).andNoOtherTheirOps();
	}

	@Test
	public void singleCompVsSingleComp() {
		final TestElement element = getTestElement();
		final TestElement link = getTestElement();
		final TestElement link2 = getTestElement();

		final MergeCase mc = newMergeCase(element, link, link2);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getMyProjectSpace().beginCompositeOperation();

				mc.getMyItem(element).setReference(mc.getMyItem(link));

				end(handle, mc.getMyId(element));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getTheirProjectSpace().beginCompositeOperation();

				mc.getTheirItem(element).setReference(mc.getTheirItem(link2));

				end(handle, mc.getTheirId(element));
			}

		}.run(false);

		mc.hasConflict(CompositeConflict.class)
		// my
			.myIs(CompositeOperation.class).andNoOtherMyOps()
			// theirs
			.theirsIs(CompositeOperation.class).andNoOtherTheirOps();
	}

	@Test
	public void compVsCompAttNC() {
		final TestElement element = getTestElement();

		final MergeCase mc = newMergeCase(element);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getMyProjectSpace().beginCompositeOperation();

				mc.getMyItem(element).setName("Blub");

				end(handle, mc.getMyId(element));
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				CompositeOperationHandle handle = mc.getTheirProjectSpace().beginCompositeOperation();

				mc.getTheirItem(element).setDescription("Foobar");

				end(handle, mc.getTheirId(element));
			}

		}.run(false);

		mc.hasConflict(null);
	}

}
