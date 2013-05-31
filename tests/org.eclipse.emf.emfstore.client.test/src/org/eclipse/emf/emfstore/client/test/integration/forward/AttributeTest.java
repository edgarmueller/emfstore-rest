/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * Hodaie
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.integration.forward;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.SerializationException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Test;

/**
 * @author Hodaie
 */
public class AttributeTest extends IntegrationTest {

	private long randomSeed = 1;

	/**
	 * Finds an attribute with isMany = true and moves elements inside this attribute.
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void multiAttributeMoveTest() throws SerializationException, ESException {
		System.out.println("MultiAttributeMoveTest");

		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testHelper.doMultiAttributeMove();
			}
		}.run(false);

		commitChanges();
		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));

	}

	/**
	 * 1. Get a random model element form test project; 2. get randomly one of its attributes. 3. change the attribute
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void attributeChangeTest() throws SerializationException, ESException {
		System.out.println("AttributeChangeTest");

		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doChangeAttribute();
			}

		}.run(false);

		commitChanges();

		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));

	}

	/**
	 * Change the same attribute on a randomly selected ME twice.
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void attributeTransitiveChangeTest() throws SerializationException, ESException {
		System.out.println("AttributeTransitiveChangeTest");
		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doAttributeTransitiveChange();

			}

		}.run(false);

		commitChanges();
		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

}
