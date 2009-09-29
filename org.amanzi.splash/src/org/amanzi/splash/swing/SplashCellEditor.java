package org.amanzi.splash.swing;import java.awt.Component;import java.awt.event.ActionEvent;import javax.swing.DefaultCellEditor;import javax.swing.JTable;import javax.swing.JTextField;public class SplashCellEditor extends DefaultCellEditor {	/**	 * 	 */	private static final long serialVersionUID = -2031687089150606947L;	private JTable table;	private Object cellValue;	private String oldFormula;		private int cellRow;		private int cellColumn;	public SplashCellEditor() {		super(new JTextField()); // No defaut super constructor		final JTextField textField = new JTextField();		editorComponent = textField;		// Reassign an other delegate that checks stopCellEditing ()		// before firing stop event		delegate = new EditorDelegate() {			/**			 * 			 */			private static final long serialVersionUID = -4282468453693627605L;			public void setValue(Object value) {				textField.setText((value != null) ? value.toString() : "");			}			public Object getCellEditorValue() {				return textField.getText();			}			public void actionPerformed(ActionEvent event) {				if (SplashCellEditor.this.stopCellEditing())					super.actionPerformed(event);			}		};		textField.addActionListener(delegate);		// this.parser = parser;	}	/**	 * Returns the table model value edited by this editor.	 */	public Object getCellEditorValue() {		return cellValue;	}	/**	 * Quick utility for Obtaining casted model value	 * 	 * @return	 */	private SplashTableModel getModel() {		return (SplashTableModel) table.getModel();	}	/**	 * Handle stop cell editing event when user presses ENTER after editing a	 * cell	 */	public boolean stopCellEditing() {		String newFormula = (String) super.getCellEditorValue();				cellValue = getModel().interpret(newFormula, oldFormula, cellRow, cellColumn);		return super.stopCellEditing();	}	/**	 * fired when user starts editing a cell	 * 	 */	public Component getTableCellEditorComponent(JTable table, Object value,			boolean isSelected, int row, int column) {		cellValue = value;				cellRow = row;		cellColumn = column;		value = (String) ((Cell) value).getDefinition();		if (value != null) {			// check on value type			if (value instanceof Cell)				oldFormula = (String) ((Cell) value).getDefinition();			if (value instanceof String)				oldFormula = value.toString();		}		this.table = table;		final JTextField c = (JTextField) super.getTableCellEditorComponent(				table, value, // edit the text field of Cell				isSelected, row, column);		c.selectAll(); // automatically select the whole string in the cell		return c;		// return super.getTableCellEditorComponent (table, value, isSelected,		// row, column);	}}