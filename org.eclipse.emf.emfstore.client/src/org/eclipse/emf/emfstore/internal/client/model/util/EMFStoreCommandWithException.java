package org.eclipse.emf.emfstore.internal.client.model.util;

public abstract class EMFStoreCommandWithException<T> extends EMFStoreCommand {

	private T excpetion;

	public T getExcpetion() {
		return excpetion;
	}

	public void setExcpetion(T excpetion) {
		this.excpetion = excpetion;
	}

	public boolean hasException() {
		return excpetion != null;
	}
}
