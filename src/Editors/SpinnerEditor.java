package Editors;

import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class SpinnerEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	 private static final long serialVersionUID = 1L;
	    private JSpinner editSpinner, renderSpinner;
	    private JTable table;
	    
	public SpinnerEditor(JTable table, int column) {
		SpinnerModel spinnerModel = new SpinnerNumberModel(10, 1, 30, 1);
        editSpinner = new JSpinner(spinnerModel);
        renderSpinner = new JSpinner(spinnerModel);
        this.table = table;
        table.getColumnModel().getColumn(column).setCellEditor(this);
    }

	@Override
	public Object getCellEditorValue() {
		return editSpinner.getValue();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {  
		if (value == null) {
	     renderSpinner.setValue(1);
	  } else {
		  int intValue = ((Integer) value).intValue();
		  renderSpinner.setValue(intValue);
		  }
		return renderSpinner;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value != null) {
			editSpinner.setValue(value);
			}
		return editSpinner;
	}
}
