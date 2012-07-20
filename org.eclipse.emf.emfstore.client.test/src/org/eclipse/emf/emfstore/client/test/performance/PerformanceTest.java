/**
 * <copyright> Copyright (c) 2008-2009 Jonas Helming, Maximilian Koegel. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html </copyright>
 */
package org.eclipse.emf.emfstore.client.test.performance;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.test.SetupHelper;
import org.eclipse.emf.emfstore.client.test.server.ServerTests;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.modelmutator.api.ModelMutator;
import org.eclipse.emf.emfstore.modelmutator.api.ModelMutatorConfiguration;
import org.eclipse.emf.emfstore.modelmutator.api.ModelMutatorUtil;
import org.eclipse.emf.emfstore.server.CleanMemoryTask;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This TestCase tests all methods in the main {@link org.unicase.emfstore.EmfStore} interface.
 * 
 */
public class PerformanceTest {

	private static MemoryMeter memoryMeter;

	private Usersession usersession;
	private ProjectSpace projectSpace2;
	private final String modelKey = "http://org/eclipse/example/bowling";
	private final int width = 10;
	private final int depth = 2;
	private final long seed = 1234567800;

	private long lastSeed = seed + 1;

	private static final int NUM_ITERATIONS = 1;
	private static final double ACCEPTED_VARIANCE = 1.2;
	private double memAfterThreshold;
	double[] times;
	long[] memBefore, memDuring, memAfter;
	private SetupHelper setupHelper;

	/**
	 * Start server and gain sessionid.
	 * 
	 * @throws EmfStoreException in case of failure
	 * @throws IOException
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws EmfStoreException, IOException {
		ServerTests.setUpBeforeClass();
		memoryMeter = new MemoryMeter();
		memoryMeter.start();
	}

	/**
	 * Overrides parent implementation.
	 * 
	 * @throws EmfStoreException in case of failure
	 */
	@Before
	public void beforeTest() throws EmfStoreException {
		setupHelper = new SetupHelper(modelKey, 7, 7, lastSeed);
		setupHelper.setupWorkSpace();
		setupHelper.generateRandomProject();
		setupHelper.shareProject();
		assertNotNull(setupHelper.getTestProject());
		initMeasurments();
	}

	@After
	public void afterTest() throws IOException {
		SetupHelper.cleanupWorkspace();
	}

	@AfterClass
	public static void finish() throws EmfStoreException, IOException {
		memoryMeter.finish();
		ServerTests.tearDownAfterClass();
	}

