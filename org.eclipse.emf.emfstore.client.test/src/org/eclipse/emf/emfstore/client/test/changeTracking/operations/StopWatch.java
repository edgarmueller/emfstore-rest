package org.eclipse.emf.emfstore.client.test.changeTracking.operations;

public class StopWatch {

	private final String name;
	private long startTime;
	private boolean silent;

	public StopWatch(String name) {
		this.name = name;
		start();
	}

	public void start() {
		startTime = System.currentTimeMillis();
	}

	public StopWatch silent() {
		this.silent = true;
		return this;
	}

	public void stop() {
		long timeElapsed = System.currentTimeMillis() - startTime;
		log("stopped, total time " + timeElapsed);
	}

	private void log(String message) {
		if (silent) {
			return;
		}
		System.out.println(name + " at " + System.currentTimeMillis() + ": " + message);
	}
}
