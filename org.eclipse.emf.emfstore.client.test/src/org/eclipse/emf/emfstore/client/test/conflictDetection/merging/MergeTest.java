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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.test.conflictDetection.ConflictDetectionTest;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.Conflict;
import org.eclipse.emf.emfstore.internal.client.model.controller.ChangeConflict;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictBucketCandidate;
import org.eclipse.emf.emfstore.internal.server.conflictDetection.ConflictDetector;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * Helper super class for merge tests.
 * 
 * @author wesendon
 */
public class MergeTest extends ConflictDetectionTest {

	private MergeCase mergeCase;

	/**
	 * Default Constructor.
	 * 
	 * @return case helper
	 */
	public MergeCase newMergeCase() {
		return newMergeCase(new EObject[0]);
	}

	public MergeCase newMergeCase(EObject... objs) {
		mergeCase = new MergeCase();
		mergeCase.add(objs);
		mergeCase.ensureCopy();
		return mergeCase;
	}

	public List<ModelElementId> getIds(EObject... objs) {
		ArrayList<ModelElementId> result = new ArrayList<ModelElementId>();
		for (EObject obj : objs) {
			result.add(mergeCase.getMyId(obj));
		}
		return result;
	}

	public ModelElementId getId(EObject obj) {
		return mergeCase.getMyId(obj);
	}

	public ModelElementId getMyId(EObject obj) {
		return mergeCase.getMyId(obj);
	}

	public ModelElementId getTheirId(EObject obj) {
		return mergeCase.getTheirId(obj);
	}

	/**
	 * Helper class for merge tests. It manages the two projectspaces and offers covenience methods.
	 * 
	 * @author wesendon
	 */
	public class MergeCase {

		private ProjectSpace theirProjectSpace;

		private void add(final EObject... objs) {
			new EMFStoreCommand() {
				@Override
				protected void doRun() {
					for (EObject obj : objs) {
						getProject().addModelElement(obj);
					}
				}
			}.run(false);
		}

		public void addTheirs(final EObject... objs) {
			new EMFStoreCommand() {
				@Override
				protected void doRun() {
					for (EObject obj : objs) {
						getTheirProject().addModelElement(obj);
					}
				}
			}.run(false);
		}

		@SuppressWarnings("unchecked")
		public <T extends EObject> T getMyItem(T id) {
			ensureCopy();
			return (T) getProject().getModelElement(byId(id));
		}

		public ModelElementId getMyId(EObject obj) {
			return getProject().getModelElementId(obj);
		}

		public ModelElementId getTheirId(EObject obj) {
			return getTheirProject().getModelElementId(getTheirItem(obj));
		}

		@SuppressWarnings("unchecked")
		public <T extends EObject> T getTheirItem(T id) {
			ensureCopy();
			return (T) getTheirProject().getModelElement(byId(id));
		}

		private ModelElementId byId(EObject id) {
			return getProject().getModelElementId(id);
		}

		public void ensureCopy() {
			if (theirProjectSpace == null) {
				new EMFStoreCommand() {
					@Override
					protected void doRun() {
						clearOperations();
						theirProjectSpace = cloneProjectSpace(getProjectSpace());
					}
				}.run(false);
			}
		}

		public Project getTheirProject() {
			ensureCopy();
			return this.theirProjectSpace.getProject();
		}

		public ProjectSpace getTheirProjectSpace() {
			ensureCopy();
			return this.theirProjectSpace;
		}

		public DecisionManager execute() {
			ensureCopy();
			PrimaryVersionSpec spec = Versions.createPRIMARY(23);

			List<ChangePackage> myChangePackages = Arrays.asList(getProjectSpace().getLocalChangePackage(true));
			List<ChangePackage> theirChangePackages = Arrays.asList(getTheirProjectSpace().getLocalChangePackage(true));

			Set<ConflictBucketCandidate> conflictCandidateBuckets = new ConflictDetector()
				.calculateConflictCandidateBuckets(myChangePackages, theirChangePackages);
			ChangeConflict changeConflict = new ChangeConflict(getProjectSpace(),
				myChangePackages, theirChangePackages, conflictCandidateBuckets, getProject());

			DecisionManager manager = new DecisionManager(getProject(), changeConflict, spec, spec, false);

			return manager;
		}

