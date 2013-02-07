package org.eclipse.emf.emfstore.client.test.api;

import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.client.api.ILocalProject;

public final class ProjectChangeUtil {

	private ProjectChangeUtil() {
	}

	public static Player addPlayerToProject(ILocalProject localProject) {
		Player player = BowlingFactory.eINSTANCE.createPlayer();
		localProject.getModelElements().add(player);
		return player;
	}
}
