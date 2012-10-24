package org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.dialogs.admin.acimport.wizard;

import org.eclipse.emf.emfstore.client.ui.Activator;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class LdapImportWizardPage extends WizardPage {

	private Text serverName;
	private Text ldapBase;

	public LdapImportWizardPage() {
		super("LDAP Import");

	}

	// protected LdapImportWizardPage(String pageName) {
	// super(pageName);
	// }

	public void createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		// Set the specific help for this Composite
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, Activator.PLUGIN_ID + ".help_import_ldap");

		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		setTitle("LDAP server data");
		setMessage("Enter data of server to connect to");

		Label name = new Label(composite, SWT.NULL);
		name.setText("Server name:");
		serverName = new Text(composite, SWT.SINGLE | SWT.BORDER);
		serverName.setSize(350, 20);

		Label desc = new Label(composite, SWT.NULL);
		desc.setText("Server base:");
		ldapBase = new Text(composite, SWT.SINGLE | SWT.BORDER);
		ldapBase.setSize(350, 20);

		Point defaultMargins = LayoutConstants.getMargins();
		GridLayoutFactory.fillDefaults().numColumns(2).margins(defaultMargins.x, defaultMargins.y)
			.generateLayout(composite);

		setControl(composite);
	}

}
