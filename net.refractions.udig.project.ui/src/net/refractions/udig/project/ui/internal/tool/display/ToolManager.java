/*
 * uDig - User Friendly Desktop Internet GIS client http://udig.refractions.net (C) 2004,
 * Refractions Research Inc. This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; version 2.1 of the License. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package net.refractions.udig.project.ui.internal.tool.display;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.refractions.udig.catalog.IGeoResource;
import net.refractions.udig.core.internal.ExtensionPointList;
import net.refractions.udig.internal.ui.UDIGDNDProcessor;
import net.refractions.udig.internal.ui.UDIGDropHandler;
import net.refractions.udig.internal.ui.UDigByteAndLocalTransfer;
import net.refractions.udig.internal.ui.UiPlugin;
import net.refractions.udig.internal.ui.operations.OperationCategory;
import net.refractions.udig.internal.ui.operations.OperationMenuFactory;
import net.refractions.udig.internal.ui.operations.RunOperationsAction;
import net.refractions.udig.project.EditManagerEvent;
import net.refractions.udig.project.IEditManagerListener;
import net.refractions.udig.project.ILayer;
import net.refractions.udig.project.IMap;
import net.refractions.udig.project.internal.Map;
import net.refractions.udig.project.internal.ProjectFactory;
import net.refractions.udig.project.internal.ProjectPackage;
import net.refractions.udig.project.internal.commands.CreateMapCommand;
import net.refractions.udig.project.ui.ApplicationGIS;
import net.refractions.udig.project.ui.internal.AdaptingFilter;
import net.refractions.udig.project.ui.internal.ApplicationGISInternal;
import net.refractions.udig.project.ui.internal.MapEditor;
import net.refractions.udig.project.ui.internal.MapPart;
import net.refractions.udig.project.ui.internal.Messages;
import net.refractions.udig.project.ui.internal.ProjectUIPlugin;
import net.refractions.udig.project.ui.internal.actions.Delete;
import net.refractions.udig.project.ui.internal.tool.ToolContext;
import net.refractions.udig.project.ui.internal.tool.impl.ToolContextImpl;
import net.refractions.udig.project.ui.tool.IContextMenuContributionTool;
import net.refractions.udig.project.ui.tool.IToolManager;
import net.refractions.udig.project.ui.tool.Tool;
import net.refractions.udig.project.ui.tool.ToolConstants;
import net.refractions.udig.ui.IDropAction;
import net.refractions.udig.ui.IDropHandlerListener;
import net.refractions.udig.ui.UDIGDragDropUtilities;
import net.refractions.udig.ui.ViewerDropLocation;
import net.refractions.udig.ui.operations.LazyOpFilter;
import net.refractions.udig.ui.operations.OpAction;
import net.refractions.udig.ui.operations.OpFilter;

import org.eclipse.core.commands.Command;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubCoolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.SubActionBars2;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.geotools.filter.Filter;

/**
 * Manages Edit tools activation and registration.
 * 
 * @author jeichar
 * @since 0.6.0
 */
public class ToolManager implements IToolManager {
	

    /**
     * This is a list of all tool actions(buttons) that are not part of the editor toolbar. For
     * example the info view may have a tool as part of its toolbar which is a proxy for the real
     * tool on the editor view.
     */
    Set<IAction> registeredToolActions = new HashSet<IAction>();

    protected List<String> categoryIds = new ArrayList<String>();
    
    /**
     * Modal Tool categories; these are usually represented
     * on the toolbar.
     */
    List<ModalToolCategory> modalCategories = new LinkedList<ModalToolCategory>();
    /**
     * ActionTool categories; these are usually represent on the toolbar.
     */
    List<ActionToolCategory> actionCategories = new LinkedList<ActionToolCategory>();
    /**
     * MenuToolCategories are dumped into specific spots on the menu.
     */
    List<MenuToolCategory> menuCategories = new LinkedList<MenuToolCategory>();
    /**
     * Background tools watch the map waiting to leap into action.
     */
    List<ToolProxy> backgroundTools = new LinkedList<ToolProxy>();
    /**
     * Access to shared images; used to represent the Tools on screen.
     */
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    
    /**
     * Cache of all configured cursors.
     */
    protected java.util.Map<String, CursorProxy> cursorsCache = new HashMap<String, CursorProxy>();

    /**
     * Current active tool.
     */
    protected ToolProxy activeModalToolProxy;
    
    /**
     * Default modal tool. This tool is set during
     * initialization of tools by ToolProxy.DEFAULT_ID.
     */
    ToolProxy defaultModalToolProxy;
    
    Lock redoLock=new ReentrantLock();
    Lock undoLock=new ReentrantLock();
    Lock forwardLock=new ReentrantLock();
    Lock backwardLock=new ReentrantLock();
    Lock deleteLock=new ReentrantLock();
    Lock pasteLock=new ReentrantLock();
    Lock propertiesLock=new ReentrantLock();
    Lock copyLock=new ReentrantLock();
    Lock cutLock=new ReentrantLock();

    private volatile IAction redoAction;
    private volatile IAction undoAction;
    private volatile IAction forwardAction;
    private volatile IAction backwardAction;
    private volatile IAction deleteAction;
    private volatile IAction pasteAction;
    private volatile IAction copyAction;
    private volatile IAction cutAction;
    
    
    
    private AdapterImpl commandListener;

    /**
     * Construct <code>ToolManager</code>.
     */
    public ToolManager() {
    	
//    	instance = this;

        processCategories();
        processTools();
        removeEmptyCategories();
        Collections.sort(categoryIds, new CategorySorter());
        setCommandHandlers();

    }
    
//    protected ToolManager getInstance(){
//    	return instance;
//    }

    /**
     * Populates the categories with their associated tools
     */
    private void processTools() {
        List<IConfigurationElement> extensionList = ExtensionPointList
                .getExtensionPointList(Tool.EXTENSION_ID);
        for( IConfigurationElement element : extensionList ) {
            IExtension extension = element.getDeclaringExtension();
            String type = element.getName();

            if (type.equals("backgroundTool")) { //$NON-NLS-1$

                ToolProxy proxy = new ToolProxy(extension, element, this);
                backgroundTools.add(proxy);
            } else if (type.equals("modalTool")) { //$NON-NLS-1$
                String categoryId = element.getAttribute("categoryId"); //$NON-NLS-1$
                ToolProxy proxy = new ToolProxy(extension, element, this);

                addToModalCategory(categoryId, proxy);
            } else if (type.equals("actionTool")) { //$NON-NLS-1$
                String categoryId = element.getAttribute("categoryId"); //$NON-NLS-1$
                ToolProxy proxy = new ToolProxy(extension, element, this);

                addToActionCategory(categoryId, proxy);
                addToMenuCategory(categoryId, proxy);
            }else if(type.equals("toolCursor")){ //$NON-NLS-1$
        		CursorProxy cursorProxy = new CursorProxy(element);
        		cursorsCache.put(cursorProxy.getID(), cursorProxy);
            }
        }
        
        if( activeModalToolProxy  == null )
        	activeModalToolProxy = defaultModalToolProxy;
    }
    

    /**
     * Finds cursor proxy by ID in cache.
     */
    public Cursor findToolCursor(String cursorID){
    	
    	CursorProxy cursorProxy = cursorsCache.get(cursorID);
    	if(cursorProxy != null)
    		return cursorProxy.getCursor();
    	
    	Cursor systemCursor = CursorProxy.getSystemCursor(cursorID);
    	if(systemCursor != null)
    		return systemCursor;
    	
    	
    	return null;
    	
    }
    

