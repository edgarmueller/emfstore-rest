package org.eclipse.emf.emfstore.internal.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.BasicInternalEList;

public class ListUtil {

	private ListUtil(){
	}

	
	public static <U,T extends U> List<U> copy(List<T> toCopy) {
		if(toCopy == null) {
			return null;
		}
		ArrayList<U> result = new ArrayList<U>(toCopy.size());
		for(T element : toCopy) {
			result.add(element);
		}
		return result;
	}
}
