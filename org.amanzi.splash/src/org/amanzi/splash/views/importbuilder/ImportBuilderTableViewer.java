
package org.amanzi.splash.views.importbuilder;
import java.util.Arrays;


import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;



public class ImportBuilderTableViewer {
/**
	 * @param parent
	 */
	public ImportBuilderTableViewer(Composite parent) {
		
		this.addChildControls(parent);
	}

	//	private Shell shell;
	private Table table;
	private TableViewer tableViewer;
	private Button runButton;
	
	// Create a ImportBuilderFilterList and assign it to an instance variable
	private ImportBuilderFilterList filtersList = new ImportBuilderFilterList(); 

	// Set the table column property names
	
	private final String FILTER_HEADING_COLUMN 			= "Filter Heading";
	private final String FILTER_TEXT_COLUMN 	= "Filter Text";
	private final String FILTER_FILE_NAME 	= "Filter Filename";
	

	// Set column names
	private String[] columnNames = new String[] { 
			//COMPLETED_COLUMN, 
			FILTER_HEADING_COLUMN,
			FILTER_TEXT_COLUMN,
			FILTER_FILE_NAME
			};

	/**
	 * Main method to launch the window 
	 */
	public static void main(String[] args) {

		Shell shell = new Shell();
		shell.setText("Import Builder");

		// Set layout for shell
		GridLayout layout = new GridLayout();
		shell.setLayout(layout);
		
		// Create a composite to hold the children
		Composite composite = new Composite(shell, SWT.NONE);
		final ImportBuilderTableViewer tableViewerExample = new ImportBuilderTableViewer(composite);
		
		tableViewerExample.getControl().addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				tableViewerExample.dispose();			
			}
			
		});

		// Ask the shell to display its content
		shell.open();
		tableViewerExample.run(shell);
	}

	/**
	 * Run and wait for a close event
	 * @param shell Instance of Shell
	 */
	private void run(Shell shell) {
		
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Release resources
	 */
	public void dispose() {
		
		// Tell the label provider to release its ressources
		tableViewer.getLabelProvider().dispose();
	}

	/**
	 * Create a new shell, add the widgets, open the shell
	 * @return the shell that was created	 
	 */
	private void addChildControls(Composite composite) {

		// Create a composite to hold the children
		GridData gridData = new GridData (GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH);
		composite.setLayoutData (gridData);

		// Set numColumns to 3 for the buttons 
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 4;
		composite.setLayout (layout);

		// Create the table 
		createTable(composite);
		
		// Create and setup the TableViewer
		createTableViewer();
		tableViewer.setContentProvider(new ExampleContentProvider());
		tableViewer.setLabelProvider(new ImportBuilderLabelProvider());
		// The input for the table viewer is the instance of ImportBuilderFilterList
		filtersList = new ImportBuilderFilterList();
		tableViewer.setInput(filtersList);

		// Add the buttons
		createButtons(composite);
	}

	/**
	 * Create the Table
	 */
	private void createTable(Composite parent) {
		int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | 
					SWT.FULL_SELECTION | SWT.HIDE_SELECTION;

		final int NUMBER_COLUMNS = columnNames.length;

		table = new Table(parent, style);
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 3;
		table.setLayoutData(gridData);		
					
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		// 1st column with image/checkboxes - NOTE: The SWT.CENTER has no effect!!
		TableColumn column;// = new TableColumn(table, SWT.CENTER, 0);		
		//column.setText("!");
		//column.setWidth(20);
		
		// 3rd column with task Owner
		column = new TableColumn(table, SWT.LEFT, 0);
		column.setText(columnNames[0]);
		column.setWidth(100);
		// Add listener to column so tasks are sorted by owner when clicked
		column.addSelectionListener(new SelectionAdapter() {
       	
			public void widgetSelected(SelectionEvent e) {
				//tableViewer.setSorter(new ImportBuilderFilterSorter(ImportBuilderFilterSorter.OWNER));
			}
		});
		
		// 2nd column with task Description
		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText(columnNames[1]);
		column.setWidth(400);
		// Add listener to column so tasks are sorted by description when clicked 
		column.addSelectionListener(new SelectionAdapter() {
       	
			public void widgetSelected(SelectionEvent e) {
				//tableViewer.setSorter(new ImportBuilderFilterSorter(ImportBuilderFilterSorter.DESCRIPTION));
			}
		});

		// 2nd column with task Description
		column = new TableColumn(table, SWT.LEFT, 2);
		column.setText(columnNames[2]);
		column.setWidth(200);
		// Add listener to column so tasks are sorted by description when clicked 
		column.addSelectionListener(new SelectionAdapter() {
       	
			public void widgetSelected(SelectionEvent e) {
				//tableViewer.setSorter(new ImportBuilderFilterSorter(ImportBuilderFilterSorter.DESCRIPTION));
			}
		});
	}

	/**
	 * Create the TableViewer 
	 */
	private void createTableViewer() {

		tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);
		
		tableViewer.setColumnProperties(columnNames);

		// Create the cell editors
		CellEditor[] editors = new CellEditor[columnNames.length];

		// Column 2 : Description (Free text)
		TextCellEditor textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).setTextLimit(60);
		editors[1] = textEditor;

		// Column 3 : Owner (Combo Box) 
		editors[0] = new ComboBoxCellEditor(table, filtersList.getHeadingsList(), SWT.READ_ONLY);

		// Column 4 : Percent complete (Text with digits only)
		textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).addVerifyListener(
		
			new VerifyListener() {
				public void verifyText(VerifyEvent e) {
					// Here, we could use a RegExp such as the following 
					// if using JRE1.4 such as  e.doit = e.text.matches("[\\-0-9]*");
					e.doit = "0123456789".indexOf(e.text) >= 0 ;
				}
			});
		// Column 2 : Description (Free text)
		TextCellEditor textEditor1 = new TextCellEditor(table);
		((Text) textEditor1.getControl()).setTextLimit(60);
		editors[2] = textEditor1;

		
		// Assign the cell editors to the viewer 
		tableViewer.setCellEditors(editors);
		// Set the cell modifier for the viewer
		tableViewer.setCellModifier(new ImportBuilderCellModifier(this));
		// Set the default sorter for the viewer 
		//tableViewer.setSorter(new ImportBuilderFilterSorter(ImportBuilderFilterSorter.DESCRIPTION));
	}

	/*
	 * Close the window and dispose of resources
	 */
	public void close() {
		Shell shell = table.getShell();

		if (shell != null && !shell.isDisposed())
			shell.dispose();
	}


	/**
	 * InnerClass that acts as a proxy for the ImportBuilderFilterList 
	 * providing content for the Table. It implements the IImportBuilderFilterListViewer 
	 * interface since it must register changeListeners with the 
	 * ImportBuilderFilterList 
	 */
	class ExampleContentProvider implements IStructuredContentProvider, IImportBuilderFilterListViewer {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			if (newInput != null)
				((ImportBuilderFilterList) newInput).addChangeListener(this);
			if (oldInput != null)
				((ImportBuilderFilterList) oldInput).removeChangeListener(this);
		}

		public void dispose() {
			filtersList.removeChangeListener(this);
		}

		// Return the tasks as an array of Objects
		public Object[] getElements(Object parent) {
			return filtersList.getFilters().toArray();
		}

		/* (non-Javadoc)
		 * @see IImportBuilderFilterListViewer#addTask(ImportBuilderFilter)
		 */
		public void addFilter(ImportBuilderFilter task) {
			tableViewer.add(task);
		}

		/* (non-Javadoc)
		 * @see IImportBuilderFilterListViewer#removeTask(ImportBuilderFilter)
		 */
		public void removeFilter(ImportBuilderFilter task) {
			tableViewer.remove(task);			
		}

		/* (non-Javadoc)
		 * @see IImportBuilderFilterListViewer#updateTask(ImportBuilderFilter)
		 */
		public void updateFilter(ImportBuilderFilter task) {
			tableViewer.update(task, null);	
		}
	}
	
	/**
	 * Return the array of choices for a multiple choice cell
	 */
	public String[] getChoices(String property) {
		if (FILTER_HEADING_COLUMN.equals(property))
			return filtersList.getHeadingsList();  // The ImportBuilderFilterList knows about the choice of owners
		else
			return new String[]{};
	}

	/**
	 * Add the "Add", "Delete" and "Close" buttons
	 * @param parent the parent composite
	 */
	private void createButtons(Composite parent) {
		
		// Create and configure the "Add" button
		Button add = new Button(parent, SWT.PUSH | SWT.CENTER);
		add.setText("Add");
		
		GridData gridData = new GridData (GridData.HORIZONTAL_ALIGN_BEGINNING);
		gridData.widthHint = 80;
		add.setLayoutData(gridData);
		add.addSelectionListener(new SelectionAdapter() {
       	
       		// Add a task to the ImportBuilderFilterList and refresh the view
			public void widgetSelected(SelectionEvent e) {
				filtersList.addFilter();
			}
		});

		//	Create and configure the "Delete" button
		Button delete = new Button(parent, SWT.PUSH | SWT.CENTER);
		delete.setText("Delete");
		gridData = new GridData (GridData.HORIZONTAL_ALIGN_BEGINNING);
		gridData.widthHint = 80; 
		delete.setLayoutData(gridData); 

		delete.addSelectionListener(new SelectionAdapter() {
       	
			//	Remove the selection and refresh the view
			public void widgetSelected(SelectionEvent e) {
				ImportBuilderFilter task = (ImportBuilderFilter) ((IStructuredSelection) 
						tableViewer.getSelection()).getFirstElement();
				if (task != null) {
					filtersList.removeFilter(task);
				} 				
			}
		});
		
//		Create and configure the "Run" button
//		Button run = new Button(parent, SWT.PUSH | SWT.CENTER);
//		delete.setText("Run");
//		gridData = new GridData (GridData.HORIZONTAL_ALIGN_END);
//		gridData.widthHint = 80; 
//		delete.setLayoutData(gridData); 
//
//		delete.addSelectionListener(new SelectionAdapter() {
//       	
//			//	Remove the selection and refresh the view
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});

		
		//	Create and configure the "Close" button
		runButton = new Button(parent, SWT.PUSH | SWT.CENTER);
		runButton.setText("Run");
		gridData = new GridData (GridData.HORIZONTAL_ALIGN_END);
		gridData.widthHint = 80; 
		runButton.setLayoutData(gridData); 	
		runButton.addSelectionListener(new SelectionAdapter() {
	       	
			//	Remove the selection and refresh the view
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
	}

	/**
	 * Return the column names in a collection
	 * 
	 * @return List  containing column names
	 */
	public java.util.List getColumnNames() {
		return Arrays.asList(columnNames);
	}

	/**
	 * @return currently selected item
	 */
	public ISelection getSelection() {
		return tableViewer.getSelection();
	}

	/**
	 * Return the ImportBuilderFilterList
	 */
	public ImportBuilderFilterList getFiltersList() {
		return filtersList;	
	}

	/**
	 * Return the parent composite
	 */
	public Control getControl() {
		return table.getParent();
	}

	/**
	 * Return the 'close' Button
	 */
	public Button getCloseButton() {
		return runButton;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public void setTableViewer(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}
}