    /**
     * 
     * In the current implementation finds only among modal tools.
     * 
     * TODO Add necessary functionality
     */
	public Tool findTool(String toolID) {
		for(ModalToolCategory category : modalCategories){
			for (ModalItem item : category) {
				if(toolID.equals(item.getId())){
					return ((ToolProxy)item).getTool();
				}
			}
		}
		return null;
	}
    
    private void addToModalCategory( String categoryId, ToolProxy proxy ) {
        if( filterTool(categoryId, proxy, ModalToolCategory.class) ){
            return;
        }
        ModalToolCategory modalCategory = findModalCategory(categoryId);
        if (modalCategory == null) {
            modalCategory = findModalCategory(Messages.ToolCategory_other); 
            if (modalCategory == null) {
                modalCategory = new ModalToolCategory(this);
                modalCategories.add(modalCategory);
                if (!categoryIds.contains(Messages.ToolCategory_other)) 
                    categoryIds.add(Messages.ToolCategory_other); 
            }
        }
        modalCategory.add(proxy);
    }

    /**
     * This method is called each time an action is about to be added to a category.  
     * If the message returns true the tool <b>will not</b> be added.
     * The default implementation always returns false.
     * 
     * @param categoryId the id of the category that the tool will be added to
     * @param proxy the proxy for the tool.  
     * @param categoryType the type of category
     * 
     * @return true if the tool will NOT be added to the category
     */
    protected boolean filterTool( String categoryId, ToolProxy proxy, Class<? extends ToolCategory> categoryType ) {
        return false;
    }

    private void addToMenuCategory( String categoryId, ToolProxy proxy ) {

        if( filterTool(categoryId, proxy, MenuToolCategory.class) ){
            return;
        }
        MenuToolCategory category = findMenuCategory(categoryId);
        if (category == null) {
            category = findMenuCategory(Messages.ToolCategory_other); 

            if (category == null) {
                category = new MenuToolCategory(this);
                menuCategories.add(category);
                if (!categoryIds.contains(Messages.ToolCategory_other)) 
                    categoryIds.add(Messages.ToolCategory_other); 
            }
        }
        category.add(proxy);
    }

    private void addToActionCategory( String categoryId, ToolProxy proxy ) {
        if( filterTool(categoryId, proxy, ActionToolCategory.class) ){
            return;
        }
        
        ActionToolCategory category = findActionCategory(categoryId);
        if (category == null) {
            category = findActionCategory(Messages.ToolCategory_other); 

            if (category == null) {
                category = new ActionToolCategory(this);
                actionCategories.add(category);
                if (!categoryIds.contains(Messages.ToolCategory_other)) 
                    categoryIds.add(Messages.ToolCategory_other); 
            }
        }
        category.add(proxy);
    }

    /**
     * Processes the extension point and creates all the categories.
     */
    private void processCategories() {
        List<IConfigurationElement> extension = ExtensionPointList
                .getExtensionPointList(Tool.EXTENSION_ID);
        for( IConfigurationElement element : extension ) {
            if (!element.getName().equals("category")) //$NON-NLS-1$
                continue;
            ModalToolCategory modalCategory;
            String id = element.getAttribute("id"); //$NON-NLS-1$
            categoryIds.add(id);
            modalCategory = findModalCategory(id);
            if (modalCategory == null) {
                modalCategory = new ModalToolCategory(element, ToolManager.this);
                modalCategories.add(modalCategory);
            }
            ActionToolCategory actionCategory;
            actionCategory = findActionCategory(id);
            if (actionCategory == null) {
                actionCategory = new ActionToolCategory(element, ToolManager.this);
                actionCategories.add(actionCategory);
            }
            MenuToolCategory category;
            category = findMenuCategory(id);
            if (category == null) {
                category = new MenuToolCategory(element, ToolManager.this);
                menuCategories.add(category);
            }
        }
    }

    private void removeEmptyCategories() {
        List<ToolCategory> toRemove = new ArrayList<ToolCategory>();
        for( ActionToolCategory category : actionCategories ) {
            if (category.items.size() == 0)
                toRemove.add(category);
        }
        actionCategories.removeAll(toRemove);
        for( ModalToolCategory category : modalCategories ) {
            if (category.items.size() == 0)
                toRemove.add(category);
        }
        modalCategories.removeAll(toRemove);
        for( MenuToolCategory category : menuCategories ) {
            if (category.items.size() == 0)
                toRemove.add(category);
        }
        menuCategories.removeAll(toRemove);
    }

    private void setCommandHandlers() {
        Set<String> ids = new HashSet<String>();
        ICommandService service = (ICommandService) PlatformUI.getWorkbench().getAdapter(
                ICommandService.class);
        for( ModalToolCategory category : modalCategories ) {
            if (!ids.contains(category.getId())) {
                ids.add(category.getId());
                category.setCommandHandlers(service);
            }
            registerCommands(ids, service, category);
        }
        for( ActionToolCategory category : actionCategories ) {
            if (!ids.contains(category.getId())) {
                ids.add(category.getId());
                category.setCommandHandlers(service);
            }
            registerCommands(ids, service, category);
        }
        for( MenuToolCategory category : menuCategories ) {
            if (!ids.contains(category.getId())) {
                ids.add(category.getId());
                category.setCommandHandlers(service);
            }
            registerCommands(ids, service, category);
        }

    }

    /**
     *
     * @param ids
     * @param service
     * @param category
     */
    private void registerCommands( Set<String> ids, ICommandService service, ToolCategory category ) {
        for( ModalItem tool : category ) {
            if (!ids.contains(tool.getId())) {
                ids.add(category.getId());

                for( String currentId : tool.getCommandIds() ) {
                    currentId=currentId.trim();
                    Command command = service.getCommand(currentId);
                    if (command != null)
                        command.setHandler(tool.getHandler(currentId));
                }
            }
        }
    }
    
    MapPart currentEditor;

    /**
     * This method is called to perform tools initialization when
     * the map editor is selected.
     * 
     */
    public void setCurrentEditor( MapPart editor ) {
    	
        if( editor==currentEditor )
            return;
        
        currentEditor = editor;
        if (editor != null) {
            if (editor != null) {
                setActiveTool(editor);
                setEnabled(editor.getMap(), actionCategories);
                setEnabled(editor.getMap(), menuCategories);
                setEnabled(editor.getMap(), modalCategories);
            }
        } else {
            disable(actionCategories);
            disable(menuCategories);
            disable(modalCategories);
        }
    	
    }

    private void disable( List<? extends ToolCategory> categories ) {
        for( ToolCategory category : categories ) {
            for( ModalItem item : category ) {
                OpFilter enablesFor = item.getEnablesFor();
                if( enablesFor instanceof LazyOpFilter)
                    ((LazyOpFilter)enablesFor).disable();
            }
        }
    }

    EditManagerListener selectedLayerListener;

    private PropertyDialogAction propertiesAction;
    
    /**
     * Listener for EditManager.
     * 
     * @author Vitalus
     *
     */
    class EditManagerListener implements IEditManagerListener{
    	
    	public void setCurrentMap(IMap map){
    	}

        public void changed( EditManagerEvent event ) {
            if (selectedLayerListener != this) {
                event.getSource().removeListener(this);
                return;
            }
            if (event.getType() == EditManagerEvent.SELECTED_LAYER) {
                setEnabled(event.getSource().getMap(), actionCategories);
                setEnabled(event.getSource().getMap(), menuCategories);
                setEnabled(event.getSource().getMap(), modalCategories);
            }
        }
    }

