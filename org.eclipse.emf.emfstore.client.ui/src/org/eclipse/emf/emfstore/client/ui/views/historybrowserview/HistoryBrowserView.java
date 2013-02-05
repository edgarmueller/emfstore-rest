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
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.emfstore.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.client.model.observers.DeleteProjectSpaceObserver;
import org.eclipse.emf.emfstore.client.model.util.ProjectSpaceContainer;
import org.eclipse.emf.emfstore.client.ui.Activator;
import org.eclipse.emf.emfstore.client.ui.dialogs.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.changes.ChangePackageVisualizationHelper;
import org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.provider.ESBrowserLabelProvider;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.graph.IPlotCommit;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.graph.PlotCommitProvider;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.graph.PlotLane;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.graph.SWTPlotRenderer;
import org.eclipse.emf.emfstore.client.ui.views.scm.SCMContentProvider;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.conflictDetection.BasicModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.server.model.versioning.util.HistoryQueryBuilder;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.part.ViewPart;

/**
 * This eclipse views displays the version history of EMFStore.
 * 
 * @author wesendon
 * @author Aumann
 * @author Hodaie
 * @author Shterev
 * 
 */
public class HistoryBrowserView extends ViewPart implements ProjectSpaceContainer {

	// Config
	private static final int UPPER_LIMIT = 10;
	private static final int LOWER_LIMIT = 20;

	// model state
	private ProjectSpace projectSpace;
	private EObject modelElement;

	private List<HistoryInfo> infos;
	private PrimaryVersionSpec centerVersion;
	private boolean showAllVersions;

	// viewer
	private TreeViewerWithModelElementSelectionProvider viewer;
	private SWTPlotRenderer renderer;
	private Link noProjectHint;

	// Columns
	private TreeViewerColumn changeColumn;
	private TreeViewerColumn branchColumn;
	private TreeViewerColumn commitColumn;
	private TreeViewerColumn authorColumn;
	private static final int BRANCH_COLUMN = 1;

	// content/label provider
	private SCMContentProvider contentProvider;
	private PlotCommitProvider commitProvider;
	private AdapterFactoryLabelProvider adapterFactoryLabelProvider;
	private HistorySCMLabelProvider changeLabel;
	private LogMessageColumnLabelProvider commitLabel;

	// actions
	private ExpandCollapseAction expandAndCollapse;
	private boolean isUnlinkedFromNavigator;
	private Action showAllBranches;

	/**
	 * {@inheritDoc}
	 */
	public ProjectSpace getProjectSpace() {
		if (isUnlinkedFromNavigator) {
			return null;
		}
		return this.projectSpace;
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayoutFactory.fillDefaults().applyTo(parent);

		initNoProjectHint(parent);

		// init viewer
		viewer = new TreeViewerWithModelElementSelectionProvider(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());
		Tree tree = viewer.getTree();
		tree.setHeaderVisible(true);
		ColumnViewerToolTipSupport.enableFor(viewer);
		getSite().setSelectionProvider(viewer);

		initMenuManager();

		changeColumn = createColumn("Changes", 250);
		branchColumn = createColumn("Branches", 150);
		commitColumn = createColumn("Commit Message", 250);
		authorColumn = createColumn("Author and Date", 250);

		initContentAndLabelProvider();
		initGraphRenderer();
		initToolBar();
		initProjectDeleteListener();
	}

	private void initContentAndLabelProvider() {
		contentProvider = new SCMContentProvider();
		commitProvider = new PlotCommitProvider();
		viewer.setContentProvider(contentProvider);

		changeLabel = new HistorySCMLabelProvider();
		changeColumn.setLabelProvider(changeLabel);
		branchColumn.setLabelProvider(new BranchGraphLabelProvider());
		commitLabel = new LogMessageColumnLabelProvider();
		commitColumn.setLabelProvider(commitLabel);
		authorColumn.setLabelProvider(new CommitInfoColumnLabelProvider());

		adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
	}

	private void initGraphRenderer() {
		renderer = new SWTPlotRenderer(viewer.getTree().getDisplay());
		viewer.getTree().addListener(SWT.PaintItem, new Listener() {
			public void handleEvent(Event event) {
				doPaint(event);
			}

		});
	}

