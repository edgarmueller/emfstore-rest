package org.eclipse.emf.emfstore.client.ui.handlers;

/**
 * Indicates that a required selection is missing.
 * 
 * @author emueller
 */
public class RequiredSelectionException extends RuntimeException {

	private static final long serialVersionUID = 3011252354930520148L;

	public RequiredSelectionException() {

	}

	public RequiredSelectionException(String msg) {
		super(msg);
	}
}
