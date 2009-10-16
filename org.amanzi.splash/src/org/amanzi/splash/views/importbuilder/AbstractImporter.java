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

package org.amanzi.splash.views.importbuilder;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.amanzi.integrator.awe.AWEProjectManager;
import org.amanzi.neo.core.NeoCorePlugin;
import org.amanzi.neo.core.database.nodes.AweProjectNode;
import org.amanzi.neo.core.database.nodes.CellID;
import org.amanzi.neo.core.database.nodes.CellNode;
import org.amanzi.neo.core.database.nodes.ColumnNode;
import org.amanzi.neo.core.database.nodes.RowNode;
import org.amanzi.neo.core.database.nodes.RubyProjectNode;
import org.amanzi.neo.core.database.nodes.SplashFormatNode;
import org.amanzi.neo.core.database.nodes.SpreadsheetNode;
import org.amanzi.neo.core.database.services.AweProjectService;
import org.amanzi.neo.core.service.NeoServiceProvider;
import org.amanzi.splash.database.services.SpreadsheetService;
import org.amanzi.splash.swing.Cell;
import org.amanzi.splash.ui.SplashPlugin;
import org.amanzi.splash.utilities.NeoSplashUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Transaction;

import com.eteks.openjeks.format.CellFormat;


/**
 * Abstract class that provides basic functionality for importing data into Splash
 * 
 * @author Lagutko_N
 * @since 1.0.0
 */
public abstract class AbstractImporter implements IRunnableWithProgress {
    
    /**
     * ArrayList that provides two ways to get Column
     * 1. If we know a number of columns than we can use it as a standard ArrayList.
     * 2. If we didn't know a number of columns than a if a Column by index is not found 
     *    than this column will be created and stored in ArrayList.
     * 
     * @author Lagutko_N
     * @since 1.0.0
     */
    private class ColumnArrayList extends ArrayList<ColumnNode> {
        
        /** long serialVersionUID field */
        private static final long serialVersionUID = 1L;
        
        public ColumnArrayList(int size) {
            super(size);
        }
        
        @Override
        public ColumnNode get(int i) {
            boolean notExists = false;
            ColumnNode result = null;
            
            try {
                result = super.get(i);                
            }
            catch (IndexOutOfBoundsException e) {
                notExists = true;
            }
            
            if ((result == null) && notExists) {
                CellID id = new CellID(0, i);
                
                result = createColumnNode(id.getColumnName());
                super.add(i, result);
            }
            
            return result;
        }
    }
    
    /*
     * Path to Project that will contain new Spreadsheet
     */
    protected IPath containerPath;
    
    /*
     * FileName of file to Import
     */
    protected String fileName;
    
    /*
     * Content of file to import
     */
    protected InputStream fileContent;
    
    /*
     * Project Service
     */
    private AweProjectService projectService;
    
    /*
     * Neo Service
     */
    private NeoService neoService;
    
    /*
     * List of Columns
     */
    private ColumnArrayList columns = new ColumnArrayList(0);
    
    /*
     * Current row
     */
    private RowNode currentRow;
    
    /*
     * CellFormat object
     */
    private CellFormat defaultFormat = new CellFormat();
    
    /*
     * Spreadsheet Service
     */
    private SpreadsheetService spreadsheetService;
    
    /*
     * Node of created Spreadsheet
     */
    private SpreadsheetNode spreadsheetNode;
    
    /*
     * Size of File
     */
    protected long fileSize;
    
    /**
     * Constructor 
     * 
     * @param containerPath path to Project that will contain new Spreadsheet
     * @param fileName name of File to import
     * @param stream content of File to import
     * @param fileSize size of File to import
     */
    public AbstractImporter(IPath containerPath, String fileName, InputStream stream, long fileSize) {
        this.containerPath = containerPath;
        this.fileName = fileName;
        this.fileContent = stream;
        this.fileSize = fileSize;
        
        //initializing services
        projectService = NeoCorePlugin.getDefault().getProjectService();
        neoService = NeoServiceProvider.getProvider().getService();
        spreadsheetService = SplashPlugin.getDefault().getSpreadsheetService();
    }
    
    @Override
    public abstract void run(IProgressMonitor monitor) throws InvocationTargetException;
    
    /**
     * Returns created Spreadsheet
     *
     * @return created spreadsheet
     */
    public SpreadsheetNode getSpreadsheet() {
        return spreadsheetNode;
    }
    
