package Editors;

import java.awt.Component;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import es.deusto.prog.III.Producto.Genero;
import es.deusto.prog.III.BD.GestorBD;

public class GeneroEditor extends AbstractCellEditor implements TableCellEditor {
	 private JComboBox<String> comboBoxGenero = new JComboBox<>();
	 private GestorBD gestorBD;
	 
	 public GeneroEditor(GestorBD gestorBD) {
		 this.gestorBD = gestorBD;
	 }
	 
	@Override
	public Object getCellEditorValue() {
		return comboBoxGenero.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		String articulo = (String) table.getValueAt(row, 0);
		String deporte = (String) table.getValueAt(row, 1);
		String marca = (String) table.getValueAt(row, 2);
		String[] generos = gestorBD.obtenerGenero(articulo, deporte, marca);
		
		List<String> generosLista = Arrays.asList(generos);
		
		comboBoxGenero.removeAllItems();
		
		for (String genero : generosLista) {
			comboBoxGenero.addItem(genero.toUpperCase());
		}
		comboBoxGenero.setSelectedItem(value);
		
		return comboBoxGenero;
	}
}