		public <T extends Conflict> MergeTestQuery hasConflict(Class<T> clazz, int expectedConflicts) {
			MergeTestQuery query = new MergeTestQuery(execute());
			return query.hasConflict(clazz, expectedConflicts);
		}

		public <T extends Conflict> MergeTestQuery hasConflict(Class<T> clazz) {
			if (clazz == null) {
				ArrayList<Conflict> conflicts = execute().getConflicts();
				assertEquals(0, conflicts.size());
				return null;
			}
			return hasConflict(clazz, 1);
		}

		public ProjectSpace getMyProjectSpace() {
			return getProjectSpace();
		}
	}

	public class MergeTestQuery {

		private final DecisionManager manager;
		private ArrayList<Conflict> conflicts;
		private Object lastObject;
		private HashSet<AbstractOperation> mySeen;
		private HashSet<AbstractOperation> theirSeen;

		public MergeTestQuery(DecisionManager manager) {
			this.manager = manager;
			mySeen = new LinkedHashSet<AbstractOperation>();
			theirSeen = new LinkedHashSet<AbstractOperation>();
		}

		public <T extends Conflict> MergeTestQuery hasConflict(Class<T> clazz, int i) {
			conflicts = manager.getConflicts();
			assertEquals("Number of conflicts", i, conflicts.size());
			Conflict currentConflict = currentConflict();
			if (!clazz.isInstance(currentConflict)) {
				throw new AssertionError("Expected: " + clazz.getName() + " but found: "
					+ ((currentConflict == null) ? "null" : currentConflict.getClass().getName()));
			}
			return this;
		}

		private Conflict currentConflict() {
			return conflicts.get(0);
		}

		@SuppressWarnings("unchecked")
		public <T extends AbstractOperation> T getMy(Class<T> class1) {
			AbstractOperation myOp = currentConflict().getMyOperation();
			if (!class1.isInstance(myOp)) {
				throw new AssertionError("Expected: " + class1.getName() + " but found: "
					+ ((myOp == null) ? "null" : myOp.getClass().getName()));
			}
			return (T) myOp;
		}

		@SuppressWarnings("unchecked")
		public <T extends AbstractOperation> T getTheir(Class<T> class1) {
			AbstractOperation theirOp = currentConflict().getTheirOperation();
			assertTrue(class1.isInstance(theirOp));
			return (T) theirOp;
		}

		public <T extends AbstractOperation> MergeTestQuery myIs(Class<T> class1) {
			last(true, getMy(class1));
			return this;
		}

		public <T extends AbstractOperation> MergeTestQuery myOtherContains(Class<T> class1) {
			Set<AbstractOperation> ops = currentConflict().getMyOperations();
			for (AbstractOperation op : ops) {
				if (class1.isInstance(op) && op != currentConflict().getMyOperation()) {
					last(true, op);
					return this;
				}
			}
			throw new AssertionError("Expected: " + class1.getName() + " in other my operations but was not found");
		}

		public <T extends AbstractOperation> MergeTestQuery theirsIs(Class<T> class1) {
			last(false, getTheir(class1));
			return this;
		}

		private void last(boolean my, AbstractOperation op) {
			lastObject = op;
			if (my) {
				mySeen.add(op);
			} else {
				theirSeen.add(op);
			}
		}

		public MergeTestQuery andNoOtherMyOps() {
			HashSet<AbstractOperation> my = new LinkedHashSet<AbstractOperation>(currentConflict().getMyOperations());
			my.removeAll(mySeen);
			assertEquals(0, my.size());
			return this;
		}

		public MergeTestQuery andNoOtherTheirOps() {
			HashSet<AbstractOperation> theirs = new LinkedHashSet<AbstractOperation>(currentConflict()
				.getTheirOperations());
			theirs.removeAll(theirSeen);
			assertEquals(theirs.size(), 0);
			return this;
		}

		public MergeTestQuery andReturns(String methodName, Object b) {
			assertTrue(lastObject != null);
			try {
				for (Method method : lastObject.getClass().getMethods()) {
					if (method.getName().equals(methodName)) {
						Object invoke = method.invoke(lastObject, (Object[]) null);
						assertEquals(b, invoke);
						return this;
					}
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			throw new AssertionError("No such method");
		}
	}
}