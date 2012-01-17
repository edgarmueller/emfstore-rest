package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractEMFStoreHandlerWithResult<T> extends AbstractHandler {

	private ExecutionEvent event;

	public T execute(ExecutionEvent event) throws ExecutionException {
		this.event = event;

		new EMFStoreUICommandWithResult<T>() {
			@Override
			protected T doRun() {
				return handleWithResult();
			}
		}.run(false);

		return null;
	}

	public abstract T handleWithResult();

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