	private void doPaint(Event event) {
		if (event.index != BRANCH_COLUMN) {
			return;
		}

		Object data;
		TreeItem currItem = (TreeItem) event.item;
		data = currItem.getData();
		boolean isCommitItem = true;

		while (!(data instanceof HistoryInfo)) {
			isCommitItem = false;
			currItem = currItem.getParentItem();
			if (currItem == null) {
				// no history info in parent hierarchy, do not draw.
				// Happens e.g. if the user deactivates showing the commits
				return;
			}
			data = currItem.getData();
		}

		assert data instanceof HistoryInfo : "Would have returned otherwise.";

		final IPlotCommit c = commitProvider.getCommitFor((HistoryInfo) data, !isCommitItem);
		final PlotLane lane = c.getLane();
		if (lane != null && lane.getSaturatedColor().isDisposed()) {
			return;
		}
		// if (highlight != null && c.has(highlight))
		// event.gc.setFont(hFont);
		// else
		event.gc.setFont(PlatformUI.getWorkbench().getDisplay().getSystemFont());

		renderer.paint(event, c);
	}

	private TreeViewerColumn createColumn(String label, int width) {
		TreeViewerColumn column = new TreeViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(label);
		column.getColumn().setWidth(width);
		return column;
	}

	// TODO review this stuff
	private void initMenuManager() {
		MenuManager menuMgr = new MenuManager();
		menuMgr.add(new Separator("additions"));
		getSite().registerContextMenu(menuMgr, viewer);
		Control control = viewer.getControl();
		Menu menu = menuMgr.createContextMenu(control);
		control.setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	/**
	 * Reloads the view with the current parameters.
	 */
	public void refresh() {
		setDescription();
		resetExpandCollapse();
		if (projectSpace == null || modelElement == null) {
			viewer.setInput(Collections.EMPTY_LIST);
			return;
		}
		infos = getHistoryInfos();
		addBaseVersionTag(infos);
		resetProviders(infos);
		viewer.setInput(infos);
	}

	private void addBaseVersionTag(List<HistoryInfo> infos) {
		HistoryInfo historyInfo = getHistoryInfo(projectSpace.getBaseVersion());
		if (historyInfo != null) {
			historyInfo.getTagSpecs().add(Versions.createTAG(VersionSpec.BASE, VersionSpec.GLOBAL));
		}
	}

	private void resetExpandCollapse() {
		expandAndCollapse.setChecked(false);
		expandAndCollapse.setImage(true);
	}

	private void setDescription() {
		if (projectSpace == null) {
			setContentDescription("No element selected.");
			showNoProjectHint(true);
			return;
		}
		String label = "History for ";
		if (modelElement == projectSpace) {
			label += projectSpace.getProjectName() + " [" + projectSpace.getBaseVersion().getBranch() + "]";
		} else {
			label += adapterFactoryLabelProvider.getText(modelElement);
		}
		showNoProjectHint(false);
		setContentDescription(label);
	}

	private List<HistoryInfo> getHistoryInfos() {
		// TODO dudes, this wrapping is crazy!
		List<HistoryInfo> result = new AbstractEMFStoreUIController<List<HistoryInfo>>(getViewSite().getShell(), true,
			false) {
			@Override
			public List<HistoryInfo> doRun(final IProgressMonitor monitor) throws EmfStoreException {
				return new UnknownEMFStoreWorkloadCommand<List<HistoryInfo>>(monitor) {
					@Override
					public List<HistoryInfo> run(final IProgressMonitor monitor) throws EmfStoreException {
						return new ServerCall<List<HistoryInfo>>((ProjectSpaceBase) projectSpace) {
							@Override
							protected List<HistoryInfo> run() throws EmfStoreException {
								monitor.beginTask("Fetching history form server", 100);
								List<HistoryInfo> historyInfos = getLocalChanges();
								monitor.worked(10);
								if (projectSpace != modelElement) {
									historyInfos.addAll(((List<HistoryInfo>) (List<?>) projectSpace
										.getHistoryInfos(HistoryQueryBuilder.modelelementQuery(centerVersion,
											Arrays.asList(ModelUtil.getModelElementId(modelElement)), UPPER_LIMIT,
											LOWER_LIMIT, showAllVersions, true))));
								} else {
									historyInfos.addAll((List<HistoryInfo>) (List<?>) projectSpace
										.getHistoryInfos(HistoryQueryBuilder.rangeQuery(centerVersion, UPPER_LIMIT,
											LOWER_LIMIT, showAllVersions, true, true, true)));
								}
								monitor.worked(90);
								return historyInfos;
							}
						}.execute();
					}
				}.execute();
			}
		}.execute();

		return (result != null) ? result : new ArrayList<HistoryInfo>();
	}

	private List<HistoryInfo> getLocalChanges() {

		ArrayList<HistoryInfo> revisions = new ArrayList<HistoryInfo>();
		if (projectSpace != null) {
			// TODO: add a feature "hide local revision"
			HistoryInfo localHistoryInfo = VersioningFactory.eINSTANCE.createHistoryInfo();
			ChangePackage changePackage = projectSpace.getLocalChangePackage(false);
			// filter for modelelement, do additional sanity check as the
			// project space could've been also selected
			if (modelElement != null && projectSpace.getProject().containsInstance(modelElement)) {
				Set<AbstractOperation> operationsToRemove = new LinkedHashSet<AbstractOperation>();
				for (AbstractOperation ao : changePackage.getOperations()) {
					if (!ao.getAllInvolvedModelElements().contains(
						ModelUtil.getProject(modelElement).getModelElementId(modelElement))) {
						operationsToRemove.add(ao);
					}
				}
				changePackage.getOperations().removeAll(operationsToRemove);
			}
			localHistoryInfo.setChangePackage(changePackage);
			PrimaryVersionSpec versionSpec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
			versionSpec.setIdentifier(-1);
			localHistoryInfo.setPrimerySpec(versionSpec);
			localHistoryInfo.setPreviousSpec(ModelUtil.clone(projectSpace.getBaseVersion()));
			revisions.add(localHistoryInfo);
		}
		return revisions;
	}

	private void resetProviders(List<HistoryInfo> infos) {
		ChangePackageVisualizationHelper oldHelper = changeLabel.getChangePackageVisualizationHelper();
		if (oldHelper != null) {
			oldHelper.dispose();
		}

		ArrayList<ChangePackage> cps = new ArrayList<ChangePackage>();
		for (HistoryInfo info : infos) {
			if (info.getChangePackage() != null) {
				cps.add(info.getChangePackage());
			}
		}

		ChangePackageVisualizationHelper newHelper = new ChangePackageVisualizationHelper(
			new BasicModelElementIdToEObjectMapping(projectSpace.getProject(), cps));
		changeLabel.setProject(projectSpace.getProject());
		changeLabel.setChangePackageVisualizationHelper(newHelper);
		commitLabel.setProject(projectSpace.getProject());
		commitLabel.setChangePackageVisualizationHelper(newHelper);
		commitProvider.reset(infos);
	}

	/**
	 * Displays the history for the given input.
	 * 
	 * @param input eobject in projectspace or projectspace itself
	 */
	public void setInput(EObject input) {
		try {
			if (input instanceof ProjectSpace) {
				this.projectSpace = (ProjectSpace) input;
			} else if (input != null) {
				// TODO OTS
				this.projectSpace = ((Workspace) WorkspaceProvider.getInstance().getWorkspace())
					.getProjectSpace(ModelUtil.getProject(input));
			} else {
				this.projectSpace = null;
			}
			this.modelElement = input;

			showAll(true);
			setCenterVersion();
			refresh();
		} catch (EmfStoreException e) {
		}
	}

	private void showAll(boolean show) {
		showAllVersions = show;
		showAllBranches.setChecked(show);
	}

	private void setCenterVersion() {
		if (this.projectSpace != null) {
			centerVersion = projectSpace.getBaseVersion();
		} else {
			centerVersion = null;
		}
	}

	private void showNoProjectHint(boolean b) {
		noProjectHint.setVisible(b);
		noProjectHint.getParent().layout();
	}

	private void initNoProjectHint(final Composite parent) {
		noProjectHint = new Link(parent, SWT.WRAP);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, false).applyTo(noProjectHint);

		noProjectHint
			.setText("Select a <a>project</a> or call 'Show history' from the context menu of an element in the navigator.");
		noProjectHint.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				ElementListSelectionDialog elsd = new ElementListSelectionDialog(parent.getShell(),
					new ESBrowserLabelProvider());
				List<ProjectSpace> relevantProjectSpaces = new ArrayList<ProjectSpace>();
				// TODO OTS
				for (ProjectSpace ps : ((Workspace) WorkspaceProvider.getInstance().getWorkspace()).getProjectSpaces()) {
					if (ps.getUsersession() != null) {
						relevantProjectSpaces.add(ps);
					}
				}
				elsd.setElements(relevantProjectSpaces.toArray());
				elsd.setMultipleSelection(false);
				elsd.setTitle("Select a project from the workspace");
				elsd.setMessage("Please select a project from the current workspace.");
				if (Dialog.OK == elsd.open()) {
					for (Object o : elsd.getResult()) {
						ProjectSpace resultSelection = (ProjectSpace) o;
						if (resultSelection != null) {
							setInput(resultSelection);
						}
						break;
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				this.widgetSelected(e);
			}
		});
	}

	private void initProjectDeleteListener() {
		WorkspaceProvider.getObserverBus().register(new DeleteProjectSpaceObserver() {
			public void projectSpaceDeleted(ProjectSpace projectSpace) {
				if (HistoryBrowserView.this.projectSpace == projectSpace) {
					setInput(null);
				}
			}
		});
	}

	@Override
	public void dispose() {
		adapterFactoryLabelProvider.dispose();
		super.dispose();
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/**
	 * ====================================================================
	 * 
	 * TOOLBAR.
	 * 
	 * ====================================================================
	 */

	private void initToolBar() {
		IActionBars bars = getViewSite().getActionBars();
		IToolBarManager menuManager = bars.getToolBarManager();

		addRefreshAction(menuManager);
		addShowAllBranchesAction(menuManager);
		addExpandAllAndCollapseAllAction(menuManager);
		addNextAndPreviousAction(menuManager);
		addJumpToRevisionAction(menuManager);
		addLinkWithNavigatorAction(menuManager);
	}

	private void addRefreshAction(IToolBarManager menuManager) {
		Action refresh = new Action() {
			@Override
			public void run() {
				refresh();
			}

		};
		refresh.setImageDescriptor(Activator.getImageDescriptor("/icons/refresh.png"));
		refresh.setToolTipText("Refresh");
		menuManager.add(refresh);
	}

	private void addNextAndPreviousAction(IToolBarManager menuManager) {
		Action prev = new Action() {
			@Override
			public void run() {
				centerVersion = prevNextCenter(false);
				refresh();
			}

		};
		prev.setImageDescriptor(Activator.getImageDescriptor("/icons/prev.png"));
		prev.setToolTipText("Previous items");
		menuManager.add(prev);

		Action next = new Action() {
			@Override
			public void run() {
				centerVersion = prevNextCenter(true);
				refresh();
			}

		};
		next.setImageDescriptor(Activator.getImageDescriptor("/icons/next.png"));
		next.setToolTipText("Next items");
		menuManager.add(next);
	}

	private PrimaryVersionSpec prevNextCenter(boolean next) {
		if (projectSpace == null || centerVersion == null) {
			return null;
		}
		// all versions pages only based on version numbers
		if (showAllVersions) {
			return biggestOrSmallesInfo(next);
		}
		// if center is not on the selected branch (base version of ps) jump to base before paging
		if (!projectSpace.getBaseVersion().getBranch().equals(centerVersion.getBranch())) {
			return projectSpace.getBaseVersion();
		}

		// search next or prev version on given branch
		HistoryInfo current = getHistoryInfo(centerVersion);
		while (current != null) {
			if (next) {
				if (current.getNextSpec().size() > 0) {
					HistoryInfo nextInfo = getHistoryInfo(current.getNextSpec().get(0));
					if (nextInfo == null) {
						return current.getPrimerySpec();
					}
					current = nextInfo;
				} else {
					break;
				}
			} else {
				if (current.getPreviousSpec() != null
					&& current.getPreviousSpec().getBranch().equals(projectSpace.getBaseVersion().getBranch())) {
					HistoryInfo prevInfo = getHistoryInfo(current.getPreviousSpec());
					if (prevInfo == null) {
						return current.getPrimerySpec();
					}
					current = prevInfo;
				} else {
					break;
				}
			}
		}

		if (current == null) {
			return centerVersion;
		}
		return current.getPrimerySpec();
	}

	private HistoryInfo getHistoryInfo(PrimaryVersionSpec version) {
		if (version == null) {
			return null;
		}
		for (HistoryInfo info : infos) {
			if (version.equals(info.getPrimerySpec())) {
				return info;
			}
		}
		return null;
	}

	private PrimaryVersionSpec biggestOrSmallesInfo(boolean biggest) {
		@SuppressWarnings("unchecked")
		List<HistoryInfo> input = (List<HistoryInfo>) viewer.getInput();
		if (input == null) {
			return centerVersion;
		}
		ArrayList<HistoryInfo> resultCandidates = new ArrayList<HistoryInfo>(input);
		PrimaryVersionSpec result = centerVersion;
		for (HistoryInfo info : resultCandidates) {
			if (info.getPrimerySpec().getIdentifier() != -1
				&& ((biggest && info.getPrimerySpec().compareTo(result) == 1) || (!biggest && info.getPrimerySpec()
					.compareTo(result) == -1))) {
				result = info.getPrimerySpec();
			}
		}
		return result;
	}

	private void addLinkWithNavigatorAction(IToolBarManager menuManager) {
		isUnlinkedFromNavigator = Activator.getDefault().getDialogSettings().getBoolean("LinkWithNavigator");
		Action linkWithNavigator = new Action("Link with navigator", SWT.TOGGLE) {

			@Override
			public void run() {
				Activator.getDefault().getDialogSettings().put("LinkWithNavigator", !this.isChecked());
				isUnlinkedFromNavigator = (!this.isChecked());
			}

		};
		linkWithNavigator.setImageDescriptor(Activator.getImageDescriptor("icons/link_with_editor.gif"));
		linkWithNavigator.setToolTipText("Link with Navigator");
		linkWithNavigator.setChecked(!isUnlinkedFromNavigator);
		menuManager.add(linkWithNavigator);
	}

	private void addShowAllBranchesAction(IToolBarManager menuManager) {
		showAllBranches = new Action("", SWT.TOGGLE) {
			@Override
			public void run() {
				showAllVersions = isChecked();
				refresh();
			}

		};
		showAllBranches.setImageDescriptor(Activator.getImageDescriptor("icons/arrow_branch.png"));
		showAllBranches.setToolTipText("Show All Branches");
		showAllBranches.setChecked(true);
		menuManager.add(showAllBranches);
	}

	private void addJumpToRevisionAction(IToolBarManager menuManager) {
		Action jumpTo = new Action() {
			@Override
			public void run() {
				final InputDialog inputDialog = new InputDialog(getSite().getShell(), "Go to revision", "Revision", "",
					null);
				if (inputDialog.open() == Window.OK) {
					if (projectSpace != null) {
						PrimaryVersionSpec versionSpec = resolveVersion(inputDialog.getValue());
						if (versionSpec != null) {
							showAll(true);
							centerVersion = versionSpec;
							refresh();
						}
					}
				}
			}

		};
		jumpTo.setImageDescriptor(Activator.getImageDescriptor("/icons/magnifier.png"));
		jumpTo.setToolTipText("Go to revision...");
		menuManager.add(jumpTo);
	}

	private PrimaryVersionSpec resolveVersion(final String value) {
		return new AbstractEMFStoreUIController<PrimaryVersionSpec>(getViewSite().getShell()) {
			@Override
			public PrimaryVersionSpec doRun(IProgressMonitor monitor) throws EmfStoreException {
				return new UnknownEMFStoreWorkloadCommand<PrimaryVersionSpec>(monitor) {
					@Override
					public PrimaryVersionSpec run(IProgressMonitor monitor) throws EmfStoreException {
						try {
							return (PrimaryVersionSpec) projectSpace.resolveVersionSpec(Versions.createPRIMARY(
								VersionSpec.GLOBAL, Integer.parseInt(value)));
						} catch (EmfStoreException e) {
							EMFStoreMessageDialog.showExceptionDialog(
								"Error: The version you requested does not exist.", e);
						} catch (NumberFormatException e) {
							MessageDialog.openError(getSite().getShell(), "Error", "A numeric value was expected!");
						}
						return null;
					}
				}.execute();
			}
		}.execute();
	}

	private void addExpandAllAndCollapseAllAction(IToolBarManager menuManager) {
		final ImageDescriptor expandImg = Activator.getImageDescriptor("icons/expandall.gif");
		final ImageDescriptor collapseImg = Activator.getImageDescriptor("icons/collapseall.gif");

		expandAndCollapse = new ExpandCollapseAction("", SWT.TOGGLE, expandImg, collapseImg);
		expandAndCollapse.setImageDescriptor(expandImg);
		expandAndCollapse.setToolTipText("Use this toggle to expand or collapse all elements");
		menuManager.add(expandAndCollapse);
	}

	/**
	 * Expand/Collapse action.
	 * 
	 * @author wesendon
	 */
	private final class ExpandCollapseAction extends Action {
		private final ImageDescriptor expandImg;
		private final ImageDescriptor collapseImg;

		private ExpandCollapseAction(String text, int style, ImageDescriptor expandImg, ImageDescriptor collapseImg) {
			super(text, style);
			this.expandImg = expandImg;
			this.collapseImg = collapseImg;
		}

		@Override
		public void run() {
			if (!isChecked()) {
				setImage(true);
				viewer.collapseAll();
			} else {
				setImage(false);
				viewer.expandToLevel(2);
			}
		}

		public void setImage(boolean expand) {
			setImageDescriptor(expand ? expandImg : collapseImg);
		}
	}

	/**
	 * Treeviewer that provides a model element selection for selected
	 * operations and mode element ids.
	 * 
	 * @author koegel
	 */
	private final class TreeViewerWithModelElementSelectionProvider extends TreeViewer {
		private TreeViewerWithModelElementSelectionProvider(Composite parent) {
			super(parent, SWT.NONE);
		}

		@Override
		protected Widget internalExpand(Object elementOrPath, boolean expand) {
			// TODO Auto-generated method stub
			return super.internalExpand(elementOrPath, expand);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.AbstractTreeViewer#getSelection()
		 */
		@Override
		public ISelection getSelection() {
			Control control = getControl();

			if (control == null || control.isDisposed()) {
				return super.getSelection();
			}

			Widget[] items = getSelection(getControl());
			if (items.length != 1) {
				return super.getSelection();
			}

			Widget item = items[0];
			Object data = item.getData();
			if (data == null) {
				return super.getSelection();
			}

			// TODO: remove assignment
			Object element = data;
			EObject selectedModelElement = null;

			if (element instanceof CompositeOperation) {
				selectedModelElement = handleCompositeOperation((CompositeOperation) element);
			} else if (element instanceof AbstractOperation) {
				selectedModelElement = handleAbstractOperation((AbstractOperation) element);
			} else if (element instanceof ProjectSpace) {
				selectedModelElement = ((ProjectSpace) element).getProject();
			} else if (element instanceof ModelElementId
				&& projectSpace.getProject().contains((ModelElementId) element)) {
				selectedModelElement = projectSpace.getProject().getModelElement((ModelElementId) element);
			} else if (projectSpace.getProject().containsInstance((EObject) element)) {
				selectedModelElement = (EObject) element;
			}

			if (selectedModelElement != null) {
				return new StructuredSelection(selectedModelElement);
			}

			return super.getSelection();
		}

		private EObject handleCompositeOperation(CompositeOperation op) {
			AbstractOperation mainOperation = op.getMainOperation();
			if (mainOperation != null) {
				ModelElementId modelElementId = mainOperation.getModelElementId();
				EObject modelElement = projectSpace.getProject().getModelElement(modelElementId);
				return modelElement;
			}

			return null;
		}

		private EObject handleAbstractOperation(AbstractOperation op) {
			ModelElementId modelElementId = op.getModelElementId();
			EObject modelElement = projectSpace.getProject().getModelElement(modelElementId);
			return modelElement;
		}
	}

}