    /**
     *  Creates a Spreadsheet 
     */
    protected void createSpreadsheet() {
        //computing names of Ruby and AWE projects
        IProject rubyProject = (IProject)ResourcesPlugin.getWorkspace().getRoot().findMember(containerPath);
        
        String rubyProjectName = rubyProject.getName();        
        String aweProjectName = AWEProjectManager.getAWEprojectNameFromResource(rubyProject);
        
        //computing nodes for Ruby and AWE projects
        AweProjectNode aweProjectNode = projectService.findOrCreateAweProject(aweProjectName);
        RubyProjectNode rubyProjectNode = projectService.findOrCreateRubyProject(aweProjectNode, rubyProjectName);
        
        //computing name of Spreadsheet
        String spreadsheetName = getSpreadsheetName();
        
        //validate name of Spreadsheet. If for example spreadsheet with name 'sheet' already exists
        //than it will try name 'sheet1', 'sheet2' etc.
        int i = 1;        
        String newSpreadsheetName = spreadsheetName;
        do {            
            spreadsheetName = newSpreadsheetName;
            spreadsheetNode = projectService.findSpreadsheet(rubyProjectNode, spreadsheetName);
            newSpreadsheetName = spreadsheetName.concat(Integer.toString(i++));
        } while (spreadsheetNode != null);
        
        //create a new Spreadsheet
        spreadsheetNode = projectService.findOrCreateSpreadSheet(rubyProjectNode, spreadsheetName);
        
        //also create a Spreadsheet in AWE Project Structure
        try {
            AWEProjectManager.createNeoSpreadsheet(rubyProject, spreadsheetName, NeoSplashUtil.getSpeadsheetURL(spreadsheetName));
        }
        catch (MalformedURLException e) {
            //can't happen
        }
    }
    
    /**
     * Convert name of File to Import to name of Spreadsheet
     *
     * @return name of Spreadsheet
     */
    private String getSpreadsheetName() {
        //if FileName contains extension than name of spreadsheet is a filename without extension
        int pointIndex = fileName.indexOf(".");
        if (pointIndex > 0) {
            return fileName.substring(0, pointIndex);
        }
        else {
            return fileName;
        }
    }
    
    /**
     * Saves a Cell
     *
     * @param cell cell to save
     */
    protected void saveCell(Cell cell) {
        int column = cell.getColumn();

        if (column == 0) {
            //if column is zero than it's a new row
            currentRow = null;
        }
        
        //import Cell to database in given Row and Column
        RowNode newRow = importCell(cell, currentRow, columns.get(cell.getColumn()));
        if (column == 0) {
            //if column was zero than a new Row was created
            currentRow = newRow;
        }
    }
    
    /**
     * Updates Transaction.
     * To prevent JavaHeapSpace exception we can call this method for example after processing 1000 rows
     *
     * @param transaction transaction to update
     * @return new transaction
     */
    protected Transaction updateTransaction(Transaction transaction) {
        transaction.success();
        transaction.finish();
        
        return neoService.beginTx();
    }
    
    /**
     * Import Cell to database
     *
     * @param cell cell to import
     * @param row row of Cell
     * @param column column of Cell
     * @return row that contain imported cell or null
     */
    private RowNode importCell(Cell cell, RowNode row, ColumnNode column) {
        if (spreadsheetNode == null) {
            createSpreadsheet();
        }
        
        if (row == null) {
            //if row is null than it should be a new row
            row = new RowNode(neoService.createNode(), cell.getCellID().getRowName());
            spreadsheetNode.addRow(row);
        }
        
        //create a new cell
        CellNode cellNode = new CellNode(neoService.createNode());
        //set a value to cell
        cellNode.setValue(cell.getValue());
        cellNode.setDefinition(cell.getDefinition());
        
        //create a format node for Cell
        SplashFormatNode formatNode = new SplashFormatNode(neoService.createNode());
        spreadsheetService.setSplashFormat(formatNode, defaultFormat);
        formatNode.addCell(cellNode);
        
        //add a cell to row and column
        row.addCell(cellNode);
        column.addCell(cellNode);
        
        return row;
    }
    
    /**
     * Creates a Column Node 
     *
     * @param name name of Column
     * @return created ColumnNode
     */
    private ColumnNode createColumnNode(String name) {
        if (spreadsheetNode == null) {
            createSpreadsheet();
        }
        
        ColumnNode node = new ColumnNode(neoService.createNode(), name);
        
        spreadsheetNode.addColumn(node);
        
        return node;
    }
    
    
}
