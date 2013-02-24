package org.eclipse.emf.emfstore.internal.client.model.util;

public abstract class EMFStoreCommandWithException<T> extends EMFStoreCommand {

	private T excpetion;

	public T getException() {
		return excpetion;
	}

	public void setException(T excpetion) {
		this.excpetion = excpetion;
	}

	public boolean hasException() {
		return excpetion != null;
	}
}
