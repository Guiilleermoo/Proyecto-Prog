package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
		tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		
		TableCellRenderer renderJefe = (table, value, isSelected, hasFocus, row, column) -> {

			JLabel label = new JLabel(value.toString());

				if (row == this.mouseRow && table.getValueAt(row, 3) == Estatus.JEFE ) {
					label.setBackground(Color.GREEN);
				} else {
					label.setBackground(table.getBackground());
				}
				
			if (isSelected) {
				label.setBackground(table.getSelectionBackground());
				label.setForeground(table.getForeground());
			}
			
			label.setOpaque(true);

			return label;
			};
			
			this.tablaEmpleados.setDefaultRenderer(Object.class, renderJefe);
			
			
			this.tablaEmpleados.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					//Se obtiene la fila/columna sobre la que está el ratón mientras se mueve
					mouseRow = tablaEmpleados.rowAtPoint(e.getPoint());
					mouseCol = tablaEmpleados.columnAtPoint(e.getPoint());

					//Se repinta la tabla para forzar el renderizado de las celdas
					if (mouseRow > -1 && mouseCol >-1) {
						tablaEmpleados.repaint();
					}
				}
			});
		
		
		tablaEmpleados.addMouseListener(new MouseAdapter() {
		
			
			public void mousePressed(MouseEvent e) {
				
				int seleccion = 0;
	        	if(tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 3).toString().toUpperCase() == "EMPLEADO" ) {
	        		seleccion = 1;
	        	}
				
				JTable table = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = table.rowAtPoint(point);
				if(e.getClickCount() == 1) {
					nombreYApellidoText.setText(tablaEmpleados.getValueAt(row, 1).toString());
					gmailText.setText(tablaEmpleados.getValueAt(row, 2).toString());
					estatusCombo.setSelectedIndex(seleccion);
					salarioSpinner.setValue(Double.parseDouble(tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 4).toString()));
					telefonoText.setText(tablaEmpleados.getValueAt(row, 5).toString());
					
				}
				
			}
		
		});
		

		nombreYApellidoText.addFocusListener(new FocusAdapter() {
		    public void focusLost(FocusEvent e) {
		    	if(tablaEmpleados.getSelectionModel().isSelectionEmpty() == false) {
		    		gestorBD.log(Level.INFO, nombreYApellidoText.getText() + tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0) , null);
		    		gestorBD.actualizarDatosEmpleado("NOMBREYAPELLIDOS", tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0).toString(), nombreYApellidoText.getText());
		    		tablaEmpleados.setValueAt(nombreYApellidoText.getText(), tablaEmpleados.getSelectedRow(), 1);
		    	}
		        
		    }
		});
		
		gmailText.addFocusListener(new FocusAdapter() {
		    public void focusLost(FocusEvent e) {
		    	if(tablaEmpleados.getSelectionModel().isSelectionEmpty() == false) {
		    		gestorBD.log(Level.INFO, gmailText.getText() + tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0), null);
		    		gestorBD.actualizarDatosEmpleado("GMAIL", tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0).toString(), gmailText.getText());
		    		tablaEmpleados.setValueAt(gmailText.getText(), tablaEmpleados.getSelectedRow(), 2);
		    	}
		    }
		});
		
		estatusCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if(tablaEmpleados.getSelectionModel().isSelectionEmpty() == false) {
            		gestorBD.log(Level.INFO, estatusCombo.getSelectedItem().toString() + tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0), null);
            		gestorBD.actualizarDatosEmpleado("ESTATUS", tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0).toString(), estatusCombo.getSelectedItem().toString());
            		tablaEmpleados.setValueAt(estatusCombo.getSelectedItem(), tablaEmpleados.getSelectedRow(), 3);
            	}
            }
        });
		
		salarioSpinner.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	if(tablaEmpleados.getSelectionModel().isSelectionEmpty() == false) {
	        		gestorBD.log(Level.INFO, salarioSpinner.getValue().toString() + tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0), null);
	        		gestorBD.actualizarDatosEmpleado("SALARIO", tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0).toString(), salarioSpinner.getValue().toString());
	        		tablaEmpleados.setValueAt(salarioSpinner.getValue().toString(), tablaEmpleados.getSelectedRow(), 4);
	        	}
	        }
	    });
		
		telefonoText.addFocusListener(new FocusAdapter() {
		    public void focusLost(FocusEvent e) {
		    	if(tablaEmpleados.getSelectionModel().isSelectionEmpty() == false) {
		    		gestorBD.log(Level.INFO, telefonoText.getText() + tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0), null);
		    		gestorBD.actualizarDatosEmpleado("TELEFONO", tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0).toString(), telefonoText.getText());
		    		tablaEmpleados.setValueAt(telefonoText.getText(), tablaEmpleados.getSelectedRow(), 5);
		    	}
		    }
		});
		
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
					gestorBD.log(Level.SEVERE, "Error: campo de telefono err�neo/vacio", null);
				} else {
					gestorBD.insertarTrabajador(t);
					loadEmpleados();
					limpiar();
				}
			}
		});
		
		botonBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(filaseleccionada >= 0) {
					
					int id = (int) tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0);					
					
					gestorBD.borrarTrabajador(id);
					
					modeloDatosEmpleados.removeRow(tablaEmpleados.getSelectedRow());
					tablaEmpleados.repaint();
					limpiar();
				} else {
					gestorBD.log(Level.SEVERE, "Error: seleccione una fila para borrar un producto", null);
				}
				
			}
		});
		
				
		this.setTitle("Gestion Empleados");
		this.pack();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setIconImage(new ImageIcon("data/logo.png").getImage());
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
