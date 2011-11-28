package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class EMFStoreHandlerUtil {

	/**
	 * Extracts element from event.
	 * 
	 * @param event the event
	 * @param clazz class type of the object to extract
	 * @param <T> the type of the object to extract
	 * @return the object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSelection(ExecutionEvent event, Class<T> clazz) {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel == null) {
			sel = HandlerUtil.getActiveMenuSelection(event);
		}
		if (!(sel instanceof IStructuredSelection)) {
			return null;
		}

		IStructuredSelection structuredSelection = (IStructuredSelection) sel;
		if (structuredSelection.isEmpty()) {
			return null;
		}

		Object selectedElement = structuredSelection.getFirstElement();
		if (!(clazz.isInstance(selectedElement))) {
			return null;
		}
		return (T) selectedElement;
	}

	public static <T> T requireSelection(ExecutionEvent event, Class<T> clazz) throws RequiredSelectionException {
		T selection = getSelection(event, clazz);
		if (selection == null) {
			throw new RequiredSelectionException();
		}
		return selection;
	}

}
