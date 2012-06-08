package org.eclipse.emf.emfstore.client.test.conflictDetection.merging;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AttributeMergeTest.class, MultiAttributeMergeTest.class, SingleReferenceMergeTest.class,
	SingleReferenceVsMultiMergeTets.class, MultiReferenceMergeTest.class, MultiReferenceContainmentMergeTest.class,
	DeleteMergeTest.class, CompositeMergeTest.class })
public class AllMergeTests {

}