    private void setEnabled( IMap map, Collection<? extends ToolCategory> categories) {
        
        if(selectedLayerListener == null)
        	selectedLayerListener = new EditManagerListener();
        
        selectedLayerListener.setCurrentMap(map);

        //One listener is enough. Say NO to listeners hell:)
        if(!map.getEditManager().containsListener(selectedLayerListener))
        	map.getEditManager().addListener(selectedLayerListener);
        
        for( ToolCategory cat : categories ) {
            for( ModalItem item : cat ) {
                OpFilter enablesFor = item.getEnablesFor();
                ILayer selectedLayer = map.getEditManager().getSelectedLayer();

                assert enablesFor instanceof LazyOpFilter;
                
                if( !(enablesFor instanceof LazyOpFilter) ){
                    enablesFor = new LazyOpFilter(item, enablesFor);
                }
                
                boolean accept = enablesFor.accept(selectedLayer);
                item.setEnabled(accept);
            }
        }
    }

    /**
     * Sets the context of the currently active tool and ensures that all tools are enabled. This is
     * a small hack so that tools are active even when the editor is not in focus.
     * 
     * @param editor
     */
    @SuppressWarnings("unchecked")
    void setActiveTool( MapPart editor ) {
        if (!editor.getMap().eAdapters().contains(getCommandListener(editor)))
            editor.getMap().eAdapters().add(getCommandListener(editor));
        ToolContext tools = new ToolContextImpl();
        tools.setMapInternal(editor.getMap());

        setContext(modalCategories, tools);
        setContext(actionCategories, tools);
        setContext(menuCategories, tools);

        for( ToolProxy tool : backgroundTools ) {
            tool.setContext(tools);
        }
        for( Iterator iter = registeredToolActions.iterator(); iter.hasNext(); )
            ((IAction) iter.next()).setEnabled(true);

        setCommandActions(editor.getMap(), editor);
        editor.setSelectionProvider(activeModalToolProxy.getSelectionProvider());
    }

    private void setContext( List categories, ToolContext tools ) {
        for( Iterator iter = categories.iterator(); iter.hasNext(); ) {
            ToolCategory category = (ToolCategory) iter.next();
            for( Iterator titer = category.iterator(); titer.hasNext(); ) {
                ToolProxy tool = (ToolProxy) titer.next();
                tool.setContext(tools);
            }
        }
    }

    /**
     * Adds an Action that executes a tool to the toolbar.
     * 
     * @param action
     */
    public void addToolAction( IAction action ) {
        registeredToolActions.add(action);
        action.setEnabled(ApplicationGIS.getActiveMap() != ApplicationGIS.NO_MAP);
    }

    /**
     * Creates a action that acts as a proxy for the tool in the editor toolbar.
     * <p>
     * The client code must set the name image descriptor etc... of the Action
     * </p>
     * 
     * @param toolID the id of the tool
     * @param categoryID the category the tool is part of
     * @return a proxy action that can be put in other toolbars
     */
    public IAction createToolAction( final String toolID, final String categoryID ) {
        final IAction toolAction = new Action(){
            @Override
            public void runWithEvent( Event event ) {
                IAction action = getTool(toolID, categoryID);
                if (action != null && action.isEnabled()) {
                    action.runWithEvent(event );
                }
            }
        };
        toolAction.addPropertyChangeListener(new IPropertyChangeListener(){

            public void propertyChange( PropertyChangeEvent event ) {
                if( event.getProperty().equals(IAction.ENABLED)){
                    toolAction.setEnabled((Boolean) event.getNewValue());
                }
            }
            
        });
        toolAction.setEnabled(getTool(toolID, categoryID).isEnabled());
        addToolAction(toolAction);
        return toolAction;
    }

    /**
     * 
     */
    public ActionToolCategory findActionCategory( String id ) {
        for( ActionToolCategory category : actionCategories ) {
            if (category.getId().equals(id))
                return category;
        }
        return null;
    }

    MenuToolCategory findMenuCategory( String id ) {
        for( MenuToolCategory category : menuCategories ) {
            if (category.getId().equals(id))
                return category;
        }
        return null;
    }

    protected ModalToolCategory findModalCategory( String id ) {
        for( ModalToolCategory category : modalCategories ) {
            String id2 = category.getId();
            if (id2.equals(id))
                return category;
        }
        return null;
    }

