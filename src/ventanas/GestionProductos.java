package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import es.deusto.prog.III.Producto;
import es.deusto.prog.III.BD.GestorBD;
import es.deusto.prog.III.Producto.Genero;

public class GestionProductos extends JFrame {
		protected GestorBD gestorBD;
		protected int filaseleccionada;
		
		private List<Producto> productos;
		
		protected String gmail;
		protected String contrasena;
		
		protected JTable tablaProductos;
		protected DefaultTableModel modeloDatosProductos;
		protected JScrollPane scrollPaneProductos;
		
		protected JTextField txtArticulo;
		protected JTextField txtDeporte;
		protected JTextField txtMarca;
		protected JComboBox<Genero> genero;
		protected JTextField txtTalla;
		protected JSpinner precio;
		
		
	public GestionProductos(GestorBD gestorBD, String gmail, String contrasena) {
		this.gestorBD = gestorBD;
		this.gmail = gmail;
		this.contrasena = contrasena;
		
		Container cp = this.getContentPane();

		JPanel panel = new JPanel();
		cp.add(panel);
		
		panel.setLayout(new GridLayout(1,2));
		
		tablaProductos = new JTable(modeloDatosProductos);
		
		this.initTables();
		
		this.loadProductos();
		
		JScrollPane scrollPaneProductos = new JScrollPane(this.tablaProductos);
		scrollPaneProductos.setBorder(new TitledBorder("Productos"));
		this.tablaProductos.setFillsViewportHeight(true);
		panel.add(scrollPaneProductos, BorderLayout.WEST);
		
		JPanel derecha = new JPanel();
		panel.add(derecha, BorderLayout.EAST);
		derecha.setLayout(new GridLayout(3, 0));
		
		JPanel arriba = new JPanel();
		derecha.add(arriba);
		arriba.setLayout(new GridLayout(3, 4));
		
		JLabel labelArticulo = new JLabel("Articulo:");
		arriba.add(labelArticulo);
		
		txtArticulo = new JTextField();
		arriba.add(txtArticulo);
		txtArticulo.setColumns(10);
		
		JLabel labelDeporte = new JLabel("Deporte:");
		arriba.add(labelDeporte);
		
		txtDeporte = new JTextField();
		arriba.add(txtDeporte);
		txtDeporte.setColumns(10);
		
		JLabel labelMarca = new JLabel("Marca:");
		arriba.add(labelMarca);
		
		txtMarca = new JTextField();
		arriba.add(txtMarca);
		txtMarca.setColumns(10);
		
		JLabel labelGenero = new JLabel("Genero:");
		arriba.add(labelGenero);
		
		genero = new JComboBox<Producto.Genero>(Genero.values());
		arriba.add(genero);
		
		JLabel labelTalla = new JLabel("Talla:");
		arriba.add(labelTalla);
		
		txtTalla = new JTextField();
		arriba.add(txtTalla);
		txtTalla.setColumns(10);
		
		JLabel labelPrecio = new JLabel("Precio:");
		arriba.add(labelPrecio);
		
		SpinnerModel modelPrecio = new SpinnerNumberModel(30.0, 1.0, 300.0, 0.5);
		precio = new JSpinner(modelPrecio);
		arriba.add(precio);
		
		JPanel medio = new JPanel();
		derecha.add(medio);
		
		JButton BotonAnadir = new JButton("Anadir Producto");
		medio.add(BotonAnadir);
		
		JButton BotonBorrar = new JButton("Borrar Producto");
		medio.add(BotonBorrar);

		JPanel abajo = new JPanel();
		derecha.add(abajo);
		
		SpinnerModel spinnerModel = new SpinnerNumberModel(10, 1, 100, 1);
		JSpinner spinnerStock = new JSpinner(spinnerModel);
		abajo.add(spinnerStock);
		
		JButton BotonStock = new JButton("Anadir Stock");
		abajo.add(BotonStock);
		
		BotonAnadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Producto p = new Producto();
				
				p.setArticulo(txtArticulo.getText());
				p.setDeporte(txtDeporte.getText());
				p.setMarca(txtMarca.getText());
				p.setGenero((Genero) genero.getSelectedItem());
				p.setTalla(txtTalla.getText());
				p.setPrecio((double) precio.getValue());
				p.setCantidad(0);
				
				if (txtArticulo.getText().isEmpty()) {
					gestorBD.log(Level.SEVERE, "Error: campo Articulo vacio", null);
				} else if (txtDeporte.getText().isEmpty()) {
					gestorBD.log(Level.SEVERE, "Error: campo Deporte vacio", null);
				} else if (txtMarca.getText().isEmpty()) {
					gestorBD.log(Level.SEVERE, "Error: campo Marca vacio", null);
				} else if (txtTalla.getText().isEmpty()) {
					gestorBD.log(Level.SEVERE, "Error: campo Talla vacio", null);
				} else {
					gestorBD.insertarProducto(p);
					
					int id = gestorBD.getLastId();
					modeloDatosProductos.addRow(new Object[] {id, p.getArticulo(), p.getDeporte(), p.getMarca(), p.getGenero(), p.getTalla(), p.getPrecio(), p.getCantidad()});
					
					limpiar();
				}
			}
		});
		
		BotonBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(filaseleccionada >= 0) {
					int id = (int) tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0);
					gestorBD.borrarProducto(id);
					
					modeloDatosProductos.removeRow(tablaProductos.getSelectedRow());
					tablaProductos.repaint();
				} else {
					gestorBD.log(Level.SEVERE, "Error: seleccione una fila para borrar un producto", null);
				}
				
			}
		});
		
		BotonStock.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = (int) tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0);
				gestorBD.actualizarStock(id, (int) spinnerStock.getValue());
				
				loadProductos();
			}
		});
		
		TableCellRenderer renderStock = (table, value, isSelected, hasFocus, row, column) -> {

		JLabel label = new JLabel(value.toString());

			if (Integer.parseInt(table.getValueAt(row, 7).toString()) == 0) {
				label.setBackground(Color.RED);
			} else {
				label.setBackground(table.getBackground());
			}
			
		if (isSelected) {
			label.setBackground(table.getSelectionBackground());
		}
		
		label.setOpaque(true);

		return label;
		};
		
		this.tablaProductos.getColumnModel().getColumn(0).setCellRenderer(renderStock);
		this.tablaProductos.getColumnModel().getColumn(1).setCellRenderer(renderStock);
		this.tablaProductos.getColumnModel().getColumn(2).setCellRenderer(renderStock);
		this.tablaProductos.getColumnModel().getColumn(3).setCellRenderer(renderStock);
		this.tablaProductos.getColumnModel().getColumn(4).setCellRenderer(renderStock);
		this.tablaProductos.getColumnModel().getColumn(5).setCellRenderer(renderStock);
		this.tablaProductos.getColumnModel().getColumn(6).setCellRenderer(renderStock);
		this.tablaProductos.getColumnModel().getColumn(7).setCellRenderer(renderStock);
		
		this.setTitle("Gestion Productos");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}

	public void selectRows(String selectStr) {
		gestorBD.log(Level.INFO, "Seleccionando filas de articulo", null);
	}

	public void initTables() {
		Vector<String> cabeceraProductos = new Vector<String>(Arrays.asList( "ID", "ARTICULO", "DEPORTE", "MARCA", "GENERO", "TALLA", "PRECIO", "CANTIDAD"));
		
		this.modeloDatosProductos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraProductos) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 6) {
					return true;
				}
				return false;
			}
		};
		
		tablaProductos = new JTable(this.modeloDatosProductos);	
	}
	
	public void loadProductos() {
		this.productos = gestorBD.obtenerProductos();
		
		//Se borran los datos del modelo de datos
		this.modeloDatosProductos.setRowCount(0);
		
		// Se anade al modelo una fila de datos por cada comic
		for (Producto p : this.productos) {
			this.modeloDatosProductos.addRow( new Object[] {p.getId(), p.getArticulo(), p.getDeporte(), p.getMarca(), p.getGenero(), p.getTalla(), p.getPrecio(), p.getCantidad()} );
		}	

	}
	private void limpiar() {
		txtArticulo.setText("");
		txtDeporte.setText("");
		txtMarca.setText("");
		txtTalla.setText("");
	}
}
