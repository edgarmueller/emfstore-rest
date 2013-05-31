package org.eclipse.emf.emfstore.internal.client.ui.controller;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.emfstore.internal.client.ui.controller.messages"; //$NON-NLS-1$
	public static String UIAddTagController_ErrorReason;
	public static String UIAddTagController_ErrorTitle;
	public static String UIAddTagController_TagNameTextDefault;
	public static String UIAddTagController_TagNameLabel;
	public static String UIAddTagController_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
