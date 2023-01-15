package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Editors.GeneroEditor;
import Editors.TallaEditor;
import es.deusto.prog.III.Producto;
import es.deusto.prog.III.Trabajador;
import es.deusto.prog.III.Trabajador.Estatus;
import es.deusto.prog.III.BD.GestorBD;
import es.deusto.prog.III.Producto.Genero;

public class GestionEmpleados extends JFrame{
	protected GestorBD gestorBD;
	protected String gmail;
	protected String contrasena;
	protected JTable tablaEmpleados;
	protected DefaultTableModel modeloDatosEmpleados;
	protected JScrollPane scrollPaneEmpleados;
	protected JTextField nombreYApellidoText;
	protected JTextField gmailText;
	protected JTextField contrasenaText;
	protected JTextField telefonoText;
	protected JComboBox<Estatus> estatusCombo;
	protected JSpinner salarioSpinner;
	protected SpinnerNumberModel modeloSpinner; 
	protected int filaseleccionada;
	
	//Lista de empleados
	private List<Trabajador> empleados;
	
	//Valores para implementar la modificacion del renderizado
	//cuando el raton pasa sobre una celda de la tabla
	protected int mouseRow = -1;
	protected int mouseCol = -1;
	
	public GestionEmpleados(GestorBD gestorBD, String gmail, String contrasena) {
		this.gestorBD = gestorBD;
		this.gmail = gmail;
		this.contrasena = contrasena;
		
		//Se inicializan las tablas y sus modelos de datos
		this.initTables();
		//Se cargan los empleados en la tabla de empleados
		this.loadEmpleados();
		
		Container cp = this.getContentPane();
		getContentPane().setLayout(new GridLayout(1, 2));
		
		JPanel izquierda = new JPanel();
		getContentPane().add(izquierda);
		
		tablaEmpleados = new JTable(modeloDatosEmpleados);
		izquierda.add(tablaEmpleados);
		
		//La tabla de productos se inserta en un panel con scroll
		JScrollPane scrollPaneEmpleados = new JScrollPane(this.tablaEmpleados);
		scrollPaneEmpleados.setBorder(new TitledBorder("Empleados"));
		this.tablaEmpleados.setFillsViewportHeight(true);
		izquierda.add(scrollPaneEmpleados);
		
		JPanel derecha = new JPanel();
		getContentPane().add(derecha);
		derecha.setLayout(new GridLayout(7, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Nombre Y Apellidos:");
		derecha.add(lblNewLabel);
		
		nombreYApellidoText = new JTextField();
		derecha.add(nombreYApellidoText);
		nombreYApellidoText.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Gmail:");
		derecha.add(lblNewLabel_1);
		
		gmailText = new JTextField();
		derecha.add(gmailText);
		gmailText.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Contrasena:");
		derecha.add(lblNewLabel_2);
		
		contrasenaText = new JTextField();
		derecha.add(contrasenaText);
		contrasenaText.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Estatus:");
		derecha.add(lblNewLabel_3);
		
		JComboBox estatusCombo = new JComboBox(Estatus.values());
		derecha.add(estatusCombo);
		
		
		JLabel lblNewLabel_4 = new JLabel("Salario:");
		derecha.add(lblNewLabel_4);
		
		SpinnerNumberModel modeloSpinner = new SpinnerNumberModel(2000.0, 900.0, 5000.0, 100.0);
		JSpinner salarioSpinner = new JSpinner(modeloSpinner);
		derecha.add(salarioSpinner);
		
		
		JLabel lblNewLabel_5 = new JLabel("Telefono:");
		derecha.add(lblNewLabel_5);
		
		telefonoText = new JTextField();
		derecha.add(telefonoText);
		telefonoText.setColumns(10);
		
		JButton botonAnadir = new JButton("Anadir");
		derecha.add(botonAnadir);
		
		JButton botonBorrar = new JButton("Borrar");
		derecha.add(botonBorrar);
		
		TableCellRenderer renderStock = (table, value, isSelected, hasFocus, row, column) -> {

			JLabel label = new JLabel(value.toString());

				if (table.getValueAt(row, 3) == Estatus.JEFE ) {
					label.setBackground(Color.GREEN);
				} else {
					label.setBackground(table.getBackground());
				}
				
			if (isSelected) {
				label.setBackground(table.getSelectionBackground());
			}
			
			label.setOpaque(true);

			return label;
			};
			
		tablaEmpleados.getColumnModel().getColumn(0).setCellRenderer(renderStock);	
		tablaEmpleados.getColumnModel().getColumn(1).setCellRenderer(renderStock);	
		tablaEmpleados.getColumnModel().getColumn(2).setCellRenderer(renderStock);	
		tablaEmpleados.getColumnModel().getColumn(3).setCellRenderer(renderStock);	
		tablaEmpleados.getColumnModel().getColumn(4).setCellRenderer(renderStock);		
		tablaEmpleados.getColumnModel().getColumn(5).setCellRenderer(renderStock);	
		
		
		botonAnadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Trabajador t = new Trabajador();
				
				t.setId(gestorBD.getLastId());
				t.setNombreYApellidos(nombreYApellidoText.getText());
				t.setGmail(gmailText.getText());
				t.setContrasena(contrasenaText.getText());
				t.setSalario((double) modeloSpinner.getValue());
				t.setStatus((Estatus) estatusCombo.getSelectedItem());
				t.setTelefono(telefonoText.getText());
				
				if (nombreYApellidoText.getText().isEmpty()) {
					gestorBD.log(Level.SEVERE, "Error: campo NombreYApellido vacio", null);
				} else if (gmailText.getText().isEmpty()) {
					gestorBD.log(Level.SEVERE, "Error: campo Gmail vacio", null);
				} else if (contrasenaText.getText().isEmpty()) {
					gestorBD.log(Level.SEVERE, "Error: campo Contrasena vacio", null);
				} else if (telefonoText.getText().isEmpty() || telefonoText.getText().length() != 9) {
					gestorBD.log(Level.SEVERE, "Error: campo de telefono erróneo/vacio", null);
				} else {
					gestorBD.insertarTrabajador(t);
					loadEmpleados();
				}
			}
		});
		
		botonBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(filaseleccionada >= 0) {
					
					int id = (int) tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0);
//					String nombreYApellidos = (String) tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 1);
//					String gmail =  (String) tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 2);
//					Estatus status = (Estatus) tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 3);
//					double salario = (double) tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 4);
//					String telefono = (String) tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 5);
//					trabajador.setId(id);
					
					
					gestorBD.borrarTrabajador(id);
					
					modeloDatosEmpleados.removeRow(tablaEmpleados.getSelectedRow());
					tablaEmpleados.repaint();
				} else {
					gestorBD.log(Level.SEVERE, "Error: seleccione una fila para borrar un producto", null);
				}
				
			}
		});
		
				
		this.setTitle("Gestion Empleados");
		this.pack();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}
	

	private void loadEmpleados() {
		
		
		this.empleados = gestorBD.obtenerTrabajadores();
		//Se borran los datos del modelo de datos
		this.modeloDatosEmpleados.setRowCount(0);

		// Se anade al modelo una fila de datos por cada empleado
		for (Trabajador p : empleados) {
			this.modeloDatosEmpleados.addRow( new Object[] {p.getId(), p.getNombreYApellidos(), p.getGmail(), p.getStatus(), p.getSalario(), p.getTelefono()} );

		}		
	}
	
	private void initTables() {
		//Cabecera del modelo de datos
		Vector<String> cabeceraEmpleados = new Vector<String>(Arrays.asList("ID", "NOMBRE Y APELLIDOS", "GMAIL", "STATUS", "SALARIO", "TELEFONO"));
		
		//Se crea el modelo de datos para la tabla de empleados solo con la cabecera		
		this.modeloDatosEmpleados = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraEmpleados) {
			public boolean isCellEditable (int row, int column) {
				return false;
			}
		};

	}
	private void limpiar() {
		telefonoText.setText("");
		nombreYApellidoText.setText("");
		gmailText.setText("");
		contrasenaText.setText("");
		
		
	}
}
