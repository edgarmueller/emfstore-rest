package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractEMFStoreHandler extends AbstractHandler {

	private ExecutionEvent event;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.event = event;
		doExecute();
		return null;
	}

	public abstract Object doExecute();

	protected ExecutionEvent getEvent() {
		return event;
	}

	public <T> T requireSelection(Class<T> clazz) throws RequiredSelectionException {
		return EMFStoreHandlerUtil.requireSelection(getEvent(), clazz);
	}

	public <T> T getSelection(Class<T> clazz) {
		return EMFStoreHandlerUtil.getSelection(getEvent(), clazz);
	}

	public Shell getShell() {
		return Display.getCurrent().getActiveShell();
	}
}
