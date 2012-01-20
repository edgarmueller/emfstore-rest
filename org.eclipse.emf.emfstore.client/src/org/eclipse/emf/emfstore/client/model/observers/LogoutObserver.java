package org.eclipse.emf.emfstore.client.model.observers;

import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.common.observer.IObserver;

public interface LogoutObserver extends IObserver {

	void logoutCompleted(Usersession session);
}
