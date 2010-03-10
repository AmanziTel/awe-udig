/* AWE - Amanzi Wireless Explorer
 * http://awe.amanzi.org
 * (C) 2008-2009, AmanziTel AB
 *
 * This library is provided under the terms of the Eclipse Public License
 * as described at http://www.eclipse.org/legal/epl-v10.html. Any use,
 * reproduction or distribution of the library constitutes recipient's
 * acceptance of this agreement.
 *
 * This library is distributed WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.amanzi.awe.filters.views;

import java.util.LinkedHashMap;

import org.amanzi.awe.filters.AbstractFilter;
import org.amanzi.awe.filters.FilterUtil;
import org.amanzi.awe.filters.tree.TreeNeoNode;
import org.amanzi.neo.core.enums.GeoNeoRelationshipTypes;
import org.amanzi.neo.core.enums.NodeTypes;
import org.amanzi.neo.core.service.NeoServiceProvider;
import org.amanzi.neo.core.utils.ActionUtil;
import org.amanzi.neo.core.utils.NeoUtils;
import org.amanzi.neo.core.utils.NodeDeletingManager;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.neo4j.api.core.Direction;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Relationship;
import org.neo4j.api.core.ReturnableEvaluator;
import org.neo4j.api.core.StopEvaluator;
import org.neo4j.api.core.Transaction;
import org.neo4j.api.core.TraversalPosition;
import org.neo4j.api.core.Traverser;
import org.neo4j.api.core.Traverser.Order;

/**
 * <p>
 * view for handle filters
 * </p>
 * 
 * @author Tsinkel_A
 * @since 1.0.0
 */
public class FilterView extends ViewPart {

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "org.amanzi.awe.filters.views.FilterView";

    private TreeViewer viewer;
    private DrillDownAdapter drillDownAdapter;
    private AddGroupAction addGroupAction;
    private AddFilterAction addFilterAction;
    private final NeoService service;

    private Node rootFlterNode;

    private Composite groupFrame;

    private Group filterFrame;

    private Text tGroupName;

    private Text cProperty;

    private Button bGroupOk;

    private Button bGroupCancel;

    private Text tProperty;

    private Button bFilterOk;

    private Button bFilterCancel;

    private Combo cFirst;

    private Text cFirstText;

    private Combo cSecRel;

    private Combo cSecond;

    private Text cSecondText;

    private TreeNeoNode rootTree;

    private DeleteAction deleteAction;

    private Combo cGis;

    private Text tGisFilter;

    private Button bClearFilter;

    private final LinkedHashMap<String, Node> dataMap;

    private ClearFilter clearFiltr;

    private AssignFilter assignFiltr;

    class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {
        private Object invisibleRoot;
        private TreeNeoNode rootNode;

        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
            Transaction tx = NeoUtils.beginTx(service);
            try {
                if (newInput instanceof TreeNeoNode) {
                    invisibleRoot = newInput;
                    rootNode = (TreeNeoNode)newInput;
                } else if (newInput instanceof Node) {
                    rootNode = new TreeNeoNode(getFileRootNode());
                    invisibleRoot = service.getReferenceNode();
                } else {
                    invisibleRoot = null;
                    rootNode = null;
                }
            } finally {
                tx.finish();
            }
        }

        public void dispose() {
        }

        public Object[] getElements(Object parent) {
            if (invisibleRoot == null) {
                return null;
            } else if (invisibleRoot instanceof Node) {
                return new Object[] {rootNode};
            } else {
                return getChildren(parent);
            }
        }

        public Object getParent(Object child) {
            if (child instanceof TreeNeoNode) {
                return ((TreeNeoNode)child).getParent(service);
            }
            return null;
        }

        public Object[] getChildren(Object parent) {
            if (parent instanceof TreeNeoNode) {
                return ((TreeNeoNode)parent).getChildren(service);
            }
            return new Object[0];
        }

