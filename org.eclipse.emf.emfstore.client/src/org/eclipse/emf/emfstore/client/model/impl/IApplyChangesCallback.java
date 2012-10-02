package org.eclipse.emf.emfstore.client.model.impl;

import java.util.List;

import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;

public interface IApplyChangesCallback {

	public void applyChangesIntern(PrimaryVersionSpec baseSpec,
			List<ChangePackage> incoming, ChangePackage myChanges);
}