    /**
     * Used to contribute Tools to the provided menu manger.
     * <p>
     * The following contributions are made:
     * <ul>
     * <li>navigate: forward and backward buttons
     * <li>map: an entry for each tool category
     * </ul>
     */
    public void contributeToMenu( IMenuManager manager ) {
        IMenuManager navigateMenu = manager.findMenuUsingPath(IWorkbenchActionConstants.M_NAVIGATE);
        if (navigateMenu == null) {
        	navigateMenu = new MenuManager(
        			Messages.ToolManager_menu_manager_title, IWorkbenchActionConstants.M_NAVIGATE);

        	IContributionItem additions = manager.find(IWorkbenchActionConstants.MB_ADDITIONS);
			if( additions==null || !(additions instanceof GroupMarker) ){
				manager.add(navigateMenu);
        	}else{
        		manager.appendToGroup(IWorkbenchActionConstants.MB_ADDITIONS, navigateMenu);
        	}			
            navigateMenu.add(new GroupMarker(IWorkbenchActionConstants.NAV_START));
            navigateMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));            
            navigateMenu.add(new GroupMarker(IWorkbenchActionConstants.NAV_END));
        }
        
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        navigateMenu.appendToGroup(IWorkbenchActionConstants.NAV_END,
        		ActionFactory.BACK.create(window));
        navigateMenu.appendToGroup(IWorkbenchActionConstants.NAV_END,
        		ActionFactory.FORWARD.create(window));
        
        if (!manager.isVisible()){
            manager.setVisible(true);
        }
        IMenuManager mapMenu = manager.findMenuUsingPath("map");        
        if( mapMenu == null ){
            mapMenu = new MenuManager(
                    Messages.ToolManager_menu_manager_title, "map");
            manager.add(mapMenu);
            mapMenu.add(new GroupMarker("mapStart"));
            mapMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
            mapMenu.add(new GroupMarker("mapEnd"));
        }
        for( MenuToolCategory category : menuCategories ) {
            category.contribute(manager);
        }
    }
    
    
    

    /** (non-Javadoc)
     * 
     * @see net.refractions.udig.project.ui.tool.IToolManager#contributeActiveModalTool(org.eclipse.jface.action.IMenuManager)
     */
    public void contributeActiveModalTool( IMenuManager manager ) {
        
        Tool activeTool = getActiveTool();
        if(activeTool instanceof IContextMenuContributionTool){
            IContextMenuContributionTool contributionTool = (IContextMenuContributionTool)activeTool;
            ArrayList<IContributionItem> contributions = new ArrayList<IContributionItem>();
            contributionTool.contributeContextMenu(contributions);
            
            if(!contributions.isEmpty()){
                manager.add(new Separator());
                for( IContributionItem item : contributions ) {
                    manager.add(item);
                }
            }
        }
    }

    /**
     * Retrieves the redo action that is used by much of the map components such as the MapEditor
     * and the LayersView. redoes the last undone command sent to the currently active map.
     */
    public IAction getREDOAction() {
        Map activeMap = ApplicationGISInternal.getActiveMap();
        redoLock.lock();
        try{
            if (redoAction == null) {
                redoAction = new Action(){
                    /**
                     * @see org.eclipse.jface.action.Action#run()
                     */
                    public void run() {
                        Map activeMap = ApplicationGISInternal.getActiveMap();
                        if (activeMap != ApplicationGIS.NO_MAP)
                            activeMap.redo();
                    }
                };
                redoAction.setImageDescriptor(sharedImages
                        .getImageDescriptor(ISharedImages.IMG_TOOL_REDO));
                redoAction.setText(Messages.ToolManager_redoAction); 
                redoAction.setActionDefinitionId("org.eclipse.ui.edit.redo"); //$NON-NLS-1$
            }
            if (activeMap != ApplicationGIS.NO_MAP)
                redoAction.setEnabled(activeMap.getCommandStack().canRedo());
            else
                redoAction.setEnabled(false);

        return redoAction;
        }finally{
            redoLock.unlock();
        }
    }

    /**
     * 
     */
    public void setREDOAction( IAction action, IWorkbenchPart part ) {
        if( action == null )
            throw new NullPointerException("action must not be null"); //$NON-NLS-1$
        if( part == null )
            throw new NullPointerException("part must not be null"); //$NON-NLS-1$
        redoLock.lock();
        try {
            redoAction = action;
            redoAction.setActionDefinitionId("org.eclipse.ui.edit.redo"); //$NON-NLS-1$
            IKeyBindingService service = part.getSite().getKeyBindingService();
            service.registerAction(redoAction);
        } finally {
            redoLock.unlock();
        }
    }
    /**
     * Retrieves the undo action that is used by much of the map components such as the MapEditor
     * and the LayersView. Undoes the last command sent to the currently active map.
     * 
     * @param part
     */
    public IAction getUNDOAction() {
        Map activeMap = ApplicationGISInternal.getActiveMap();
        undoLock.lock();
        try{
            if (undoAction == null) {
                undoAction = new Action(){
                    /**
                     * @see org.eclipse.jface.action.Action#run()
                     */
                    public void run() {
                        Map activeMap = ApplicationGISInternal.getActiveMap();
                        if (activeMap != ApplicationGIS.NO_MAP)
                            activeMap.undo();
                    }
                };
                undoAction.setImageDescriptor(sharedImages
                        .getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
                undoAction.setText(Messages.ToolManager_undoAction); 
                undoAction.setActionDefinitionId("org.eclipse.ui.edit.undo"); //$NON-NLS-1$
            }
            if (activeMap != ApplicationGIS.NO_MAP)
                undoAction.setEnabled(activeMap.getCommandStack().canUndo());
            else
                undoAction.setEnabled(false);
            return undoAction;
        }finally{
            undoLock.unlock();
        }
    }

    /**
     * 
     */
    public void setUNDOAction( IAction action, IWorkbenchPart part ) {
        if( action == null )
            throw new NullPointerException("action must not be null"); //$NON-NLS-1$
        if( part == null )
            throw new NullPointerException("part must not be null"); //$NON-NLS-1$
        undoLock.lock();
        try{
            undoAction = action;
            undoAction.setActionDefinitionId("org.eclipse.ui.edit.undo"); //$NON-NLS-1$
            IKeyBindingService service = part.getSite().getKeyBindingService();
            service.registerAction(undoAction);
        }finally{
            undoLock.unlock();
        }
    }
    /**
     * Retrieves the forward navigation action that is used by much of the map components such as
     * the MapEditor and the LayersView. Executes the last undone Nav command on the current map.
     */
    public IAction getFORWARD_HISTORYAction() {
        Map activeMap = ApplicationGISInternal.getActiveMap();
        forwardLock.lock();
        try{
            if (forwardAction == null) {
                forwardAction = new Action(){
                    /**
                     * @see org.eclipse.jface.action.Action#run()
                     */
                    public void run() {
                        Map activeMap = ApplicationGISInternal.getActiveMap();
                        if (activeMap != ApplicationGIS.NO_MAP)
                            activeMap.forwardHistory();
                    }
                };
                forwardAction.setImageDescriptor(sharedImages
                        .getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
                forwardAction.setText(Messages.ToolManager_forward); 
                forwardAction.setToolTipText(Messages.ToolManager_forward_tooltip); 
                forwardAction.setActionDefinitionId("org.eclipse.ui.navigate.forward"); //$NON-NLS-1$
            }
            if (activeMap != ApplicationGIS.NO_MAP)
                forwardAction.setEnabled(activeMap.getCommandStack().canRedo());
            else
                forwardAction.setEnabled(false);
            return forwardAction;
        }finally{
            forwardLock.unlock();
        }

    }

    /**
     * 
     */
    public void setFORWARDAction( IAction action, IWorkbenchPart part ) {
        if( action == null )
            throw new NullPointerException("action must not be null"); //$NON-NLS-1$
        if( part == null )
            throw new NullPointerException("part must not be null"); //$NON-NLS-1$
        forwardLock.lock();
        try{
            forwardAction = action;
            forwardAction.setActionDefinitionId("org.eclipse.ui.navigate.forward"); //$NON-NLS-1$
            IKeyBindingService service = part.getSite().getKeyBindingService();
            service.registerAction(forwardAction);
        }finally{
            forwardLock.unlock();
        }
    }

    public void registerActionsWithPart( IWorkbenchPart part ) {

        IKeyBindingService service = part.getSite().getKeyBindingService();
        service.registerAction(getBACKWARD_HISTORYAction());
        service.registerAction(getFORWARD_HISTORYAction());
        service.registerAction(getCOPYAction(part));
        service.registerAction(getCUTAction(part));
        service.registerAction(getDELETEAction());
        service.registerAction(getPASTEAction(part));
        service.registerAction(getREDOAction());
        service.registerAction(getUNDOAction());
        
        addToolScope(part.getSite());
    }
    
    public void unregisterActions( IWorkbenchPart  part ){

        IKeyBindingService service = part.getSite().getKeyBindingService();

        service.unregisterAction(getBACKWARD_HISTORYAction());
        service.unregisterAction(getFORWARD_HISTORYAction());
        service.unregisterAction(getCOPYAction(part));
        service.unregisterAction(getCUTAction(part));
        service.unregisterAction(getDELETEAction());
        service.unregisterAction(getPASTEAction(part));
        service.unregisterAction(getREDOAction());
        service.unregisterAction(getUNDOAction());
        
        service.setScopes(new String[0]);
    }

    /**
     * Retrieves the backward navigation action that is used by much of the map components such as
     * the MapEditor and the LayersView. Undoes the last Nav command set to the current map.
     * 
     * @param part
     */
    public IAction getBACKWARD_HISTORYAction() {
        Map activeMap = ApplicationGISInternal.getActiveMap();
        backwardLock.lock();
        try{
            if (backwardAction == null) {
                backwardAction = new Action(){
                    /**
                     * @see org.eclipse.jface.action.Action#run()
                     */
                    public void run() {
                        Map activeMap = ApplicationGISInternal.getActiveMap();
                        if (activeMap != ApplicationGIS.NO_MAP)
                            activeMap.backwardHistory();
                    }

                };
                backwardAction.setImageDescriptor(sharedImages
                        .getImageDescriptor(ISharedImages.IMG_TOOL_BACK));
                backwardAction.setText(Messages.ToolManager_back); 
                backwardAction.setToolTipText(Messages.ToolManager_back_tooltip); 
                backwardAction.setActionDefinitionId("org.eclipse.ui.navigate.back"); //$NON-NLS-1$
            }
            if (activeMap != ApplicationGIS.NO_MAP)
                backwardAction.setEnabled(activeMap.getCommandStack().canUndo());
            else
                backwardAction.setEnabled(false);
        }finally{
            backwardLock.unlock();
        }

        return backwardAction;
    }

    /**
     * 
     */
    public void setBACKAction( IAction action, IWorkbenchPart part ) {
        if( action == null )
            throw new NullPointerException("action must not be null"); //$NON-NLS-1$
        if( part == null )
            throw new NullPointerException("part must not be null"); //$NON-NLS-1$
        backwardLock.lock();
        try{
            backwardAction = action;
            backwardAction.setActionDefinitionId("org.eclipse.ui.navigate.back"); //$NON-NLS-1$
            IKeyBindingService service = part.getSite().getKeyBindingService();
            service.registerAction(backwardAction);
        }finally{
            backwardLock.unlock();
        }

    }
    
    /**
     * 
     */
    public IAction getCUTAction( IWorkbenchPart part ) {
        cutLock.lock();
        try{
            if( cutAction==null ){
                cutAction=new Action(){
                    
                };
            }
            // JONES
            return cutAction;
        }finally{
            cutLock.unlock();
        }
    }

    public void setCUTAction( IAction action, IWorkbenchPart part ) {
        if( action == null )
            throw new NullPointerException("action must not be null"); //$NON-NLS-1$
        if( part == null )
            throw new NullPointerException("part must not be null"); //$NON-NLS-1$
        cutLock.lock();
        try{
            cutAction = action;
            cutAction.setActionDefinitionId("org.eclipse.ui.edit.cut"); //$NON-NLS-1$
            IKeyBindingService service = part.getSite().getKeyBindingService();
            service.registerAction(cutAction);
        }finally{
            cutLock.unlock();
        }
    }

    public IAction getCOPYAction( final IWorkbenchPart part ) {
        copyLock.lock();
        try{
            if (copyAction == null) {
                    if (copyAction == null) {
                        copyAction = new CopyAction();
                        IAction template = ActionFactory.COPY.create(part.getSite().getWorkbenchWindow());
                        copyAction.setText(template.getText());
                        copyAction.setToolTipText(template.getToolTipText());
                        copyAction.setImageDescriptor(template.getImageDescriptor());
                        copyAction.setId(template.getId());
                        copyAction.setActionDefinitionId("org.eclipse.ui.edit.copy"); //$NON-NLS-1$
                    }
            }
            if (copyAction instanceof CopyAction) {
                ((CopyAction) copyAction).setPart(part);
            }
            IKeyBindingService service = part.getSite().getKeyBindingService();
            service.registerAction(copyAction);
            return copyAction;
        }finally{
            copyLock.unlock();
        }
    }

    public void setCOPYAction( IAction action, IWorkbenchPart part ) {
        if( action == null )
            throw new NullPointerException("action must not be null"); //$NON-NLS-1$
        if( part == null )
            throw new NullPointerException("part must not be null"); //$NON-NLS-1$
        copyLock.lock();
        try{
            copyAction = action;
            copyAction.setActionDefinitionId("org.eclipse.ui.edit.copy"); //$NON-NLS-1$
            IKeyBindingService service = part.getSite().getKeyBindingService();
            service.registerAction(copyAction);
        }finally{
            copyLock.unlock();
        }
    }
    // 
    public IAction getPropertiesAction( IWorkbenchPart part, ISelectionProvider selectionProvider ) {
        propertiesLock.lock();
        try{
            if (propertiesAction == null ||
                    propertiesAction.getSelectionProvider() != selectionProvider ){
                propertiesAction  = new PropertyDialogAction( part.getSite().getWorkbenchWindow(), selectionProvider );
            }
            IKeyBindingService service = part.getSite().getKeyBindingService();
            service.registerAction(propertiesAction);
            return propertiesAction;
        } finally {
            propertiesLock.unlock();
        }
    }
    
    public IAction getPASTEAction( IWorkbenchPart part ) {
        pasteLock.lock();
        try{
            if (pasteAction == null) {
                pasteAction = new PasteAction();
                IAction template = ActionFactory.PASTE.create(part.getSite().getWorkbenchWindow());
                pasteAction.setText(template.getText());
                pasteAction.setToolTipText(template.getToolTipText());
                pasteAction.setImageDescriptor(template.getImageDescriptor());
                pasteAction.setId(template.getId());
                pasteAction.setActionDefinitionId("org.eclipse.ui.edit.paste"); //$NON-NLS-1$
            }
            if (pasteAction instanceof PasteAction) {
                ((PasteAction) pasteAction).setPart(part);
            }
    
            IKeyBindingService service = part.getSite().getKeyBindingService();
            service.registerAction(pasteAction);
            return pasteAction;
        }finally{
            pasteLock.unlock();
        }
    }

    public void setPASTEAction( IAction action, IWorkbenchPart part ) {
        if( action == null )
            throw new NullPointerException("action must not be null"); //$NON-NLS-1$
        if( part == null )
            throw new NullPointerException("part must not be null"); //$NON-NLS-1$
        pasteLock.lock();
        try{
            pasteAction = action;
            pasteAction.setActionDefinitionId("org.eclipse.ui.edit.paste"); //$NON-NLS-1$
            IKeyBindingService service = part.getSite().getKeyBindingService();
            service.registerAction(pasteAction);
        }finally{
            pasteLock.unlock();
        }
    }

    public synchronized IAction getDELETEAction() {
        deleteLock.lock();
        try{
            if (deleteAction == null) {
                deleteAction = new Action(){
                    @Override
                    public void run() {
                        Delete delete=new Delete();
                        ISelection s = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
                        delete.selectionChanged(this, s);
                        delete.run(this);                }
                };
                deleteAction.setActionDefinitionId("org.eclipse.ui.edit.delete"); //$NON-NLS-1$
                IWorkbenchAction actionTemplate = ActionFactory.DELETE.create(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
                deleteAction.setText(actionTemplate.getText());
                deleteAction.setToolTipText(actionTemplate.getToolTipText());
                deleteAction.setImageDescriptor(actionTemplate.getImageDescriptor());
                deleteAction.setDescription(actionTemplate.getDescription());
                deleteAction.setDisabledImageDescriptor(actionTemplate.getDisabledImageDescriptor());
            }
    
            return deleteAction;
        }finally{
            deleteLock.unlock();
        }
    }

    public synchronized void setDELETEAction( IAction action, IWorkbenchPart part ) {
        if( action == null )
            throw new NullPointerException("action must not be null"); //$NON-NLS-1$
        if( part == null )
            throw new NullPointerException("part must not be null"); //$NON-NLS-1$
        deleteLock.lock();
        try{
            deleteAction = action;
            deleteAction.setActionDefinitionId("org.eclipse.ui.edit.delete"); //$NON-NLS-1$
            IKeyBindingService service = part.getSite().getKeyBindingService();
            service.registerAction(deleteAction);
        }finally{
            deleteLock.unlock();
        }
    }

    private void createModalToolToolbar( SubCoolBarManager cbmanager ) {
        ToolBarManager manager = new ToolBarManager(SWT.FLAT);

        for( String id : categoryIds ) {
            ModalToolCategory modalCategory = findModalCategory(id);
            if (modalCategory != null) {
                modalCategory.contribute(manager);
            }
        }
        if (manager != null && manager.getItems().length > 0)
            cbmanager.add(manager);
    }

    private void createActionToolToolbar( SubCoolBarManager cbmanager ) {
        ToolBarManager manager = new ToolBarManager(SWT.FLAT);

        manager.add(getBACKWARD_HISTORYAction());
        manager.add(getFORWARD_HISTORYAction());
        for( String id : categoryIds ) {
            ActionToolCategory category = findActionCategory(id);
            if (category != null)
                category.contribute(manager);
        }
        if (manager != null && manager.getItems().length > 0)
            cbmanager.add(manager);
    }

    /**
     * Adds both action tools and modal tools to the manager
     * 
     * @deprecated
     * 
     * @see contributeActionTools(IToolBarManager toolBarManager, IActionBars bars )
     * @see contributeModalTools(IToolBarManager toolBarManager, IActionBars bars )
     * 
     * @param cbmanager
     * @param bars
     * @see net.refractions.udig.project.ui.tool.ModalTool
     * @see net.refractions.udig.project.ui.tool.ActionTool
     */
    public void contributeToCoolBar( SubCoolBarManager cbmanager, IActionBars bars ) {
        cbmanager.setVisible(true);
        createActionToolToolbar(cbmanager);
        createModalToolToolbar(cbmanager);
    }
    
    


    /* (non-Javadoc)
     * @see net.refractions.udig.project.ui.tool.IToolManager#contributeActionTools(org.eclipse.jface.action.IToolBarManager, org.eclipse.ui.IActionBars)
     */
    public void contributeActionTools(IToolBarManager toolBarManager, IActionBars bars ) {
        toolBarManager.add(getBACKWARD_HISTORYAction());
        toolBarManager.add(getFORWARD_HISTORYAction());
        for( String id : categoryIds ) {
            ActionToolCategory category = findActionCategory(id);
            if (category != null)
                category.contribute(toolBarManager);
        }
    }

    /* (non-Javadoc)
     * @see net.refractions.udig.project.ui.tool.IToolManager#contributeModalTools(org.eclipse.jface.action.IToolBarManager, org.eclipse.ui.IActionBars)
     */
    public void contributeModalTools(IToolBarManager toolBarManager, IActionBars bars ) {
        for( String id : categoryIds ) {
            ModalToolCategory modalCategory = findModalCategory(id);
            if (modalCategory != null) {
                modalCategory.contribute(toolBarManager);
            }
        }
    }

    SubActionBars2 getActionBars() {
        if (ApplicationGISInternal.getActiveMap() == ApplicationGIS.NO_MAP)
            return null;
        return ApplicationGISInternal.getActiveEditor().getActionbar();
    }
    
    private IAction actionCLOSE;
    private IAction actionSAVE;
    private IAction actionCLOSE_ALL;

    /**
     * Contributes the common global actions.
     * @param part WorkbenchPart such as a view or editor
     * @param bars Actionbar used to register global actions
     */
    public void contributeGlobalActions( IWorkbenchPart part, IActionBars bars ) {
        IKeyBindingService service = part.getSite().getKeyBindingService();
        bars.setGlobalActionHandler(ActionFactory.BACK.getId(), getBACKWARD_HISTORYAction());
        bars.setGlobalActionHandler(ActionFactory.FORWARD.getId(), getFORWARD_HISTORYAction());
        bars.setGlobalActionHandler(ActionFactory.UNDO.getId(), getUNDOAction());
        bars.setGlobalActionHandler(ActionFactory.REDO.getId(), getREDOAction());
        bars.setGlobalActionHandler(ActionFactory.CUT.getId(), getCUTAction(part));
        bars.setGlobalActionHandler(ActionFactory.COPY.getId(), getCOPYAction(part));
        bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), getPASTEAction(part));
        bars.setGlobalActionHandler(ActionFactory.DELETE.getId(), getDELETEAction());
        
        ISelectionProvider selection = part.getSite().getSelectionProvider();
        if( selection != null ){
            bars.setGlobalActionHandler(ActionFactory.PROPERTIES.getId(), getPropertiesAction(part,selection));
        }
        
        if(actionCLOSE == null)
        	actionCLOSE = ActionFactory.CLOSE.create(part.getSite().getWorkbenchWindow());
        service.registerAction(actionCLOSE);
        bars.setGlobalActionHandler(ActionFactory.CLOSE.getId(), actionCLOSE);

        if(actionSAVE == null)
        	actionSAVE = ActionFactory.SAVE.create(part.getSite().getWorkbenchWindow());
        service.registerAction(actionSAVE);
        bars.setGlobalActionHandler(ActionFactory.SAVE.getId(), actionSAVE);

        if(actionCLOSE_ALL == null)
        	actionCLOSE_ALL = ActionFactory.CLOSE_ALL.create(part.getSite().getWorkbenchWindow());
        bars.setGlobalActionHandler(ActionFactory.CLOSE_ALL.getId(), actionCLOSE_ALL);

    }

    void dispose() {
        for( ToolCategory category : modalCategories ) {
            category.dispose(ApplicationGISInternal.getActiveEditor().getEditorSite()
                    .getActionBars());
        }
        for( ToolCategory category : actionCategories ) {
            category.dispose(ApplicationGISInternal.getActiveEditor().getEditorSite()
                    .getActionBars());
        }
        for( ToolCategory category : menuCategories ) {
            category.dispose(ApplicationGISInternal.getActiveEditor().getEditorSite()
                    .getActionBars());
        }
        for( ToolProxy tool : backgroundTools ) {
            tool.dispose();
        }
        
    }

    Adapter getCommandListener( final MapPart editor ) {
        if (commandListener == null) {
            commandListener = new AdapterImpl(){
                /**
                 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
                 */
                public void notifyChanged( Notification msg ) {
                    // Map map = (Map) getTarget();
                    /*
                     * While this adapter is a singlton and added to all opened maps, each time
                     * target variable is reset.
                     */
                    Map map = null;
                    switch( msg.getFeatureID(msg.getNotifier().getClass()) ) {
                    case ProjectPackage.MAP__COMMAND_STACK:
                        map = (Map) msg.getNotifier();
                        setCommandActions(map, editor);
                        break;
                    case ProjectPackage.MAP__NAV_COMMAND_STACK:
                        map = (Map) msg.getNotifier();
                        setCommandActions(map, editor);
                        break;
                    }

                }
            };
        }
        return commandListener;
    }

    void setCommandActions( Map map, MapPart editor ) {
        if (map.getCommandStack().canRedo())
            getREDOAction().setEnabled(true);
        else
            getREDOAction().setEnabled(false);
        if (map.getCommandStack().canUndo())
            getUNDOAction().setEnabled(true);
        else
            getUNDOAction().setEnabled(false);
        if (map.getNavCommandStack().hasBackHistory())
            getBACKWARD_HISTORYAction().setEnabled(true);
        else
            getBACKWARD_HISTORYAction().setEnabled(false);
        if (map.getNavCommandStack().hasForwardHistory())
            getFORWARD_HISTORYAction().setEnabled(true);
        else
            getFORWARD_HISTORYAction().setEnabled(false);
    }

    public IAction getTool( String toolID, String categoryID ) {
        return getToolAction(toolID, categoryID);
    }
    
    public IAction getToolAction( String toolID, String categoryID ) {
        final IAction tool = getToolInteral(toolID, categoryID);

        if( tool == null )
            return null;
        
        return new IAction(){
            IAction wrapped=tool;

            public void addPropertyChangeListener( IPropertyChangeListener listener ) {
                wrapped.addPropertyChangeListener(listener);
            }

            public int getAccelerator() {
                return wrapped.getAccelerator();
            }

            public String getActionDefinitionId() {
                return wrapped.getActionDefinitionId();
            }

            public String getDescription() {
                return wrapped.getDescription();
            }

            public ImageDescriptor getDisabledImageDescriptor() {
                return wrapped.getDisabledImageDescriptor();
            }

            public HelpListener getHelpListener() {
                return wrapped.getHelpListener();
            }

            public ImageDescriptor getHoverImageDescriptor() {
                return wrapped.getHoverImageDescriptor();
            }

            public String getId() {
                return wrapped.getId();
            }

            public ImageDescriptor getImageDescriptor() {
                return wrapped.getImageDescriptor();
            }

            public IMenuCreator getMenuCreator() {
                return wrapped.getMenuCreator();
            }

            public int getStyle() {
                return wrapped.getStyle();
            }

            public String getText() {
                return wrapped.getText();
            }

            public String getToolTipText() {
                return wrapped.getToolTipText();
            }

            public boolean isChecked() {
                return wrapped.isChecked();
            }

            public boolean isEnabled() {
                return wrapped.isEnabled();
            }

            public boolean isHandled() {
                return wrapped.isHandled();
            }

            public void removePropertyChangeListener( IPropertyChangeListener listener ) {
                wrapped.removePropertyChangeListener(listener);
            }

            public void runWithEvent( Event event ) {
                wrapped.runWithEvent(event);
            }

            public void run() {
                wrapped.run();
            }

            public void setAccelerator( int keycode ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setActionDefinitionId( String id ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setChecked( boolean checked ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setDescription( String text ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setDisabledImageDescriptor( ImageDescriptor newImage ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setEnabled( boolean enabled ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setHelpListener( HelpListener listener ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setHoverImageDescriptor( ImageDescriptor newImage ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setId( String id ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setImageDescriptor( ImageDescriptor newImage ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setMenuCreator( IMenuCreator creator ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setText( String text ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }

            public void setToolTipText( String text ) {
                throw new UnsupportedOperationException("This is an unmodifiable action"); //$NON-NLS-1$
            }
        };
    }

    /**
     * returns the actual tool action.  
     */
    private IAction getToolInteral( String toolID, String categoryID ) {
        ToolCategory category = findModalCategory(categoryID);
        if (category != null) {
            IAction tool = searchCategoryForTool(toolID, category);
            if( tool!=null )
                return tool;
        }
        
        category = findActionCategory(categoryID);
        if (category != null) {
            IAction tool = searchCategoryForTool(toolID, category);
            if( tool!=null )
                return tool;
        }
        
        category = findMenuCategory(categoryID);
        if (category != null) {
            IAction tool = searchCategoryForTool(toolID, category);
            if( tool!=null )
                return tool;
        }
        return null;
    }

    /**
     *
     * @param toolID
     * @param category
     * @return 
     */
    private IAction searchCategoryForTool( String toolID, ToolCategory category ) {
        for( Iterator iter2 = category.iterator(); iter2.hasNext(); ) {
            ToolProxy tool = (ToolProxy) iter2.next();
            String id = tool.getId();
            if (id.equals(toolID))
                return tool.getAction();
        }
        return null;
    }

    /**
     * Returns the list of categories containing modal tools.
     * 
     * @return the list of categories containing modal tools.
     */
    public List<ModalToolCategory> getModalToolCategories() {
        return modalCategories;
    }
    
//    
//    public Tool getActiveModalTool(){
//    	
//    }

    /**
     * Returns the tool category that is currently active.
     * 
     * @return the tool category that is currently active.
     */
    public ToolCategory getActiveCategory() {
        return findModalCategory(activeModalToolProxy.getCategoryId());
    }

    private static class CategorySorter implements Comparator<String>, Serializable {

        /** long serialVersionUID field */
        private static final long serialVersionUID = 1L;
        private final static java.util.Map<String, Integer> values = new HashMap<String, Integer>();
        static {
            values.put(ToolConstants.RENDER_CA, 5);
            values.put(ToolConstants.ZOOM_CA, 4);
            values.put(ToolConstants.PAN_CA, 3);
            values.put(ToolConstants.SELECTION_CA, 2);
            values.put(ToolConstants.INFO_CA, 1);
            values.put(ToolConstants.EDIT_CA, 0);
        }

        private static final int max = -1;
        private static final int min = 1;
        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare( String arg0, String arg1 ) {
            Integer value0 = values.get(arg0);
            Integer value1 = values.get(arg1);
            if (value0 == null && value1 == null)
                return 0;
            if (value1 == null)
                return max;
            if (value0 == null )
                return min;
            if (value0.equals(value1))
                return 0;

            return value0.intValue() > value1.intValue() ? max : min;
        }

    }

    /**
     * This method enables the context that allows tool keybinding to work.
     * 
     * @param site
     */
    public void addToolScope( IWorkbenchPartSite site ) {
        String[] scopes = site.getKeyBindingService().getScopes();
        String[] newScopes = new String[scopes.length + 1];
        System.arraycopy(scopes, 0, newScopes, 1, scopes.length);
        newScopes[0] = "net.refractions.udig.project.ui.tool"; //$NON-NLS-1$
        site.getKeyBindingService().setScopes(newScopes);
    }

    /*
     * This allows for customized operation menus that are based on the currently selected tool.
     */
    public MenuManager createOperationsContextMenu(ISelection selection) {
        try {
            MenuManager contextManager = getOperationMenuFactory().createMenuManager();

            List<OperationCategory> primaryCategories = activeModalToolProxy.getOperationCategories();
            Collection<OperationCategory> secondaryCategories = getOperationMenuFactory()
                    .getCategories().values();

            for( int i = 0; i < primaryCategories.size(); i++ ) {
                OperationCategory category = primaryCategories.get(i);

                // Limit the size of the context menu to 20, but don't ever display a portion of
                // a category, only always the entire thing.
                if (contextManager.getItems().length >= 15 && category.getActions().size() > 5) {
                    break;
                }

                MenuManager menu = category.createContextMenu();

                if ((i != 0 && menu.getItems().length != 0)
                        && (secondaryCategories.size() != 0 || getOperationMenuFactory()
                                .getActions().size() != 0)) {

                    contextManager.add(new Separator());
                }

                for( IContributionItem item : menu.getItems() ) {
                    contextManager.add(item);
                }
            }

            // if primaryCategories are present, create an "Other" submenu
            if (contextManager.getItems().length != 0) {
                RunOperationsAction action = new RunOperationsAction();
                action.setText(Messages.ToolCategory_other); 

                contextManager.add(new Separator());
                contextManager.add(action);
            } else {
                Iterator iter = secondaryCategories.iterator();
                while( iter.hasNext() ) {
                    OperationCategory category = (OperationCategory) iter.next();

                    for( OpAction action : category.getActions() ) {
                        if( selection instanceof IStructuredSelection )
                            action.updateEnablement((IStructuredSelection) selection, true);
                        if (action.isEnabled())
                            contextManager.add(action);
                    }
                    if (iter.hasNext())
                        contextManager.add(new Separator());
                }

                if (getOperationMenuFactory().getActions().size() != 0) {
                    contextManager.add(new Separator());
                }
                for( OpAction action : getOperationMenuFactory().getActions() ) {
                    if( selection instanceof IStructuredSelection )
                        action.updateEnablement((IStructuredSelection) selection, true);
                    if (action.isEnabled()) {
                        contextManager.add(action);
                    }
                }
            }

            return contextManager;
        } catch (Throwable e) {
            ProjectUIPlugin.log("error creating the Operations ContextMenu", e);//$NON-NLS-1$
            return new MenuManager();
        }
    }

    private OperationMenuFactory getOperationMenuFactory() {
        return UiPlugin.getDefault().getOperationMenuFactory();
    }
    
    


    /**
     *  (non-Javadoc)
     * @see net.refractions.udig.project.ui.tool.IToolManager#getActiveTool()
     */
    public Tool getActiveTool() {
    	return activeModalToolProxy.getTool();
    }
    
    /**
     * Returns active tool proxy object.
     * 
     * @return
     */
    protected ToolProxy getActiveToolProxy(){
    	return activeModalToolProxy;
    }
    
    

//    
//    static class CopyAction extends Action {
//        final Set<Transfer> transfers = UDIGDragDropUtilities.getTransfers();
//        IWorkbenchPart part;
//        public IWorkbenchPart getPart() {
//            return part;
//        }
//        public void setPart( IWorkbenchPart part ) {
//            this.part = part;
//        }
//        @Override
//        public void runWithEvent( Event event ) {
//            Clipboard clipBoard = new Clipboard(event.display);
//            try {
//                IStructuredSelection selection = (IStructuredSelection) part.getSite()
//                        .getSelectionProvider().getSelection();
//                if (selection.isEmpty())
//                    return;
//                List<Object> data = new ArrayList<Object>();
//                List<Transfer> dataTransfers = new ArrayList<Transfer>();
//                Object[] testData = new Object[1];
//                Transfer[] testTransfer = new Transfer[1];
//                for( Iterator iter = selection.iterator(); iter.hasNext(); ) {
//                    Object element = iter.next();
//                    for( Transfer transfer : transfers ) {
//                        try {
//                            testData[0] = element;
//                            testTransfer[0] = transfer;
//                            clipBoard.setContents(testData, testTransfer);
//                        } catch (Exception e) {
//                            continue;
//                        }
//                        data.add(element);
//                        dataTransfers.add(transfer);
//                    }
//                }
//                clipBoard.setContents(data.toArray(), dataTransfers
//                        .toArray(new Transfer[dataTransfers.size()]));
//            } finally {
//                clipBoard.dispose();
//            }
//
//        }
//    }

    static class CopyAction extends Action {
        final Set<Transfer> transfers = UDIGDragDropUtilities.getTransfers();
        IWorkbenchPart part;
        public IWorkbenchPart getPart() {
            return part;
        }
        public void setPart( IWorkbenchPart part ) {
            this.part = part;
        }
        @Override
        public void runWithEvent( Event event ) {
            Clipboard clipBoard = new Clipboard(event.display);
            try {
                IMap map = ApplicationGIS.getActiveMap();
                if( map == ApplicationGIS.NO_MAP )
                    return;
                ILayer selectedLayer = map.getEditManager().getSelectedLayer();
                if( selectedLayer == null )
                    return;
                
                Filter layerFilter = selectedLayer.getFilter();
                if ( layerFilter==Filter.ALL ){
                    return;
                }
                AdaptingFilter filter = null;

                if( layerFilter instanceof AdaptingFilter){
                    AdaptingFilter adapting = (AdaptingFilter) layerFilter;
                    if( adapting.getAdapter(ILayer.class)!=null ){
                        filter = adapting;
                    }
                }
                
                if( filter == null ){
                    filter = new AdaptingFilter(layerFilter, selectedLayer);
                }
                clipBoard.setContents(new Object[]{filter}, 
                        new Transfer[]{UDigByteAndLocalTransfer.getInstance()});
            }finally {
                clipBoard.dispose();
            }

        }
    }
    
//    static class PasteAction extends Action {
//        IWorkbenchPart part;
//        public IWorkbenchPart getPart() {
//            return part;
//        }
//        public void setPart( IWorkbenchPart part ) {
//            this.part = part;
//        }
//        @Override
//        public void run() {
//            MapEditor activeEditor = ApplicationGISInternal.getActiveEditor();
//            Object targetObject = activeEditor;
//            IStructuredSelection selection = (IStructuredSelection) part.getSite()
//                    .getSelectionProvider().getSelection();
//            if (!selection.isEmpty()) {
//                targetObject = selection.iterator().next();
//
//            }
//            Clipboard clipboard = new Clipboard(activeEditor.getSite().getShell().getDisplay());
//            Set<Transfer> transfers = UDIGDNDProcessor.getTransfers();
//            Object contents = null;
//            for( Transfer transfer : transfers ) {
//                contents = clipboard.getContents(transfer);
//                if (contents != null)
//                    break;
//            }
//            if (contents != null) {
//                UDIGDropHandler dropHandler = activeEditor.getDropHandler();
//                dropHandler.setTarget(targetObject);
//                dropHandler.setViewerLocation(ViewerDropLocation.NONE);
//                dropHandler.performDrop(contents, null);
//            }
//        }
//    }

    static class PasteAction extends Action {
        IWorkbenchPart part;
        public IWorkbenchPart getPart() {
            return part;
        }
        public void setPart( IWorkbenchPart part ) {
            this.part = part;
        }
        @Override
        public void run() {
            Clipboard clipboard = new Clipboard(part.getSite().getShell().getDisplay());
            Set<Transfer> transfers = UDIGDNDProcessor.getTransfers();
            Object contents = null;
            for( Transfer transfer : transfers ) {
                contents = clipboard.getContents(transfer);
                if (contents != null)
                    break;
            }
            
            Object selection = firstSelectedElement();

            if (contents != null) {
                MapEditor activeEditor = ApplicationGISInternal.getActiveEditor();
                final Map finalMap;
                final UDIGDropHandler finalDropHandler;
                if( selection instanceof Map){
                    finalMap = (Map)selection;
                    finalDropHandler = new UDIGDropHandler();
                    activeEditor=null;
                } else if( activeEditor==null ){
                    CreateMapCommand command = new CreateMapCommand(null,Collections.<IGeoResource>emptyList(), null);
                    try {
                        command.run(new NullProgressMonitor());
                    } catch (Exception e) {
                        throw (RuntimeException) new RuntimeException( ).initCause( e );
                    }
                    finalMap = (Map) command.getCreatedMap();
                    finalDropHandler = new UDIGDropHandler();
                }else{
                    finalDropHandler = activeEditor.getDropHandler();
                    finalMap = activeEditor.getMap();
                }
                
                final MapEditor finalActiveEditor = activeEditor;
                ILayer selectedLayer = finalMap.getEditManager().getSelectedLayer();
                if( selectedLayer==null ){
                    finalDropHandler.setTarget(finalMap);
                }else{
                    finalDropHandler.setTarget(selectedLayer);
                }
                finalDropHandler.addListener(new IDropHandlerListener(){

                    public void done( IDropAction action, Throwable error ) {

                        if( finalActiveEditor==null && finalMap.getMapLayers().size()==0 ){
                            finalMap.getProjectInternal().getElementsInternal().remove(finalMap);
                        }
                        
                        finalDropHandler.removeListener(this);
                    }

                    public void noAction( Object data ) {
                        if( finalActiveEditor==null && finalMap.getMapLayers().size()==0 ){
                            finalMap.getProjectInternal().getElementsInternal().remove(finalMap);
                        }
                        finalDropHandler.removeListener(this);
                    }

                    public void starting( IDropAction action ) {
                    }
                    
                });
                finalDropHandler.setViewerLocation(ViewerDropLocation.ON);
                finalDropHandler.performDrop(contents, null);
                
            }
        }
        private Object firstSelectedElement() {
            ISelection selection = part.getSite().getSelectionProvider().getSelection();
            if( selection.isEmpty() || !(selection instanceof IStructuredSelection)){
                return null;
            }else{
                return ((IStructuredSelection)selection).getFirstElement();
            }
        }
    }

    public List<ActionToolCategory> getActiveToolCategories() {
        return actionCategories;
    }

}
