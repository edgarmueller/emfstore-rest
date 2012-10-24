package org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin.acimport.wizard;

import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class AcUserImportPageThree extends WizardPage {

	public AcUserImportPageThree() {
		super("CSV Import");
	}

	protected AcUserImportPageThree(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		FileFieldEditor editor = new FileFieldEditor("RRRR", "blub", composite);
		setControl(composite);
	}

}
