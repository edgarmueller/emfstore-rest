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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.test.common.dsl.Create;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceSetOperation;
import org.eclipse.emf.emfstore.test.model.TestElement;
import org.junit.Test;

/**
 * Tests conflict detection behaviour on attributes.
 * 
 * @author chodnick
 */
public class ConflictDetectionReferenceTest extends ConflictDetectionTest {

	/**
	 * Tests if overwriting of references is detected as conflict.
	 */
	@Test
	public void conflictSingleReference() {

		final TestElement section1 = Create.testElement();
		final TestElement section2 = Create.testElement();
		final TestElement actor = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section1);
				getProject().addModelElement(section2);
				getProject().addModelElement(actor);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		ModelElementId actor1Id = getProject().getModelElementId(actor);
		ModelElementId actor2Id = actor1Id;
		final ModelElementId section1Id = getProject().getModelElementId(section1);
		final ModelElementId section2Id = project2.getModelElementId(section1);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actor1Id);
		final TestElement actor2 = (TestElement) project2.getModelElement(actor2Id);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor1.setContainer((TestElement) getProject().getModelElement(section1Id));
				actor2.setContainer((TestElement) project2.getModelElement(section2Id));

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(conflicts.size(), 1);

	}

	/**
	 * Tests if overwriting of single references is detected as conflict.
	 */
	@Test
	public void noConflictSingleReferenceSameValue() {

		final TestElement section1 = Create.testElement();
		final TestElement section2 = Create.testElement();
		final TestElement actor = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section1);
				getProject().addModelElement(section2);
				getProject().addModelElement(actor);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		ModelElementId actor1Id = getProject().getModelElementId(actor);
		ModelElementId actor2Id = actor1Id;
		final ModelElementId section1Id = getProject().getModelElementId(section1);
		final ModelElementId section2Id = section1Id;

		final TestElement actor1 = (TestElement) getProject().getModelElement(actor1Id);
		final TestElement actor2 = (TestElement) project2.getModelElement(actor2Id);

		// attention: same structure is being built here, should not conflict
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor1.setContainer((TestElement) getProject().getModelElement(section1Id));
				actor2.setContainer((TestElement) project2.getModelElement(section2Id));

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if overwriting of references is detected as conflict.
	 */
	@Test
	public void noConflictSingleReferenceUnrelated() {

		final TestElement section1 = Create.testElement();
		final TestElement task = Create.testElement();
		final TestElement actor = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {

				getProject().addModelElement(section1);
				getProject().addModelElement(task);
				getProject().addModelElement(actor);

				clearOperations();

			}
		}.run(false);

		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		ModelElementId actor1Id = getProject().getModelElementId(actor);
		ModelElementId actor2Id = actor1Id;
		final ModelElementId section1Id = getProject().getModelElementId(section1);
		final ModelElementId task2Id = project2.getModelElementId(task);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actor1Id);
		final TestElement actor2 = (TestElement) project2.getModelElement(actor2Id);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				actor1.setContainer((TestElement) getProject().getModelElement(section1Id));
				actor2.setReference((TestElement) project2.getModelElement(task2Id));
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if overwriting of references is detected as conflict.
	 */
	@Test
	public void conflictSingleReferenceOpposite() {

		final TestElement issue = Create.testElement();
		final TestElement solution1 = Create.testElement();
		final TestElement solution2 = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(issue);
				getProject().addModelElement(solution1);
				getProject().addModelElement(solution2);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		Project project2 = ps2.getProject();

		ModelElementId issueId = getProject().getModelElementId(issue);
		final ModelElementId solution1Id = getProject().getModelElementId(solution1);
		ModelElementId solution2Id = getProject().getModelElementId(solution2);

		final TestElement issue1 = (TestElement) getProject().getModelElement(issueId);
		final TestElement issue2 = (TestElement) project2.getModelElement(issueId);
		final TestElement solution22 = (TestElement) project2.getModelElement(solution2Id);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				issue1.setContainedElement((TestElement) getProject().getModelElement(solution1Id));
				solution22.setSrefContainer(issue2);

			}
		}.run(false);
		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if overwriting of references is detected as conflict.
	 */
	@Test
	public void noConflictSingleReferenceOppositeSameValue() {

		final TestElement issue = Create.testElement();
		final TestElement solution = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(issue);
				getProject().addModelElement(solution);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		Project project2 = ps2.getProject();

		ModelElementId issueId = getProject().getModelElementId(issue);
		ModelElementId solutionId = getProject().getModelElementId(solution);

		final TestElement issue1 = (TestElement) getProject().getModelElement(issueId);
		final TestElement issue2 = (TestElement) project2.getModelElement(issueId);
		final TestElement solution1 = (TestElement) getProject().getModelElement(solutionId);
		final TestElement solution2 = (TestElement) project2.getModelElement(solutionId);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				issue1.setContainedElement(solution1);
				solution2.setSrefContainer(issue2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if overwriting of references is detected as conflict.
	 */
	@Test
	public void noConflictSingleReferenceOppositeUnrelated() {

		final TestElement issue = Create.testElement();
		final TestElement solution = Create.testElement();
		final TestElement section = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(issue);
				getProject().addModelElement(solution);
				getProject().addModelElement(section);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		Project project2 = ps2.getProject();

		ModelElementId issueId = getProject().getModelElementId(issue);
		ModelElementId solution1Id = getProject().getModelElementId(solution);
		ModelElementId sectionId = getProject().getModelElementId(section);

		final TestElement issue1 = (TestElement) getProject().getModelElement(issueId);
		final TestElement issue2 = (TestElement) project2.getModelElement(issueId);
		final TestElement solution1 = (TestElement) getProject().getModelElement(solution1Id);
		final TestElement section2 = (TestElement) project2.getModelElement(sectionId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				issue1.setReference(solution1);
				issue2.setContainedElement(section2);
			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(0, conflicts.size());

	}

	/**
	 * Tests if overwriting of single references is detected as conflict.
	 */
	@Test
	public void conflictSingleMultiReference() {

		final TestElement section1 = addToProject(Create.testElement());
		final TestElement section2 = addToProject(Create.testElement());
		final TestElement actor = addToProject(Create.testElement());

		clearOperations();
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		Project project2 = ps2.getProject();

		ModelElementId actor1Id = getProject().getModelElementId(actor);
		ModelElementId actor2Id = actor1Id;
		final ModelElementId section1Id = getProject().getModelElementId(section1);
		ModelElementId section2Id = getProject().getModelElementId(section2);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actor1Id);
		final TestElement actor2 = (TestElement) project2.getModelElement(actor2Id);
		final TestElement section22 = (TestElement) project2.getModelElement(section2Id);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor1.setContainer((TestElement) getProject().getModelElement(section1Id));
				section22.getContainedElements().add(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if overwriting of single references is detected as conflict.
	 */
	@Test
	public void noConflictSingleMultiReferenceSameValue() {

		final TestElement section1 = Create.testElement();
		final TestElement section2 = Create.testElement();
		final TestElement actor = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section1);
				getProject().addModelElement(section2);
				getProject().addModelElement(actor);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		Project project2 = ps2.getProject();

		ModelElementId actor1Id = getProject().getModelElementId(actor);
		ModelElementId actor2Id = actor1Id;
		final ModelElementId section1Id = getProject().getModelElementId(section1);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actor1Id);
		final TestElement actor2 = (TestElement) project2.getModelElement(actor2Id);
		final TestElement section12 = (TestElement) project2.getModelElement(section1Id);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {

				actor1.setContainer((TestElement) getProject().getModelElement(section1Id));
				section12.getContainedElements().add(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if overwriting of references is detected as conflict.
	 */
	@Test
	public void noConflictSingleMultiReferenceUnrelated() {

		final TestElement section1 = Create.testElement();
		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section1);
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		ModelElementId actorId = getProject().getModelElementId(actor);
		final ModelElementId section1Id = getProject().getModelElementId(section1);
		final ModelElementId useCaseId = getProject().getModelElementId(useCase);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) project2.getModelElement(actorId);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				actor1.setContainer((TestElement) getProject().getModelElement(section1Id));
				actor2.getReferences().add(((TestElement) project2.getModelElement(useCaseId)));

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(conflicts.size(), 0);

	}

	/**
	 * Tests if overwriting of references is detected as conflict.
	 */
	@Test
	public void conflictSingleReferenceBothOpposite() {

		final TestElement issue = Create.testElement();
		final TestElement solution1  = Create.testElement();
		final TestElement solution2 = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(issue);
				getProject().addModelElement(solution1);
				getProject().addModelElement(solution2);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		Project project2 = ps2.getProject();

		ModelElementId issueId = getProject().getModelElementId(issue);
		ModelElementId solution1Id = getProject().getModelElementId(solution1);
		ModelElementId solution2Id = getProject().getModelElementId(solution2);

		final TestElement issue1 = (TestElement) getProject().getModelElement(issueId);
		final TestElement issue2 = (TestElement) project2.getModelElement(issueId);
		final TestElement solution1inProject1 = (TestElement) getProject().getModelElement(solution1Id);
		final TestElement solution2inProject22 = (TestElement) project2.getModelElement(solution2Id);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				solution1inProject1.setContainedElement(issue1);
				solution2inProject22.setContainedElement(issue2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if overwriting of references is detected as conflict.
	 */
	@Test
	public void noConflictSingleReferenceBothOppositeSameValue() {

		final TestElement issue = Create.testElement();
		final TestElement solution1 = Create.testElement();
		final TestElement solution2 = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(issue);
				getProject().addModelElement(solution1);
				getProject().addModelElement(solution2);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		Project project2 = ps2.getProject();

		ModelElementId issueId = getProject().getModelElementId(issue);
		ModelElementId solution1Id = getProject().getModelElementId(solution1);

		final TestElement issue1 = (TestElement) getProject().getModelElement(issueId);
		final TestElement issue2 = (TestElement) project2.getModelElement(issueId);
		final TestElement solution1inProject1 = (TestElement) getProject().getModelElement(solution1Id);
		final TestElement solution2inProject2 = (TestElement) project2.getModelElement(solution1Id);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				solution1inProject1.setContainedElement(issue1);
				solution2inProject2.setContainedElement(issue2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if overwriting of references is detected as conflict.
	 */
	@Test
	public void noConflictSingleReferenceBothOppositeUnrelated() {

		final TestElement issue1 = Create.testElement();
		final TestElement issue2 = Create.testElement();
		final TestElement solution1 = Create.testElement();
		final TestElement solution2 = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(issue1);
				getProject().addModelElement(issue2);
				getProject().addModelElement(solution1);
				getProject().addModelElement(solution2);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		Project project2 = ps2.getProject();

		ModelElementId issue11Id = getProject().getModelElementId(issue1);
		ModelElementId issue22Id = project2.getModelElementId(issue2);
		ModelElementId solution1Id = getProject().getModelElementId(solution1);
		ModelElementId solution2Id = getProject().getModelElementId(solution2);

		final TestElement issue11 = (TestElement) getProject().getModelElement(issue11Id);
		final TestElement issue22 = (TestElement) project2.getModelElement(issue22Id);
		final TestElement solution1inProject1 = (TestElement) getProject().getModelElement(solution1Id);
		final TestElement solution2inProject2 = (TestElement) project2.getModelElement(solution2Id);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				solution1inProject1.setContainedElement(issue11);
				solution2inProject2.setContainedElement(issue22);
			}
		}.run(false);
		
		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(0, conflicts.size());

	}

	/**
	 * Tests if overwriting of single references is detected as conflict.
	 */
	@Test
	public void conflictMultiMultiReferenceBothOpposite() {

		final TestElement section1 = Create.testElement();
		final TestElement section2 = Create.testElement();
		final TestElement actor = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section1);
				getProject().addModelElement(section2);
				getProject().addModelElement(actor);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		Project project2 = ps2.getProject();

		ModelElementId actorId = getProject().getModelElementId(actor);
		ModelElementId section11Id = getProject().getModelElementId(section1);
		ModelElementId section22Id = getProject().getModelElementId(section2);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) project2.getModelElement(actorId);
		final TestElement section11 = (TestElement) getProject().getModelElement(section11Id);
		final TestElement section22 = (TestElement) project2.getModelElement(section22Id);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section11.getContainedElements().add(actor1);
				section22.getContainedElements().add(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if overwriting of single references is detected as conflict.
	 */
	@Test
	public void noConflictMultiMultiReferenceSameChange() {

		final TestElement section1 = Create.testElement();
		final TestElement section2 = Create.testElement();
		final TestElement actor = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section1);
				getProject().addModelElement(section2);
				getProject().addModelElement(actor);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		Project project2 = ps2.getProject();

		ModelElementId actorId = getProject().getModelElementId(actor);
		ModelElementId section1Id = getProject().getModelElementId(section1);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actorId);
		final TestElement actor2 = (TestElement) project2.getModelElement(actorId);
		final TestElement section1inProject1 = (TestElement) getProject().getModelElement(section1Id);
		final TestElement section1inProject2 = (TestElement) project2.getModelElement(section1Id);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1inProject1.getContainedElements().add(actor1);
				section1inProject2.getContainedElements().add(actor2);

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(1, conflicts.size());

	}

	/**
	 * Tests if overwriting of references is detected as conflict.
	 */
	@Test
	public void noConflictMultiMultiReferenceUnrelated() {

		final TestElement section = Create.testElement();
		final TestElement useCase = Create.testElement();
		final TestElement actor = Create.testElement();

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getProject().addModelElement(section);
				getProject().addModelElement(useCase);
				getProject().addModelElement(actor);

				clearOperations();

			}
		}.run(false);
		ProjectSpace ps2 = cloneProjectSpace(getProjectSpace());
		final Project project2 = ps2.getProject();

		ModelElementId actor1Id = getProject().getModelElementId(actor);
		ModelElementId actor2Id = actor1Id;
		ModelElementId section1Id = getProject().getModelElementId(section);
		final ModelElementId useCaseId = getProject().getModelElementId(useCase);

		final TestElement actor1 = (TestElement) getProject().getModelElement(actor1Id);
		final TestElement actor2 = (TestElement) project2.getModelElement(actor2Id);
		final TestElement section1 = (TestElement) getProject().getModelElement(section1Id);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				section1.getContainedElements().add(actor1);
				actor2.getReferences().add(((TestElement) project2.getModelElement(useCaseId)));

			}
		}.run(false);

		List<AbstractOperation> ops1 = getProjectSpace().getOperations();
		List<AbstractOperation> ops2 = ps2.getOperations();

		Set<AbstractOperation> conflicts = getConflicts(ops1, ops2);
		assertEquals(getConflicts(ops1, ops2).size(), getConflicts(ops2, ops1).size());

		assertEquals(conflicts.size(), 0);
	}

	/**
	 * Set vs add - no conflict.
	 */
	@Test
	public void noConflictMultiReferenceAddVsSet() {

		final TestElement testElement = addToProject(Create.testElement());
		final TestElement first = addToProject(Create.testElement());
		final TestElement second = addToProject(Create.testElement());
		final TestElement inserted = addToProject(Create.testElement());
		final TestElement added = addToProject(Create.testElement());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getReferences().addAll(Arrays.asList(first, second));
				clearOperations();
				testElement.getReferences().set(1, inserted);
			}
		}.run(false);

		AbstractOperation set = myCheckAndGetOperation(MultiReferenceSetOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getReferences().add(added);
			}
		}.run(false);

		AbstractOperation add = myCheckAndGetOperation(MultiReferenceOperation.class);

		assertEquals(false, doConflict(set, add));
		assertEquals(false, doConflict(add, set));
	}
	
	private <T extends EObject> T addToProject(final T eObject) {
		RunESCommand.run(new Callable<Void>() {			
			public Void call() throws Exception {
				getProject().getModelElements().add(eObject);
				return null;
			}
		});
		return eObject;
	}
	
	/**
	 * Set vs remove - no conflict.
	 */
	@Test
	public void noConflictMultiReferenceRemoveVsSet() {
		final TestElement testElement = addToProject(Create.testElement());
		final TestElement first = addToProject(Create.testElement());
		final TestElement second = addToProject(Create.testElement());
		final TestElement inserted = addToProject(Create.testElement());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getReferences().addAll(Arrays.asList(first, second));
				clearOperations();
				testElement.getReferences().remove(first);
			}
		}.run(false);

		AbstractOperation remove = myCheckAndGetOperation(MultiReferenceOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getReferences().set(testElement.getReferences().indexOf(second), inserted);
			}
		}.run(false);

		AbstractOperation set = myCheckAndGetOperation(MultiReferenceSetOperation.class);

		assertEquals(false, doConflict(set, remove));
		assertEquals(false, doConflict(remove, set));
	}

	/**
	 * Set vs remove - conflict.
	 */
	@Test
	public void conflictMultiReferenceRemoveVsSet() {
		final TestElement testElement = addToProject(Create.testElement());
		final TestElement first = addToProject(Create.testElement());
		final TestElement second = addToProject(Create.testElement());
		final TestElement inserted = addToProject(Create.testElement());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getReferences().addAll(Arrays.asList(first, second));
				clearOperations();
				testElement.getReferences().remove(second);
			}
		}.run(false);

		AbstractOperation remove = myCheckAndGetOperation(MultiReferenceOperation.class);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.getReferences().add(second);
				clearOperations();
				testElement.getReferences().set(testElement.getReferences().indexOf(second), inserted);
			}
		}.run(false);

		AbstractOperation set = myCheckAndGetOperation(MultiReferenceSetOperation.class);

		assertEquals(true, doConflict(set, remove));
		assertEquals(true, doConflict(remove, set));
	}

}
