package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import org.eclipse.emf.emfstore.client.model.ModelFactory;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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

public class LoginDialog extends TitleAreaDialog {

	private Text passwordField;
	private Button savePassword;
	private ComboViewer usernameCombo;
	private final ILoginDialogController controller;
	private Usersession selectedUsersession;
	private boolean passwordModified;
	private Button okButton;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public LoginDialog(Shell parentShell, ILoginDialogController controller) {
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
		setTitle("Log in to " + controller.getServerLabel());
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
		if (controller.isUsersessionLocked()) {
			combo.setEnabled(false);
			combo.setText(controller.getUsersession().getUsername());
		}
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
		// passwordField.setText("password");
		GridData gd_passwordField = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_passwordField.widthHint = 250;
		passwordField.setLayoutData(gd_passwordField);
		passwordField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				passwordModified = true;
				flushErrorMessage();
			}
		});
		new Label(loginContainer, SWT.NONE);

		Label savePasswordLabel = new Label(loginContainer, SWT.NONE);
		savePasswordLabel.setText("Save Password");

		savePassword = new Button(loginContainer, SWT.CHECK);
		new Label(loginContainer, SWT.NONE);

		initData();
		loadUsersession(controller.getUsersession());
		return area;
	}

	private void initData() {
		usernameCombo.setContentProvider(ArrayContentProvider.getInstance());
		if (!controller.isUsersessionLocked()) {
			usernameCombo.setInput(controller.getKnownUsersessions());
		}
	}

	/**
	 * Fills the login dialog data according to the given {@link Usersession}.
	 * 
	 * @param usersession
	 *            the user session to be loaded
	 */
	private void loadUsersession(Usersession usersession) {

		// session may be null; this is the case when LoginDialogController#login
		// has been called with a server info instead of an user session
		if (usersession == null || usersession.getUsername() == null || usersession.getUsername().equals("")) {
			return;
		}

		selectedUsersession = usersession;

		if (selectedUsersession.isSavePassword() && selectedUsersession.getPassword() != null) {
			// passwordField.setText(selectedUsersession.getPassword());
		}

		passwordField.setMessage("Otto is doof");

		passwordModified = false;
		savePassword.setSelection(selectedUsersession.isSavePassword());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		try {

			if (selectedUsersession == null) {
				selectedUsersession = ModelFactory.eINSTANCE.createUsersession();
			}

			selectedUsersession.setUsername(usernameCombo.getCombo().getText());
			selectedUsersession.setSavePassword(savePassword.getSelection());
			selectedUsersession.setServerInfo(controller.getServerInfo());

			if (passwordModified) {
				selectedUsersession.setPassword(passwordField.getText());
			}

			controller.validate(selectedUsersession);
		} catch (EmfStoreException e) {
			setErrorMessage(e.getMessage());
			return;
		}
		super.okPressed();
	}

	/**
	 * Returns the selected {@link Usersession}.
	 * 
	 * @return the user session selected by the user
	 */
	public Usersession getSelectedUsersession() {
		return selectedUsersession;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#getInitialSize()
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(400, 250);
	}

	/**
	 * Clears the error message.
	 */
	private void flushErrorMessage() {
		setErrorMessage(null);
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
			flushErrorMessage();
		}
	}

}
