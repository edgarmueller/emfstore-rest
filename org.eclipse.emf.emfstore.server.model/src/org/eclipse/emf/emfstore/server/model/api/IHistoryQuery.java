package org.eclipse.emf.emfstore.server.model.api;

import org.eclipse.emf.emfstore.server.model.versioning.util.HistoryQueryFactoryImpl;

public interface IHistoryQuery {

	IHistoryQueryFactory FACTORY = HistoryQueryFactoryImpl.INSTANCE;

}
