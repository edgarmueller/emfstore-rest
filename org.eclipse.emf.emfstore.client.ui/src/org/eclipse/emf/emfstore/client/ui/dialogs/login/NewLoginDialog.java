package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;

public class NewLoginDialog extends TitleAreaDialog {

	private Text passwordField;
	private Button savePassword;
	private ComboViewer usernameCombo;
	private final LoginController controller;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public NewLoginDialog(Shell parentShell, LoginController controller) {
		super(parentShell);
		this.controller = controller;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitleImage(ResourceManager.getPluginImage("org.eclipse.emf.emfstore.client.ui", "icons/login_icon.png"));
		setTitle("Log in to <servername>");
		setMessage("Please enter your username and password");
		getShell().setText("Authentication required");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite loginContainer = new Composite(container, SWT.NONE);
		loginContainer.setLayout(new GridLayout(3, false));
		loginContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		loginContainer.setBounds(0, 0, 64, 64);

		Label usernameLabel = new Label(loginContainer, SWT.NONE);
		GridData gd_usernameLabel = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_usernameLabel.widthHint = 95;
		usernameLabel.setLayoutData(gd_usernameLabel);
		usernameLabel.setText("Username");

		usernameCombo = new ComboViewer(loginContainer, SWT.NONE);
		usernameCombo.addSelectionChangedListener(new ComboListener());
		Combo combo = usernameCombo.getCombo();
		GridData gd_usernameCombo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_usernameCombo.widthHint = 235;
		combo.setLayoutData(gd_usernameCombo);
		new Label(loginContainer, SWT.NONE);

		Label passwordLabel = new Label(loginContainer, SWT.NONE);
		GridData gd_passwordLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_passwordLabel.widthHint = 80;
		passwordLabel.setLayoutData(gd_passwordLabel);
		passwordLabel.setText("Password");

		passwordField = new Text(loginContainer, SWT.BORDER | SWT.PASSWORD);
		passwordField.setText("password");
		GridData gd_passwordField = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_passwordField.widthHint = 250;
		passwordField.setLayoutData(gd_passwordField);
		new Label(loginContainer, SWT.NONE);

		Label savePasswordLabel = new Label(loginContainer, SWT.NONE);
		savePasswordLabel.setText("Save Password");

		savePassword = new Button(loginContainer, SWT.CHECK);
		new Label(loginContainer, SWT.NONE);

		initData();
		loadUsersession(controller.provideUsersession());
		return area;
	}

	private void initData() {
		usernameCombo.setContentProvider(ArrayContentProvider.getInstance());
		usernameCombo.setInput(new String[] { "Otto", "Hans" });
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(400, 250);
	}

	private void loadUsersession(Usersession usersession) {
		String password = usersession.getPassword();
		if (password != null) {
			passwordField.setText(password);
		}
		savePassword.setSelection(usersession.isSavePassword());
	}

	private final class ComboListener implements ISelectionChangedListener {
		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = event.getSelection();
			if (selection instanceof StructuredSelection) {
				Object firstElement = ((StructuredSelection) selection).getFirstElement();
				if (firstElement instanceof Usersession) {
					loadUsersession((Usersession) firstElement);
				}
			}
		}
	}
}
