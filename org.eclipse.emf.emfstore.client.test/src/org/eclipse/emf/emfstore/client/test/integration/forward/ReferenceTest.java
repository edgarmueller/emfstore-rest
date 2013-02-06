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
package org.eclipse.emf.emfstore.client.test.integration.forward;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.server.exceptions.EMFStoreException;
import org.junit.Test;

/**
 * @author Hodaie
 */
public class ReferenceTest extends IntegrationTest {

	private long randomSeed = 1;

	/**
	 * Takes a random ME (meA). Takes randomly one of its containment references. Creates a new ME matching containment
	 * reference type (meB). Adds created meB to meA's containment reference.
	 * 
	 * @throws EMFStoreException EmfStoreException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void containmentReferenceAddNewTest() throws SerializationException, EMFStoreException {
		System.out.println("ContainmentReferenceAddNewTest");

		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testHelper.doContainemntReferenceAddNew();

			}

		}.run(false);

		commitChanges();
		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));

	}

	/**
	 * This takes a random model element (meA). Takes one of its containments (meToMove). Takes containing reference of
	 * meToMove. Finds another ME of type meA (meB). Moves meToMove to meB. Finds yet another ME of type meA (meC) .
	 * Moves meToMove to meC.
	 * 
	 * @throws EMFStoreException EmfStoreException
	 * @throws SerializationException SerializationException
	 */
	// @Test
	public void containmentRefTransitiveChangeTest() throws SerializationException, EMFStoreException {
		System.out.println("ContainmentRefTransitiveChangeTest");

		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doContainmentRefTransitiveChange();
			}

		}.run(false);

		commitChanges();
		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

	/**
	 * This move an element in a many reference list to another position.
	 * 
	 * @throws EMFStoreException EmfStoreException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void multiReferenceMoveTest() throws SerializationException, EMFStoreException {
		System.out.println("MultiReferenceMoveTest");
		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testHelper.doMultiReferenceMove();
			}
		}.run(false);

		commitChanges();
		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

	/**
	 * Select a random ME (meA). Select one of its non-containment references. Find an ME matching reference type (meB).
	 * Add meB to meA.
	 * 
	 * @throws EMFStoreException EmfStoreException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void nonContainmentReferenceAddTest() throws SerializationException, EMFStoreException {
		System.out.println("NonContainmentReferenceAddTest");
		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doNonContainmentReferenceAdd();
			}

		}.run(false);

		commitChanges();
		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

	/**
	 * Removes a referenced model element form a non-containment reference of a randomly selected ME.
	 * 
	 * @throws EMFStoreException EmfStoreException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void nonContainmentReferenceRemoveTest() throws SerializationException, EMFStoreException {
		System.out.println("NonContainmentReferenceRemoveTest");

		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doNonContainmentReferenceRemove();
			}
		}.run(false);

		commitChanges();
		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

	/**
	 * Takes a random ME (meA). Takes randomly one of its containment references. Finds an existing ME in project
	 * matching the reference type (meB). Adds meB to this reference of meA (moves meB from its old parent to meA).
	 * 
	 * @throws EMFStoreException EmfStoreException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void containmentReferenceMoveTest() throws SerializationException, EMFStoreException {
		System.out.println("ContainmentReferenceMoveTest");
		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doContainmentReferenceMove();
			}
		}.run(false);

		commitChanges();
		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));

	}

}