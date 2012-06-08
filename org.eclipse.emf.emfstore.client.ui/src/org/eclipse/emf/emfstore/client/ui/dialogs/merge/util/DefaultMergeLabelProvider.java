package org.eclipse.emf.emfstore.client.ui.dialogs.merge.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.util.MergeLabelProvider;

public class DefaultMergeLabelProvider implements MergeLabelProvider {

	private AdapterFactoryLabelProvider adapterFactory;

	public DefaultMergeLabelProvider() {
		adapterFactory = UIDecisionUtil.getAdapterFactory();
	}

	public int getPriority() {
		return 10;
	}

	public String getText(EObject modelElement) {
		return adapterFactory.getText(modelElement);
	}

	public void dispose() {
		adapterFactory.dispose();
	}
}