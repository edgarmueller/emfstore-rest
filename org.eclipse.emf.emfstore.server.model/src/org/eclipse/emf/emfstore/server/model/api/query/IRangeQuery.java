package org.eclipse.emf.emfstore.server.model.api.query;

public interface IRangeQuery extends IHistoryQuery {

	int getUpperLimit();

	void setUpperLimit(int value);

	int getLowerLimit();

	void setLowerLimit();

	void setIncludeIncoming(boolean value);

	boolean isIncludeIncoming();

	void setIncludeOutgoing(boolean value);

	boolean isIncludeOutgoing();

}
