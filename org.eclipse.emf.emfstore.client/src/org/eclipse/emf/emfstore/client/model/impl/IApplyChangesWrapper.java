package org.eclipse.emf.emfstore.client.model.impl;

import java.util.List;

import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;

public interface IApplyChangesWrapper {

	public void wrapApplyChanges(IApplyChangesCallback callback,
			PrimaryVersionSpec baseSpec, List<ChangePackage> incoming,
			ChangePackage myChanges);
}
