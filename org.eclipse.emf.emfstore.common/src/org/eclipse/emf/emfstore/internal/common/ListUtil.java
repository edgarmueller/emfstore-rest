package org.eclipse.emf.emfstore.internal.common;

import java.util.ArrayList;
import java.util.List;

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
