package org.eclipse.emf.emfstore.client.ui.dialogs;

import java.util.List;

import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public class BranchSelectionDialog extends TitleAreaDialog {
	protected final java.util.List<BranchInfo> branches;
	protected TableViewer tableViewer;
	protected BranchInfo result;
	protected final PrimaryVersionSpec baseVersion;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @param primaryVersionSpec
	 */
	public BranchSelectionDialog(Shell parentShell, PrimaryVersionSpec baseVersion, java.util.List<BranchInfo> branches) {
		super(parentShell);
		this.baseVersion = baseVersion;
		this.branches = branches;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		setHeaderTexts();

		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		addCreationField(container);

		tableViewer = new TableViewer(container, SWT.BORDER | SWT.V_SCROLL);
		Table list = tableViewer.getTable();
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		tableViewer.setLabelProvider(new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Object element = cell.getElement();
				if (element instanceof BranchInfo) {
					BranchInfo branch = (BranchInfo) element;
					StyledString styledString = new StyledString("Branch:  " + branch.getName() + "  ", StyledString
						.createColorRegistryStyler("red", null));
					styledString.append("[Version: " + branch.getHead().getIdentifier() + "]",
						StyledString.DECORATIONS_STYLER);
					cell.setText(styledString.toString());
					cell.setStyleRanges(styledString.getStyleRanges());
				}

				super.update(cell);
			}
		});

		tableViewer.setInput(branches);

		endOfInit();

		return area;
	}

	protected void endOfInit() {
	}

	@Override
	protected void okPressed() {
		ISelection selection = tableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			result = (BranchInfo) ((IStructuredSelection) selection).getFirstElement();
		}
		super.okPressed();
	}

	public BranchInfo getResult() {
		return result;
	}

	protected void setHeaderTexts() {
		setTitle("Branch Selection");
		setMessage("Please select which Branch you want to merge into your local copy of the project.");
	}

	protected void addCreationField(Composite container) {
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
		return new Point(400, 350);
	}

	static public class CheckoutSelection extends BranchSelectionDialog {

		public CheckoutSelection(Shell parentShell, List<BranchInfo> branches) {
			super(parentShell, null, branches);
		}

		@Override
		protected void setHeaderTexts() {
			setTitle("Checkout Branch");
			setMessage("Please choose which Branch should be checked out.");
		}

	}

	static public class Creation extends BranchSelectionDialog {

		private Text text;
		private String newName = "";

		public Creation(Shell parentShell, PrimaryVersionSpec baseVersion, java.util.List<BranchInfo> branches) {
			super(parentShell, baseVersion, branches);
		}

		@Override
		protected void setHeaderTexts() {
			setTitle("Create Branch");
			setMessage("Please specify a name for the new Branch.");
		}

		public String getNewBranch() {
			return newName;
		}

		@Override
		protected void endOfInit() {
			tableViewer.getTable().setEnabled(false);
			tableViewer.getTable().setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
		}

		@Override
		protected void okPressed() {
			if (text != null) {
				newName = text.getText();
			}
			super.okPressed();
		}

		@Override
		protected void addCreationField(Composite container) {
			Composite creationContainer = new Composite(container, SWT.NONE);
			creationContainer.setLayout(new GridLayout(2, false));
			creationContainer.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));

			Label lblNewBranch = new Label(creationContainer, SWT.NONE);
			lblNewBranch.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblNewBranch.setText("New Branch:");

			text = new Text(creationContainer, SWT.BORDER);
			text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
	}
}
