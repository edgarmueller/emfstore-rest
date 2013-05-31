package org.eclipse.emf.emfstore.client.test.changeTracking.operations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.client.test.WorkspaceTest;
import org.eclipse.emf.emfstore.internal.client.observers.SimpleOperationObserver;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.junit.Before;
import org.junit.Test;

public class SimpleOperationObserverTest extends WorkspaceTest {

	boolean operationPerformed;

	@Before
	public void before() {
		operationPerformed = false;
	}

	@Test
	public void testForwards() {

		getProjectSpace().getOperationManager().addOperationObserver(new SimpleOperationObserver() {

			@Override
			public void operationPerformed(AbstractOperation operation) {
				operationPerformed = true;
			}

		});

		getProject().addModelElement(createTestElement("A"));

		assertTrue(operationPerformed);
	}

	@Test
	public void testBackwards() {

		getProject().addModelElement(createTestElement("A"));

		assertFalse(operationPerformed);

		getProjectSpace().getOperationManager().addOperationObserver(new SimpleOperationObserver() {

			@Override
			public void operationPerformed(AbstractOperation operation) {
				operationPerformed = true;
			}

		});

		getProjectSpace().revert();

		assertTrue(operationPerformed);
	}
}
