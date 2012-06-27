package org.eclipse.emf.emfstore.common;

public interface IReinitializable extends IDisposable {
	
	boolean isDisposed();

	void reinit();
}
