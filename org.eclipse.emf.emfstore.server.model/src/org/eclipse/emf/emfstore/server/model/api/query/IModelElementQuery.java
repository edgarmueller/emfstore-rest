package org.eclipse.emf.emfstore.server.model.api.query;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.api.IModelElementId;

public interface IModelElementQuery extends IRangeQuery {

	List<IModelElementId> getModelElements();
}
