/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.dialogs.login;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.common.ESDisposable;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.ui.Activator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

/**
 * A dialog showing a list of servers from which the user is able to select one.
 * 
 */
public class ServerInfoSelectionDialog extends TitleAreaDialog {

	private final java.util.List<ServerInfo> servers;
	private ESServer result;
	private ListViewer listViewer;
	private ServerInfoLabelProvider labelProvider;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param servers
	 *            a list of servers to be displayed
	 */
	public ServerInfoSelectionDialog(Shell parentShell, java.util.List<ServerInfo> servers) {
		super(parentShell);
		this.servers = servers;
		this.result = null;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Please select a server");
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("In order to execute your requested operation, you have to select a server.");
		setTitle("Please select a Server");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		listViewer = new ListViewer(container, SWT.BORDER | SWT.V_SCROLL);
		List list = listViewer.getList();
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		labelProvider = new ServerInfoLabelProvider();
		listViewer.setLabelProvider(labelProvider);
		listViewer.setInput(servers);
		if (servers.size() == 1) {
			listViewer.setSelection(new StructuredSelection(servers.get(0)));
		}
		return area;
	}

	@Override
	protected void okPressed() {
		ISelection selection = listViewer.getSelection();
		if (selection instanceof IStructuredSelection
			&& ((IStructuredSelection) selection).getFirstElement() instanceof ServerInfo) {
			ServerInfo serverInfo = (ServerInfo) ((IStructuredSelection) selection).getFirstElement();
			result = serverInfo.getAPIImpl();
		}
		super.okPressed();
	}

	/**
	 * Returns the selected server.
	 * 
	 * @return the selected server.
	 */
	public ESServer getResult() {
		return result;
	}

	@Override
	public boolean close() {
		labelProvider.dispose();
		return super.close();
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
		return new Point(355, 403);
	}

	/**
	 * A label provider for {@link ServerInfo} instances.
	 */
	private class ServerInfoLabelProvider extends AdapterFactoryLabelProvider implements ESDisposable {

		public ServerInfoLabelProvider() {
			super(Activator.getAdapterFactory());
		}

		@Override
		public String getText(Object object) {
			if (object instanceof ServerInfo) {
				ServerInfo server = (ServerInfo) object;
				return server.getName() + " [" + server.getUrl() + " : " + server.getPort() + "]";
			}

			return super.getText(object);
		}
	}
}