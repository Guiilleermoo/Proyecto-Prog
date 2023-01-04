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
		private JTextField txtArticulo;
		private JTextField txtDeporte;
		private JTextField txtMarca;
		private JTextField txtGenero;
		private JTextField txtTalla;
		private JTextField txtPrecio;
		
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
		
		JTextField deporteText = new JTextField();
		
		
		JButton botonInsertarproducto = new JButton("Insertar Producto");
		SpinnerModel spinner = new SpinnerNumberModel(10, 0 , 100, 1);
		
		panel.add(derecha, BorderLayout.EAST);
		derecha.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel arriba = new JPanel();
		derecha.add(arriba);
		arriba.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel arriba1 = new JPanel();
		arriba.add(arriba1);
		arriba1.setLayout(new GridLayout(3, 4, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Articulo:");
		arriba1.add(lblNewLabel);
		
		txtArticulo = new JTextField();
		arriba1.add(txtArticulo);
		txtArticulo.setColumns(10);
		
		JLabel de = new JLabel("Deporte:");
		arriba1.add(de);
		
		txtDeporte = new JTextField();
		arriba1.add(txtDeporte);
		txtDeporte.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Marca:");
		arriba1.add(lblNewLabel_2);
		
		txtMarca = new JTextField();
		arriba1.add(txtMarca);
		txtMarca.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Genero:");
		arriba1.add(lblNewLabel_3);
		
		txtGenero = new JTextField();
		arriba1.add(txtGenero);
		txtGenero.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Talla:");
		arriba1.add(lblNewLabel_4);
		
		txtTalla = new JTextField();
		arriba1.add(txtTalla);
		txtTalla.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Precio:");
		arriba1.add(lblNewLabel_5);
		
		txtPrecio = new JTextField();
		arriba1.add(txtPrecio);
		txtPrecio.setColumns(10);
		
		JPanel arriba2 = new JPanel();
		arriba.add(arriba2);
		
		JButton Buscar = new JButton("Buscar");
		arriba2.add(Buscar);
		
		JButton BotonAnadir = new JButton("Anadir Producto");
		arriba2.add(BotonAnadir);
		
		JButton BotonBorrar = new JButton("Borrar Producto");
		arriba2.add(BotonBorrar);
		
		JButton Actualizar = new JButton("Actualizar ");
		arriba2.add(Actualizar);
		
		JPanel abajo = new JPanel();
		derecha.add(abajo);
		
		JSpinner spinner_1 = new JSpinner();
		abajo.add(spinner_1);
		
		JButton BotonStock = new JButton("Anadir Stock");
		abajo.add(BotonStock);
		
		botonInsertarproducto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		this.tablaProductos.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				Double precioNuevo = (Double) tablaProductos.getValueAt(e.getFirstRow(), e.getColumn());
				Integer id = (Integer) tablaProductos.getValueAt(e.getFirstRow(), 0);
				gestorBD.actualizarPrecio(id, precioNuevo);
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
		
		Buscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filaseleccionada = tablaProductos.getSelectedRow();
				if(filaseleccionada < 0) {
					JOptionPane.showMessageDialog(null, "Selecciona una fila de la tabla", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
				
				txtArticulo.setText((String) tablaProductos.getValueAt(filaseleccionada, 1));
				txtDeporte.setText((String) tablaProductos.getValueAt(filaseleccionada, 2));
				txtMarca.setText((String) tablaProductos.getValueAt(filaseleccionada, 3));
				txtGenero.setText(tablaProductos.getValueAt(filaseleccionada, 4)+ "");
				txtTalla.setText((String) tablaProductos.getValueAt(filaseleccionada, 5));
				txtPrecio.setText(tablaProductos.getValueAt(filaseleccionada, 6)+ "");
			}
		});
		
		BotonAnadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String []info = new String[7];
				info[0] = "21";
				info[1] = txtArticulo.getText();
				info[2] = txtDeporte.getText();
				info[3] = txtMarca.getText();
				info[4] = txtGenero.getText();
				info[5] = txtTalla.getText();
				info[6] = txtPrecio.getText();
				
				modeloDatosProductos.addRow(info);
				
				limpiar();
			}

			
		});
	
		BotonBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filaseleccionada = tablaProductos.getSelectedRow();
				if(filaseleccionada >= 0) {
					modeloDatosProductos.removeRow(filaseleccionada);
				}else {
					JOptionPane.showMessageDialog(null, "Selecciona una fila de la tabla", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		
		
		
		
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
		txtGenero.setText("");
		txtMarca.setText("");
		txtPrecio.setText("");
		txtTalla.setText("");
		
	}
	
	
	
}
