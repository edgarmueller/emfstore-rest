package org.eclipse.emf.emfstore.client.ui.handlers;

/**
 * Indicates that a required selection is missing.
 * 
 * @author emueller
 */
public class RequiredSelectionException extends RuntimeException {

	private static final long serialVersionUID = 3011252354930520148L;

	public RequiredSelectionException() {
		super("The selected element is invalid for this action.");
	}

	public RequiredSelectionException(String msg) {
		super(msg);
	}
}
