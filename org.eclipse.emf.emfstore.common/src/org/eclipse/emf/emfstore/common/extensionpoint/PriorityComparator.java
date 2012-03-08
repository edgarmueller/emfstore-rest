package org.eclipse.emf.emfstore.common.extensionpoint;

import java.util.Comparator;

public class PriorityComparator implements Comparator<ExtensionElement> {

	private final String fieldname;
	private final boolean desc;
	

	public PriorityComparator() {
		this("priority", false);
	}
	
	public PriorityComparator(boolean descending) {
		this("priority", descending);
	}

	public PriorityComparator(String fieldname, boolean descending) {
		this.fieldname = fieldname;
		this.desc = descending;

	}

	public int compare(ExtensionElement o1, ExtensionElement o2) {
		try {
			o1.setThrowException(true);
			o2.setThrowException(true);
			return o1.getInteger(this.fieldname).compareTo(o2.getInteger(this.fieldname)) * ((desc)?-1:1);
		} catch (ExtensionPointException e) {
			return 0;
		}
	}

}
