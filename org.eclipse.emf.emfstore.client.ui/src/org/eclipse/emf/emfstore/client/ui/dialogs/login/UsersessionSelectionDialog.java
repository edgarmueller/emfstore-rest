package org.eclipse.emf.emfstore.client.ui.dialogs.login;

import java.util.HashSet;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

public class UsersessionSelectionDialog extends Dialog {

	private final LoginController controller;
	private TreeViewer treeViewer;
	private Usersession result;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public UsersessionSelectionDialog(Shell parentShell, LoginController controller) {
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
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel
			.setText("Please select or create your usersession. \n TODO allow to create usersessions and serverinfos");

		treeViewer = new TreeViewer(container, SWT.BORDER);
		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		treeViewer.setContentProvider(new UsersessionContentProvider());
		treeViewer.setLabelProvider(new UsersessionsLabelProvider());

		treeViewer.setInput(controller.getServerInfoInput());
		treeViewer.expandAll();

		return container;
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
		return new Point(285, 380);
	}

	@Override
	protected void okPressed() {
		IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Usersession) {
			this.result = (Usersession) firstElement;
		} else {
			return;
		}
		super.okPressed();
	}

	public Usersession getResult() {
		return this.result;
	}

	private final class UsersessionsLabelProvider extends AdapterFactoryLabelProvider {
		public UsersessionsLabelProvider() {
			super(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		}
	}

	private final class UsersessionContentProvider implements ITreeContentProvider {

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Object[]) {
				HashSet<ServerInfo> set = new HashSet<ServerInfo>();
				for (Object obj : (Object[]) inputElement) {
					if (obj instanceof ServerInfo) {
						set.add((ServerInfo) obj);
					}
				}
				return set.toArray();
			}
			return null;
		}

		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof ServerInfo) {
				return controller.getUsersessions((ServerInfo) parentElement);
			}
			return null;
		}

		public Object getParent(Object element) {
			return null;
		}

		public boolean hasChildren(Object element) {
			return element instanceof ServerInfo;
		}
	}

}
