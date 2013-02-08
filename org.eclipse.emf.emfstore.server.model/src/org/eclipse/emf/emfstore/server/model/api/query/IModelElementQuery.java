package org.eclipse.emf.emfstore.server.model.api.query;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.IModelElementId;

public interface IModelElementQuery extends IRangeQuery {

	List<IModelElementId> getModelElementIds();

	void addModelElementId(IModelElementId id);

	void removeModelElementId(IModelElementId id);
}
