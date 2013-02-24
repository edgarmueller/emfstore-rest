package org.eclipse.emf.emfstore.internal.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.internal.common.api.APIDelegate;
import org.eclipse.emf.emfstore.internal.common.api.InternalAPIDelegator;


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
	
	public static <T extends APIDelegate<U>, U> List<U> mapToInverse(List<T> toCopy) {
		if(toCopy == null) {
			return null;
		}
		ArrayList<U> result = new ArrayList<U>(toCopy.size());
		for(T element : toCopy) {
			result.add(element.getAPIImpl());
		}
		return result;
	}


	/**
	 * Maps a list of a given internal type to its corresponding API type by copying it. 
	 * 
	 * @param toCopy
	 * 			the list to be copied
	 * @return the copied list with its elements mapped to their API type 
	 * 
	 * @param <T> the internal API mapping to an API implementation class
	 * @param <V> the type of the API interface
	 * @param <U> the type of the API implementation class
	 */
	// T = Usersession
	// U = ESUsersessionImpl
	// V = ESUsersession
	public static <T extends APIDelegate<U>, V, U extends V> List<V> mapToAPI(Class<V> apiClass, List<T> toCopy) {
		if(toCopy == null) {
			return null;
		}
		ArrayList<V> result = new ArrayList<V>(toCopy.size());
		for(T element : toCopy) {
			result.add(element.getAPIImpl());
		}
		return result;
	}

}
