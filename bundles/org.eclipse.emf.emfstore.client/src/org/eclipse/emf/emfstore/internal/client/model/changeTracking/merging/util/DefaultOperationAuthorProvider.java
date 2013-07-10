package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

public class DefaultOperationAuthorProvider implements OperationAuthorProvider {

	Map<AbstractOperation, String> operationAuthorMap;

	public DefaultOperationAuthorProvider(List<ChangePackage> leftChanges,
		List<ChangePackage> rightChanges) {
		operationAuthorMap = new LinkedHashMap<AbstractOperation, String>();
		for (ChangePackage changePackage : leftChanges) {
			scanIntoAuthorMap(changePackage);
		}
		for (ChangePackage changePackage : rightChanges) {
			scanIntoAuthorMap(changePackage);
		}

	}

	private void scanIntoAuthorMap(ChangePackage changePackage) {
		if (changePackage.getLogMessage() != null && changePackage.getLogMessage().getAuthor() != null) {
			String author = changePackage.getLogMessage().getAuthor();
			for (AbstractOperation operation : changePackage.getOperations()) {
				operationAuthorMap.put(operation, author);
			}
		}

	}

	public String getAuthor(AbstractOperation operation) {
		String author = operationAuthorMap.get(operation);
		if (author == null) {
			author = "UNKOWN";
		}
		return author;
	}

}