	/**
	 * Opens projects of different sizes, shares them with the server and then deletes them. r
	 * 
	 * @see org.unicase.emfstore.EmfStore#createProject(org.eclipse.emf.emfstore.server.model.SessionId, String, String,
	 *      org.eclipse.emf.emfstore.server.model.versioning.LogMessage, Project)
	 * @see org.unicase.emfstore.EmfStore#getProjectList(org.eclipse.emf.emfstore.server.model.SessionId)
	 * @throws EmfStoreException in case of failure.
	 * @throws IOException
	 */
	@Test
	public void shareProjectTest() throws EmfStoreException, IOException {

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				setupHelper.loginServer();
				usersession = setupHelper.getUsersession();
				WorkspaceManager.getInstance().getCurrentWorkspace().getUsersessions().add(usersession);
			}
		}.run(false);

		final ProjectSpace projectSpace = setupHelper.getTestProjectSpace();

		initMeasurments();

		for (int i = 0; i < NUM_ITERATIONS; i++) {
			memoryMeter.startMeasurements();
			memBefore[i] = usedMemory();
			long time = System.currentTimeMillis();
			new EMFStoreCommand() {
				@Override
				protected void doRun() {
					try {
						if (!usersession.isLoggedIn()) {
							usersession.logIn();
						}
						projectSpace.shareProject(usersession, null);
					} catch (EmfStoreException e) {
						fail();
					}
				}
			}.run(false);
			times[i] = (System.currentTimeMillis() - time) / 1000.0;

			assertNotNull(setupHelper.getTestProject());
			memAfter[i] = usedMemory();
			memDuring[i] = memoryMeter.stopMeasurements();
			ModelUtil.logInfo("share project - iteration #" + (i + 1) + ": time=" + times[i] + ", memory used before: "
				+ memBefore[i] / 1024 / 1024 + "MB, during: " + memDuring[i] / 1024 / 1024 + "MB, after: "
				+ memAfter[i] / 1024 / 1024 + "MB");

			if (i > 0 && memAfter[i] > memAfterThreshold * ACCEPTED_VARIANCE) {
				fail();
			}
			memAfterThreshold = memAfter[i];

			new EMFStoreCommand() {
				@Override
				protected void doRun() {
					try {
						WorkspaceManager.getInstance().getConnectionManager()
							.deleteProject(usersession.getSessionId(), projectSpace.getProjectId(), true);
					} catch (EmfStoreException e) {
						e.printStackTrace();
					}
				}
			}.run(false);
		} // for loop with iterations
		ModelUtil.logInfo("times=" + Arrays.toString(times));
		usersession = null;
		WorkspaceManager.getInstance().reinit();
	}

	/**
	 * Measures average time, spent for the checkout operation. Opens projects of different sizes, shares them with the
	 * server, checkouts and then deletes them.
	 * 
	 * @see org.unicase.emfstore.EmfStore#createProject(org.eclipse.emf.emfstore.server.model.SessionId, String, String,
	 *      org.eclipse.emf.emfstore.server.model.versioning.LogMessage, Project)
	 * @see org.unicase.emfstore.EmfStore#getProjectList(org.eclipse.emf.emfstore.server.model.SessionId)
	 * @throws EmfStoreException in case of failure.
	 */
	@Test
	public void checkoutProjectTest() throws EmfStoreException {

		final ProjectSpace projectSpace = setupHelper.getTestProjectSpace();
		long memAfterThreshold = 0;
		for (int i = 0; i < NUM_ITERATIONS; i++) {
			memoryMeter.startMeasurements();
			memBefore[i] = usedMemory();
			long time = System.currentTimeMillis();

			new EMFStoreCommand() {
				@Override
				protected void doRun() {
					try {
						projectSpace2 = WorkspaceManager.getInstance().getCurrentWorkspace()
							.checkout(setupHelper.getUsersession(), projectSpace.getProjectInfo());
					} catch (EmfStoreException e) {
						e.printStackTrace();
					}
				}
			}.run(false);
			times[i] = (System.currentTimeMillis() - time) / 1000.0;
			memAfter[i] = usedMemory();
			memDuring[i] = memoryMeter.stopMeasurements();
			ModelUtil.logInfo("checkout project " + projectSpace.getProjectName() + " iteration #" + (i + 1)
				+ ": time=" + times[i] + ", memory used before: " + memBefore[i] / 1024 / 1024 + "MB, during: "
				+ memDuring[i] / 1024 / 1024 + "MB, after: " + memAfter[i] / 1024 / 1024 + "MB");

			if (i > 0 && memAfter[i] > memAfterThreshold * 1.2) {
				fail();
			}
			memAfterThreshold = memAfter[i];

			new EMFStoreCommand() {
				@Override
				protected void doRun() {
					try {
						WorkspaceManager.getInstance().getCurrentWorkspace().deleteProjectSpace(projectSpace2);
						projectSpace2 = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.run(false);
		}

		ModelUtil.logInfo("times=" + Arrays.toString(times));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					WorkspaceManager.getInstance().getConnectionManager()
						.deleteProject(setupHelper.getUsersession().getSessionId(), projectSpace.getProjectId(), true);
				} catch (EmfStoreException e) {
					e.printStackTrace();
				} finally {
					SetupHelper.cleanupServer();
				}
			}
		}.run(false);
	}

	/**
	 * Measures average time, spent for the commit and update operations. Opens projects of different sizes, shares them
	 * with the server and checks it out as two different projects. Then the test generates changes in one of the
	 * projects, using the ModelMutator, commits them to the server, and updates the second project. The test performs
	 * model change, commit and update NUM_ITERATIONS times and calculates times for commit and update operations
	 * 
	 * @see org.unicase.emfstore.EmfStore#createProject(org.eclipse.emf.emfstore.server.model.SessionId, String, String,
	 *      org.eclipse.emf.emfstore.server.model.versioning.LogMessage, Project)
	 * @see org.unicase.emfstore.EmfStore#getProjectList(org.eclipse.emf.emfstore.server.model.SessionId)
	 * @throws EmfStoreException in case of failure.
	 */
	@Test
	public void commitAndUpdateProjectTest() throws EmfStoreException {

		final SetupHelper setupHelper2 = new SetupHelper((String) null);
		setupHelper2.setupWorkSpace();
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					setupHelper2.loginServer();
					Usersession usersession2 = setupHelper2.getUsersession();
					setupHelper2.getWorkSpace().getUsersessions().add(usersession2);
					// projectSpace2 = usersession2.checkout(setupHelper1.getTestProjectSpace().getProjectInfo());
					projectSpace2 = WorkspaceManager.getInstance().getCurrentWorkspace()
						.checkout(usersession2, setupHelper.getTestProjectSpace().getProjectInfo());
				} catch (EmfStoreException e) {
					e.printStackTrace();
				}
			}
		}.run(false);

		final ProjectSpace projectSpace1 = setupHelper.getTestProjectSpace();
		double[] modelChangeTimes = new double[NUM_ITERATIONS];
		double[] commitTimes = new double[NUM_ITERATIONS];
		double[] updateTimes = new double[NUM_ITERATIONS];
		long[] memBeforeMut = new long[NUM_ITERATIONS];
		long[] memDuringMut = new long[NUM_ITERATIONS];
		long[] memAfterMut = new long[NUM_ITERATIONS];
		long[] memDuringCommit = new long[NUM_ITERATIONS];
		long[] memAfterCommit = new long[NUM_ITERATIONS];
		long[] memDuringUpdate = new long[NUM_ITERATIONS];
		long[] memAfterUpdate = new long[NUM_ITERATIONS];

		for (int i = 0; i < NUM_ITERATIONS; i++) {
			memoryMeter.startMeasurements();
			memBeforeMut[i] = usedMemory();
			long time = System.currentTimeMillis();
			changeModel(projectSpace1);
			modelChangeTimes[i] = (System.currentTimeMillis() - time) / 1000.0;

			memDuringMut[i] = memoryMeter.stopMeasurements();
			memAfterMut[i] = usedMemory();
			ModelUtil.logInfo("change model-  iteration #" + (i + 1) + ": time=" + modelChangeTimes[i]
				+ " memory used before:" + memBeforeMut[i] / 1024 / 1024 + "MB, during: " + memDuringMut[i] / 1024
				/ 1024 + "MB, after: " + memAfterMut[i] / 1024 / 1024 + "MB");

			System.out.println("VERSION BEFORE commit:" + projectSpace1.getProjectInfo().getVersion().getIdentifier());
			time = System.currentTimeMillis();
			new EMFStoreCommand() {
				@Override
				protected void doRun() {
					try {
						projectSpace1.update();
					} catch (EmfStoreException e) {
						e.printStackTrace();
					}
				}
			}.run(false);
			memoryMeter.startMeasurements();
			time = System.currentTimeMillis();

			new EMFStoreCommand() {
				@Override
				protected void doRun() {
					try {
						projectSpace1.commit(null, null, null);
					} catch (EmfStoreException e) {
						fail();
					}
				}
			}.run(false);

			commitTimes[i] = (System.currentTimeMillis() - time) / 1000.0;

			memDuringCommit[i] = memoryMeter.stopMeasurements();
			memAfterCommit[i] = usedMemory();
			ModelUtil.logInfo("commit project - iteration #" + (i + 1) + ": time=" + commitTimes[i]
				+ ", memory used before: " + memAfterMut[i] / 1024 / 1024 + "MB, during: " + memDuringCommit[i] / 1024
				/ 1024 + "MB, after: " + memAfterCommit[i] / 1024 / 1024 + "MB");
			if (i > 0 && memAfter[i] > memAfterThreshold * ACCEPTED_VARIANCE) {
				fail();
			}
			memAfterThreshold = memAfter[i];

			memoryMeter.startMeasurements();
			time = System.currentTimeMillis();
			new EMFStoreCommand() {
				@Override
				protected void doRun() {
					try {
						projectSpace2.update();
					} catch (EmfStoreException e) {
						e.printStackTrace();
					}
				}
			}.run(false);
			updateTimes[i] = (System.currentTimeMillis() - time) / 1000.0;
			CleanMemoryTask task = new CleanMemoryTask(WorkspaceManager.getInstance().getCurrentWorkspace()
				.getResourceSet());
			task.run();
			memDuringUpdate[i] = memoryMeter.stopMeasurements();
			memAfterUpdate[i] = usedMemory();
			ModelUtil.logInfo("update project - iteration #" + (i + 1) + ": time=" + updateTimes[i]
				+ ", memory used before: " + memAfterCommit[i] / 1024 / 1024 + "MB, during: " + memDuringUpdate[i]
				/ 1024 / 1024 + "MB, after: " + memAfterUpdate[i] / 1024 / 1024 + "MB");

			if (i > 0 && memAfter[i] > memAfterThreshold * ACCEPTED_VARIANCE) {
				fail();
			}
			memAfterThreshold = memAfter[i];
		}

		ModelUtil.logInfo("Mutate model - average=" + average(modelChangeTimes) + ", min=" + min(modelChangeTimes)
			+ ", max=" + max(modelChangeTimes) + ", mean=" + mean(modelChangeTimes));

		WorkspaceManager.getInstance().reinit();
		// new EMFStoreCommand() {
		// @Override
		// protected void doRun() {
		// try {
		// getConnectionManager().deleteProject(setupHelper.getUsersession().getSessionId(),
		// projectSpace1.getProjectId(), true);
		// WorkspaceManager.getInstance().getCurrentWorkspace().deleteProjectSpace(projectSpace1);
		// WorkspaceManager.getInstance().getCurrentWorkspace().deleteProjectSpace(projectSpace2);
		// WorkspaceManager.getInstance().getCurrentWorkspace().getUsersessions()
		// .remove(setupHelper.getUsersession());
		// WorkspaceManager.getInstance().getCurrentWorkspace().getUsersessions()
		// .remove(setupHelper2.getUsersession());
		// } catch (IOException e) {
		// e.printStackTrace();
		// } catch (EmfStoreException e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// SetupHelper.cleanupWorkspace();
		// } catch (IOException e) {
		// throw new RuntimeException(e);
		// }
		// SetupHelper.cleanupServer();
		// projectSpace2 = null;
		// }
		// }
		// }.run(false);
	}

	public void changeModel(final ProjectSpace prjSpace) {
		lastSeed = lastSeed == seed ? seed + 1 : seed;
		final ModelMutatorConfiguration mmc = new ModelMutatorConfiguration(ModelMutatorUtil.getEPackage(modelKey),
			prjSpace.getProject(), lastSeed);
		mmc.setDepth(depth);
		mmc.setWidth(width);
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				long time = System.currentTimeMillis();
				ModelMutator.changeModel(mmc);
				System.out.println("Changed model: " + (System.currentTimeMillis() - time) / 1000.0 + "sec");
			}
		}.run(false);
		System.out.println("Number of changes: " + prjSpace.getOperations().size());
	}

	public static long usedMemory() {
		Runtime.getRuntime().gc();
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

	public static double min(double[] arr) {
		double min = Double.MAX_VALUE;
		for (double x : arr) {
			if (x < min) {
				min = x;
			}
		}
		return min;
	}

	public static double max(double[] arr) {
		double max = Double.MIN_VALUE;
		for (double x : arr) {
			if (x > max) {
				max = x;
			}
		}
		return max;
	}

	public static double average(double[] arr) {
		double sum = 0.0;
		for (double x : arr) {
			sum += x;
		}
		return (int) (sum / arr.length * 1000.0) / 1000.0;
	}

	public static double mean(double[] arr) {
		Arrays.sort(arr);
		int ind = arr.length / 2 - 1 + arr.length % 2;
		return arr[ind];
	}

	private void initMeasurments() {
		times = new double[NUM_ITERATIONS];
		memBefore = new long[NUM_ITERATIONS];
		memDuring = new long[NUM_ITERATIONS];
		memAfter = new long[NUM_ITERATIONS];
	}

	/**
	 * Class that measures memory, used during some operation(s) continuously and returns maximal value at the end.
	 */
	public static class MemoryMeter extends Thread {
		/**
		 * Period to wait (in milliseconds) between memory measurements.
		 **/
		private static final int MEASUREMENT_PERIOD = 250;

		private boolean stop = false;
		private boolean active;
		private volatile long maxUsedMemory;

		@Override
		public void run() {
			startMeasurements();
			try {
				while (!stop) {
					if (active) {
						long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
						if (usedMemory > maxUsedMemory) {
							maxUsedMemory = usedMemory;
						}
					}
					Thread.sleep(MEASUREMENT_PERIOD);
				}
			} catch (InterruptedException e) {
			}
		}

		public void startMeasurements() {
			active = true;
			maxUsedMemory = 0;
		}

		public long stopMeasurements() {
			active = false;
			long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			long curMaxMemory = maxUsedMemory;
			if (usedMemory > curMaxMemory) {
				curMaxMemory = usedMemory;
			}
			return curMaxMemory;
		}

		public void finish() {
			stop = true;
		}
	}
}