        public boolean hasChildren(Object parent) {
            if (parent instanceof TreeNeoNode)
                return ((TreeNeoNode)parent).hasChildren(service);
            return false;
        }

    }

    class ViewLabelProvider extends LabelProvider {

        @Override
        public String getText(Object obj) {
            return obj.toString();
        }

        @Override
        public Image getImage(Object obj) {
            String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
            if (obj instanceof TreeNeoNode && ((TreeNeoNode)obj).getType() != NodeTypes.FILTER) {
                imageKey = ISharedImages.IMG_OBJ_FOLDER;
            }
            return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
        }
    }

    class NameSorter extends ViewerSorter {
    }

    /**
     * The constructor.
     */
    public FilterView() {
        service = NeoServiceProvider.getProvider().getService();
        dataMap=new LinkedHashMap<String, Node>();
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize it.
     */
    @Override
    public void createPartControl(Composite parent) {
        FormLayout layout = new FormLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.spacing = 0;
        parent.setLayout(layout);
        Composite row=new Composite(parent, SWT.FILL);
        FormData layoutData = new FormData();
        layoutData.left = new FormAttachment(0, 2);
        layoutData.right = new FormAttachment(50, -2);
        layoutData.top = new FormAttachment(0, 2);
        row.setLayoutData(layoutData);
        row.setLayout(new GridLayout(5, false));
        //firs row
        Label label=new Label(row, SWT.NONE);
        label.setText("GIS nodes:");
         cGis = new Combo(row,SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
        cGis.setLayoutData(new GridData(100, SWT.DEFAULT));
         updateGisItems();
        label=new Label(row, SWT.NONE);
        label.setText("Filter:");
        tGisFilter=new Text(row,SWT.READ_ONLY);
        tGisFilter.setLayoutData(new GridData(100, SWT.DEFAULT));
        viewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
         layoutData = new FormData();
        layoutData.left = new FormAttachment(0, 2);
        layoutData.right = new FormAttachment(50, -2);
        layoutData.top = new FormAttachment(row, 2);
        layoutData.bottom = new FormAttachment(100, -2);
        viewer.getControl().setLayoutData(layoutData);
        groupFrame = new Composite(parent, SWT.BORDER);
        layoutData = new FormData();
        layoutData.left = new FormAttachment(50, 2);
        layoutData.right = new FormAttachment(100, -2);
        layoutData.top = new FormAttachment(0, 2);
        layoutData.bottom = new FormAttachment(100, -2);
        groupFrame.setLayoutData(layoutData);
        groupFrame.setLayout(new GridLayout(2, false));

        filterFrame = new Group(parent, SWT.NONE);
        filterFrame.setLayout(new GridLayout(3, false));
        layoutData = new FormData();
        layoutData.left = new FormAttachment(50, 2);
        layoutData.right = new FormAttachment(100, -2);
        layoutData.top = new FormAttachment(0, 2);
        layoutData.bottom = new FormAttachment(100, -2);
        filterFrame.setLayoutData(layoutData);
        drillDownAdapter = new DrillDownAdapter(viewer);
        viewer.setContentProvider(new ViewContentProvider());
        viewer.setLabelProvider(new ViewLabelProvider());
        viewer.setSorter(new NameSorter());
        viewer.setInput(service.getReferenceNode());
        // group frame
         label = new Label(groupFrame, SWT.NONE);
        label.setText("Group name:");
        tGroupName = new Text(groupFrame, SWT.BORDER);
        tGroupName.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        label = new Label(groupFrame, SWT.NONE);
        label.setText("Property:");
        cProperty = new Text(groupFrame, SWT.BORDER);
        cProperty.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        Composite rowFrame = new Composite(groupFrame, SWT.FILL);
        rowFrame.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
        rowFrame.setLayout(new GridLayout(2, true));
        bGroupOk = new Button(rowFrame, SWT.PUSH | SWT.CENTER);
        bGroupOk.setText("Ok");
        bGroupOk.setLayoutData(new GridData(100, SWT.DEFAULT));
        bGroupCancel = new Button(rowFrame, SWT.PUSH | SWT.CENTER);
        bGroupCancel.setText("Cancel");
        bGroupCancel.setLayoutData(new GridData(100, SWT.DEFAULT));

        // filter frame
        label = new Label(filterFrame, SWT.NONE);
        label.setText("Property:");
        tProperty = new Text(filterFrame, SWT.BORDER);
        tProperty.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));
        label = new Label(filterFrame, SWT.NONE);
        label.setText("Filter:");
        cFirst = new Combo(filterFrame, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
        cFirst.setItems(getFilters());
        cFirstText = new Text(filterFrame, SWT.BORDER);
        cFirstText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        cSecRel = new Combo(filterFrame, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
        cSecRel.setItems(FilterUtil.getFilterRel());
        cSecond = new Combo(filterFrame, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
        cSecond.setItems(getFilters());
        cSecondText = new Text(filterFrame, SWT.BORDER);
        cSecondText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        rowFrame = new Composite(filterFrame, SWT.FILL);
        rowFrame.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 3, 1));
        rowFrame.setLayout(new GridLayout(2, true));
        bFilterOk = new Button(rowFrame, SWT.PUSH | SWT.CENTER);
        bFilterOk.setText("Ok");
        bFilterOk.setLayoutData(new GridData(100, SWT.DEFAULT));
        bFilterCancel = new Button(rowFrame, SWT.PUSH | SWT.CENTER);
        bFilterCancel.setText("Cancel");
        bFilterCancel.setLayoutData(new GridData(100, SWT.DEFAULT));

        groupFrame.setVisible(false);
        filterFrame.setVisible(false);
        addListeners();
        makeActions();
        hookContextMenu();
        hookDoubleClickAction();
        contributeToActionBars();
    }

    /**
     *
     */
    public void updateGisItems() {
        dataMap.clear();
        Transaction tx = NeoUtils.beginTx(service);
        try{
            Traverser traverse = service.getReferenceNode().traverse(Order.DEPTH_FIRST, StopEvaluator.DEPTH_ONE, new ReturnableEvaluator() {
                
                @Override
                public boolean isReturnableNode(TraversalPosition currentPos) {
                    return NeoUtils.isGisNode(currentPos.currentNode());
                }
            } ,GeoNeoRelationshipTypes.CHILD,Direction.OUTGOING);
            for (Node node : traverse) {
                dataMap.put(NeoUtils.getNodeName(node), node);
            }
            cGis.setItems(dataMap.keySet().toArray(new String[0]));
        }finally{
            tx.finish();
        }
    }

    /**
     * @return
     */
    private String[] getFilters() {
        return FilterUtil.getFilterDes();
    }

    /**
     *
     */
    private void addListeners() {
        viewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                updateActions();
                groupFrame.setVisible(false);
                filterFrame.setVisible(false);
                IStructuredSelection selection = (IStructuredSelection)event.getSelection();
                if (selection.size() != 1) {
                    return;
                }
                TreeNeoNode element = (TreeNeoNode)selection.getFirstElement();
                switch (element.getType()) {
                case FILTER_GROUP:
                    showGroupFilter(element);
                    break;
                case FILTER:
                    showFilter(element);
                default:
                    break;
                }
            }
        });
        tGroupName.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
            }

            @Override
            public void focusGained(FocusEvent e) {
                viewer.getControl().setEnabled(false);
            }
        });
        cProperty.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
            }

            @Override
            public void focusGained(FocusEvent e) {
                viewer.getControl().setEnabled(false);
            }
        });
        tGroupName.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
            }
        });
        cProperty.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
            }
        });
        bGroupOk.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                saveGroup();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });
        bFilterOk.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                saveFilter();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });
        bFilterCancel.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                showFilter(rootTree);
                viewer.getControl().setEnabled(true);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });
        bGroupCancel.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                showGroupFilter(rootTree);
                viewer.getControl().setEnabled(true);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });
        final FocusListener listener = new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
            }

            @Override
            public void focusGained(FocusEvent e) {
                viewer.getControl().setEnabled(false);
            }
        };
        tProperty.addFocusListener(listener);
        cFirst.addFocusListener(listener);
        cFirstText.addFocusListener(listener);
        cSecond.addFocusListener(listener);
        cSecondText.addFocusListener(listener);
        cSecRel.addFocusListener(listener);
        cGis.addSelectionListener(new SelectionListener() {
            
            @Override
            public void widgetSelected(SelectionEvent e) {
                updateGisFilter();
            }
            
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });
    }

    /**
     *
     */
    protected void saveFilter() {
        final String property = tProperty.getText();
        final String first = cFirst.getText();
        final String firsttxt = cFirstText.getText();
        final String secondrel = cSecRel.getText();
        final String second = cSecond.getText();
        final String secontxt = cSecondText.getText();
        Job job = new Job("save filter") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                Transaction tx = NeoUtils.beginTx(service);
                try {

                    Node node = rootTree.getNode();
                    NeoUtils.setNodeName(node, property, service);
                    FilterUtil.setGroupProperty(node, property, service);
                    node.setProperty(FilterUtil.PROPERTY_FIRST, first);
                    node.setProperty(FilterUtil.PROPERTY_FIRST_TXT, firsttxt);
                    node.setProperty(FilterUtil.PROPERTY_SECOND_REL, secondrel);
                    node.setProperty(FilterUtil.PROPERTY_SECOND, second);
                    node.setProperty(FilterUtil.PROPERTY_SECOND_TXT, secontxt);
                    NeoUtils.successTx(tx);
                } finally {
                    NeoUtils.finishTx(tx);
                }
                rootTree.refresh();
                ActionUtil.getInstance().runTask(new Runnable() {

                    @Override
                    public void run() {
                        viewer.refresh(rootTree);
                    }
                }, true);
                return Status.OK_STATUS;
            }

        };
        job.schedule();
        viewer.getControl().setEnabled(true);
    }

    /**
     *
     */
    protected void saveGroup() {
        final String name = tGroupName.getText();
        final String property = cProperty.getText();
        Job job = new Job("save group") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                Transaction tx = NeoUtils.beginTx(service);
                try {

                    Node node = rootTree.getNode();
                    NeoUtils.setNodeName(node, name, service);
                    FilterUtil.setGroupProperty(node, property, service);
                    NeoUtils.successTx(tx);
                } finally {
                    NeoUtils.finishTx(tx);
                }
                rootTree.refresh();
                ActionUtil.getInstance().runTask(new Runnable() {

                    @Override
                    public void run() {
                        viewer.refresh(rootTree);
                    }
                }, true);
                return Status.OK_STATUS;
            }

        };
        job.schedule();
        viewer.getControl().setEnabled(true);
    }

    /**
     * @param element
     */
    protected void showFilter(TreeNeoNode element) {
        rootTree = element;
        Transaction tx = NeoUtils.beginTx(service);
        try {
            tProperty.setText(FilterUtil.getGroupProperty(element.getNode(), "", service));
            cFirst.setText((String)element.getNode().getProperty(FilterUtil.PROPERTY_FIRST, ""));
            cFirstText.setText((String)element.getNode().getProperty(FilterUtil.PROPERTY_FIRST_TXT, ""));
            cSecRel.setText((String)element.getNode().getProperty(FilterUtil.PROPERTY_SECOND_REL, ""));
            cSecond.setText((String)element.getNode().getProperty(FilterUtil.PROPERTY_SECOND, ""));
            cSecondText.setText((String)element.getNode().getProperty(FilterUtil.PROPERTY_SECOND_TXT, ""));
        } finally {
            NeoUtils.finishTx(tx);
        }
        groupFrame.setVisible(false);
        filterFrame.setVisible(true);
    }

    /**
     * @param element
     */
    protected void showGroupFilter(TreeNeoNode element) {
        rootTree = element;
        Transaction tx = NeoUtils.beginTx(service);
        try {
            tGroupName.setText(NeoUtils.getSimpleNodeName(rootTree.getNode(), ""));
            cProperty.setText(FilterUtil.getGroupProperty(rootTree.getNode(), "", service));
        } finally {
            NeoUtils.finishTx(tx);
        }
        groupFrame.setVisible(true);
    }

    /**
     * @return
     */
    private Node getFileRootNode() {
        Job job = new Job("find root node") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                rootFlterNode = NeoUtils.findOrCreateFilterRootNode(service);
                return Status.OK_STATUS;
            }
        };
        job.schedule();
        try {
            job.join();
        } catch (InterruptedException e) {
            // TODO Handle InterruptedException
            throw (RuntimeException)new RuntimeException().initCause(e);
        }
        return rootFlterNode;
    }

    private void hookContextMenu() {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {
            public void menuAboutToShow(IMenuManager manager) {
                FilterView.this.fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);
    }

    private void contributeToActionBars() {
        IActionBars bars = getViewSite().getActionBars();
        fillLocalPullDown(bars.getMenuManager());
        fillLocalToolBar(bars.getToolBarManager());
    }

    private void fillLocalPullDown(IMenuManager manager) {
        manager.add(addGroupAction);
        manager.add(new Separator());
        manager.add(addFilterAction);
        manager.add(new Separator());
        manager.add(deleteAction);
    }

    private void fillContextMenu(IMenuManager manager) {
        updateActions();
        if (addGroupAction.isEnabled()) {
            manager.add(addGroupAction);
        }
        if (addFilterAction.isEnabled()) {
            manager.add(addFilterAction);
        }
        manager.add(new Separator());
        if (assignFiltr.isEnabled()) {
            manager.add(assignFiltr);
        }
        manager.add(new Separator());
        if (deleteAction.isEnabled()) {
            manager.add(deleteAction);
        }
        manager.add(new Separator());
        drillDownAdapter.addNavigationActions(manager);
        // Other plug-ins can contribute there actions here
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    }

    /**
     *
     */
    private void updateActions() {
        addGroupAction.setEnabled(addGroupAction.isAcionEnable());
        addFilterAction.setEnabled(addFilterAction.isAcionEnable());
        deleteAction.setEnabled(deleteAction.isAcionEnable());
        clearFiltr.setEnabled(clearFiltr.isAcionEnable());
        assignFiltr.setEnabled(assignFiltr.isAcionEnable());
    }

    private void fillLocalToolBar(IToolBarManager manager) {
        manager.add(addGroupAction);
        manager.add(addFilterAction);
        manager.add(new Separator());
        manager.add(assignFiltr);
        manager.add(clearFiltr);
        manager.add(new Separator());
        manager.add(deleteAction);
        manager.add(new Separator());
        drillDownAdapter.addNavigationActions(manager);
    }

    private void makeActions() {
        addGroupAction = new AddGroupAction();
        addFilterAction = new AddFilterAction();
        deleteAction = new DeleteAction();
        clearFiltr = new ClearFilter();
        assignFiltr = new AssignFilter();
    }

    /**
     * @param node
     * @return
     */
    protected void createAndSelectNewGroup(final TreeNeoNode nodeTree) {
        Job job = new Job("create group") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                Transaction tx = NeoUtils.beginTx(service);
                final Node node;
                try {
                    node = service.createNode();
                    NodeTypes.FILTER_GROUP.setNodeType(node, service);
                    NeoUtils.setNodeName(node, "New Group", service);
                    NeoUtils.addChild(nodeTree.getNode(), node, null, service);
                    NeoUtils.successTx(tx);
                } finally {
                    NeoUtils.finishTx(tx);
                }
                ActionUtil.getInstance().runTask(new Runnable() {

                    @Override
                    public void run() {
                        viewer.refresh(nodeTree);
                        viewer.setSelection(new StructuredSelection(new Object[] {new TreeNeoNode(node)}), true);
                    }
                }, true);
                return Status.OK_STATUS;
            }

        };
        job.schedule();
    }

    private void hookDoubleClickAction() {
        viewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
            }
        });
    }

    private void showMessage(String message) {
        MessageDialog.openInformation(viewer.getControl().getShell(), "Filters", message);
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    @Override
    public void setFocus() {
        viewer.getControl().setFocus();
    }

    public class AddFilterAction extends Action {
        private TreeNeoNode node;

        /**
     * 
     */
        public AddFilterAction() {
            setText("Add Filter");
            setToolTipText("Add new filter");
            setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

        }

        @Override
        public void run() {
            if (isAcionEnable()) {
                createAndSelectNewFilter(node);
            } else {
                this.setEnabled(false);
            }
        }

        public boolean isAcionEnable() {
            IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
            if (selection.size() != 1) {
                return false;
            }
            Object element = (selection).getFirstElement();
            if (!(element instanceof TreeNeoNode)) {
                return false;
            }
            node = (TreeNeoNode)element;
            return node.getType() != NodeTypes.FILTER;
        }

    }

    public class DeleteAction extends Action {
        /**
         * 
         */
        public DeleteAction() {
            setText("Delete");
            setToolTipText("Delete filter");
            setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

        }

        private TreeNeoNode node;

        @Override
        public void run() {
            if (isAcionEnable()) {
                deleteNode(node);
            } else {
                this.setEnabled(false);
            }
        }

        public boolean isAcionEnable() {
            IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
            if (selection.size() != 1) {
                return false;
            }
            Object element = (selection).getFirstElement();
            if (!(element instanceof TreeNeoNode)) {
                return false;
            }
            node = (TreeNeoNode)element;
            return node.getType() != NodeTypes.FILTER_ROOT;
        }

    }

    public class AssignFilter extends Action {
        /**
         * 
         */
        public AssignFilter() {
            setText("Assign filter");
            setToolTipText("Add filter to selected data");
            setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
            
        }
        
        private Node dataNode;
        private Node filterNode;
        
        @Override
        public void run() {
            if (isAcionEnable()) {
                setFilter(dataNode,filterNode);
            } else {
                this.setEnabled(false);
            }
        }
        
        public boolean isAcionEnable() {
            dataNode=dataMap.get(cGis.getText());
            if (dataNode==null){
                return false;
            }
            IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
            if (selection.size() != 1) {
                return false;
            }
            Object element = (selection).getFirstElement();
            if (!(element instanceof TreeNeoNode)) {
                return false;
            }
            TreeNeoNode node = (TreeNeoNode)element;
            filterNode=node.getNode();
            return node.getType() != NodeTypes.FILTER_ROOT;
        }
        
    }
    public class ClearFilter extends Action {
        /**
         * 
         */
        public ClearFilter() {
            setText("Clear filter");
            setToolTipText("Clear filter");
            setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
            
        }
        
        private Node dataNode;
        
        @Override
        public void run() {
            if (isAcionEnable()) {
                clearFilter(dataNode);
            } else {
                this.setEnabled(false);
            }
        }
        
        public boolean isAcionEnable() {
            dataNode=dataMap.get(cGis.getText());
            if (dataNode==null){
                return false;
            }
            Transaction tx = NeoUtils.beginTx(service);
            try{
                return dataNode.hasRelationship(GeoNeoRelationshipTypes.USE_FILTER,Direction.OUTGOING);
            }finally{
                tx.finish();
            }
        }
        
    }
    public class AddGroupAction extends Action {
        /**
         * 
         */
        public AddGroupAction() {
            setText("Add Group");
            setToolTipText("Add new group");
            setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

        }

        private TreeNeoNode node;

        @Override
        public void run() {
            if (isAcionEnable()) {
                createAndSelectNewGroup(node);
            } else {
                this.setEnabled(false);
            }
        }

        public boolean isAcionEnable() {
            IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
            if (selection.size() != 1) {
                return false;
            }
            Object element = (selection).getFirstElement();
            if (!(element instanceof TreeNeoNode)) {
                return false;
            }
            node = (TreeNeoNode)element;
            return node.getType() == NodeTypes.FILTER_ROOT;
        }

    }

    /**
     * @param node
     */
    public void createAndSelectNewFilter(final TreeNeoNode nodeTree) {
        Job job = new Job("create filter") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                Transaction tx = NeoUtils.beginTx(service);
                final Node node;
                try {
                    node = service.createNode();
                    NodeTypes.FILTER.setNodeType(node, service);
                    NeoUtils.setNodeName(node, FilterUtil.getGroupProperty(nodeTree.getNode(), "", service), service);
                    node.setProperty(FilterUtil.PROPERTY_FILTERED_NAME, FilterUtil.getGroupProperty(nodeTree.getNode(), "", service));
                    NeoUtils.addChild(nodeTree.getNode(), node, null, service);
                    FilterUtil.setFilterValid(node, false, service);
                    NeoUtils.successTx(tx);
                } finally {
                    NeoUtils.finishTx(tx);
                }
                ActionUtil.getInstance().runTask(new Runnable() {

                    @Override
                    public void run() {
                        viewer.refresh(nodeTree);
                        viewer.setSelection(new StructuredSelection(new Object[] {new TreeNeoNode(node)}), true);
                    }
                }, true);
                return Status.OK_STATUS;
            }

        };
        job.schedule();
    }

    /**
     *
     * @param dataNode
     * @param filterNode
     */
    public void setFilter(final Node dataNode, final Node filterNode) {
        Job job=new Job("remove filter") {
            
            @Override
            protected IStatus run(IProgressMonitor monitor) {
                Transaction tx = NeoUtils.beginTx(service);
                try{
                    for (Relationship relation:dataNode.getRelationships(GeoNeoRelationshipTypes.USE_FILTER,Direction.OUTGOING)){
                        relation.delete();
                    }
                    dataNode.createRelationshipTo(filterNode, GeoNeoRelationshipTypes.USE_FILTER);
                    tx.success();
                }finally{
                    tx.finish();
                }
                return Status.OK_STATUS;
            }
        };
        job.schedule();
        try {
            job.join();
        } catch (InterruptedException e) {
            // TODO Handle InterruptedException
            throw (RuntimeException) new RuntimeException( ).initCause( e );
        }
        updateGisFilter();
    }

    /**
     *
     * @param dataNode
     */
    public void clearFilter(final Node dataNode) {
        Job job=new Job("remove filter") {
            
            @Override
            protected IStatus run(IProgressMonitor monitor) {
                Transaction tx = NeoUtils.beginTx(service);
                try{
                    for (Relationship relation:dataNode.getRelationships(GeoNeoRelationshipTypes.USE_FILTER,Direction.OUTGOING)){
                        relation.delete();
                    }
                    tx.success();
                }finally{
                    tx.finish();
                }
                return Status.OK_STATUS;
            }
        };
        job.schedule();
        try {
            job.join();
        } catch (InterruptedException e) {
            // TODO Handle InterruptedException
            throw (RuntimeException) new RuntimeException( ).initCause( e );
        }
        updateGisFilter();
    }

    /**
     *
     */
    private void updateGisFilter() {
        Node data=dataMap.get(cGis.getText());
        String result;
        if (data==null){
            result="";
        }else{
            Transaction tx = NeoUtils.beginTx(service);
            try{
                Relationship relation = data.getSingleRelationship(GeoNeoRelationshipTypes.USE_FILTER,Direction.OUTGOING);
                if (relation==null){
                    result="";
                }else{
                    result=AbstractFilter.getInstance(relation.getOtherNode(data), service).toString();
                }
            }finally{
                tx.finish();
            }
        }
        updateActions();
        tGisFilter.setText(result);
    }

    /**
     * @param node
     */
    public void deleteNode(final TreeNeoNode node) {
        viewer.getControl().setEnabled(false);
        Job job=new Job("delete node"){

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                try{
                NodeDeletingManager manager=new NodeDeletingManager(service);
                manager.deleteNode(node.getNode());
                } catch (IllegalAccessException e) {
                    // TODO Handle IllegalAccessException
                    throw (RuntimeException) new RuntimeException( ).initCause( e );
                }finally{
                ActionUtil.getInstance().runTask(new Runnable() {

                    @Override
                    public void run() {
                        viewer.refresh();
                        viewer.getControl().setEnabled(true );
                    }
                }, true);
                }
                return Status.OK_STATUS;
            }
        };
        job.schedule();
        
        
    }
}