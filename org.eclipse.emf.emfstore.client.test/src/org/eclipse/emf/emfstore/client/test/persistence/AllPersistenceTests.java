package org.eclipse.emf.emfstore.client.test.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite for running all tests of workspace.
 * 
 * @author emueller
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ImportExportTest.class, PersistenceTest.class, ProjectCacheTest.class })
public class AllPersistenceTests {

}