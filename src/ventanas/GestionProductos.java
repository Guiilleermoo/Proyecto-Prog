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
		
		private List<Producto> productos;
		
		protected String gmail;
		protected String contrasena;
		
		protected JTable tablaProductos;
		protected DefaultTableModel modeloDatosProductos;
		protected JScrollPane scrollPaneProductos;
		
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
		
		JPanel arriba = new JPanel();
		arriba.setLayout(new GridLayout(4, 3));
		
		JLabel articuloLabel = new JLabel("Articulo: ");
		arriba.add(articuloLabel);
		
		JTextField articuloText = new JTextField();
		arriba.add(articuloText);
		
		JLabel deporteLabel = new JLabel("Deporte: ");
		arriba.add(deporteLabel);
		
		JTextField deporteText = new JTextField();
		arriba.add(deporteLabel);
		
		JLabel marcaLabel = new JLabel("marca: ");
		arriba.add(marcaLabel);
		
		JTextField marcaText = new JTextField();
		arriba.add(marcaText);
		
		JLabel generoLabel = new JLabel("Genero: ");
		arriba.add(generoLabel);
		
		JTextField generoText = new JTextField();
		arriba.add(generoText);
		
		JLabel tallaLabel = new JLabel("Talla: ");
		arriba.add(tallaLabel);
		
		JTextField tallaText = new JTextField();
		arriba.add(tallaText);
		
		JLabel precioLabel = new JLabel("Precio: ");
		arriba.add(precioLabel);
		
		JTextField precioText = new JTextField();
		arriba.add(precioText);
		
		
		JButton botonInsertarproducto = new JButton("Insertar Producto");
		
		
		
		
		
		
		
		JButton botonBorrarproducto = new JButton("Borrar Producto");
		
		JPanel abajo = new JPanel();
		SpinnerModel spinner = new SpinnerNumberModel(10, 0 , 100, 1);
		JSpinner spinnerStock = new JSpinner(spinner);
		JButton botonStock = new JButton("Añadir Stock");
		
		abajo.add(spinnerStock, BorderLayout.EAST);
		abajo.add(botonStock, BorderLayout.WEST);
		
		derecha.setLayout(new GridLayout(3,1));
		derecha.add(arriba);
		derecha.add(botonBorrarproducto);
		derecha.add(abajo);
		
		panel.add(derecha, BorderLayout.EAST);
		
		botonInsertarproducto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		botonBorrarproducto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		botonStock.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gestorBD.actualizarStock(gestorBD.getProductoById(tablaProductos.getSelectedRow()+1), Integer.parseInt(spinnerStock.getValue().toString()));
				loadProductos();
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
}
