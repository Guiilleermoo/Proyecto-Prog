package Editors;

import java.awt.Component;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import es.deusto.prog.III.BD.GestorBD;

public class TallaEditor extends AbstractCellEditor implements TableCellEditor {
	private JComboBox<String> comboBoxTalla = new JComboBox<>();
	 private GestorBD gestorBD;
	 
	 public TallaEditor(GestorBD gestorBD) {
		 this.gestorBD = gestorBD;
	 }
	 
	@Override
	public Object getCellEditorValue() {
		return comboBoxTalla.getSelectedItem();
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		String articulo = (String) table.getValueAt(row, 0);
		String deporte = (String) table.getValueAt(row, 1);
		String marca = (String) table.getValueAt(row, 2);
		String genero = (String) table.getValueAt(row, 3).toString();

		String[] tallas = gestorBD.obtenerTalla(articulo, deporte, marca, genero);
		
		List<String> tallasLista = Arrays.asList(tallas);
		
		comboBoxTalla.removeAllItems();
		for (String talla : tallasLista) {
			comboBoxTalla.addItem(talla);
		}
		comboBoxTalla.setSelectedItem(value);
		return comboBoxTalla;
	}
}
