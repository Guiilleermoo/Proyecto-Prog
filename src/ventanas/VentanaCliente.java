package ventanas;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import es.deusto.prog.III.Producto;
import es.deusto.prog.III.BD.GestorBD;
import es.deusto.prog.III.Producto.Genero;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class VentanaCliente extends JFrame{
	protected GestorBD gestorBD;
	
	// Guardamos el gmail y contrasena en variables
	protected String gmail;
	protected String contrasena;
	
	private static final long serialVersionUID = 1L;
	
	//Lista de objetos productos
	private List<Producto> productos;
	
	//Definicionn de las tablas y modelos de datos de cada una
	protected JTable tablaProductos;
	protected JTable tablaSeleccionados;
	protected DefaultTableModel modeloDatosSeleccionados;
	protected DefaultTableModel modeloDatosProductos;
	protected JScrollPane scrollPaneProductos;

	//Valores para implementar la modificacion del renderizado
	//cuando el raton pasa sobre una celda de la tabla
	protected int mouseRow = -1;
	protected int mouseCol = -1;

	
	public VentanaCliente(GestorBD gestorBD, String gmail, String contrasena) {
		this.gestorBD = gestorBD;
		this.gmail = gmail;
		this.contrasena = contrasena;
		
		
		getContentPane().setLayout(new GridLayout(3, 0));
		
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);

		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5);

		
		JLabel Articulo = new JLabel("Articulo:");
		panel_5.add(Articulo);
		
		JComboBox articulo = new JComboBox();
		articulo.setModel(new DefaultComboBoxModel(new String[] {"Cualquiera", "Zapatillas", "Camiseta", "Pantalon", "Accesorios"}));
		panel_5.add(articulo);
		
		JLabel Deporte = new JLabel("Deporte:");
		Deporte.setEnabled(true);
		panel_5.add(Deporte);
		
		JComboBox deporte = new JComboBox();
		deporte.setModel(new DefaultComboBoxModel(new String[] {"Cualquiera", "Running", "Futbol"}));
		panel_5.add(deporte);
		
		JLabel Marca = new JLabel("Marca:");
		panel_5.add(Marca);
		
		JComboBox marca = new JComboBox();
		marca.setModel(new DefaultComboBoxModel(new String[] {"Cualquiera", "Nike", "Adidas", "Asics", "New Balance", "Puma", "Joma"}));
		panel_5.add(marca);
		
		JLabel Genero = new JLabel("Genero:");
		panel_5.add(Genero);
		
		JComboBox genero = new JComboBox();
		panel_5.add(genero);
		genero.setModel(new DefaultComboBoxModel(new String[] {"Cualquiera", "Hombre", "Mujer", "Unisex", "Niño ", "Niña"}));
		
		JPanel panel = new JPanel();
		panel_4.add(panel);
		
		
		JLabel Precio = new JLabel("Precio:");
		panel.add(Precio);
		
		JSlider slider = new JSlider();
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setValue(30);
		slider.setMinorTickSpacing(5);
		slider.setMaximum(300);
		slider.setMajorTickSpacing(50);
		panel.add(slider);
		
		JCheckBox Precio2 = new JCheckBox("Cualquier precio");
		Precio2.setHorizontalAlignment(SwingConstants.LEFT);
		panel_4.add(Precio2);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		JButton Buscar = new JButton("Buscar");
		Buscar.setBackground(new Color(177, 205, 248));
		panel_3.add(Buscar);
		
		JPanel panel_6 = new JPanel();
		getContentPane().add(panel_6);
		panel_6.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		panel_2.setLayout(new GridLayout(2, 0, 0, 0));
		
		
		tablaProductos = new JTable(modeloDatosProductos);

		tablaSeleccionados = new JTable();
		

		//Se inicializan las tablas y sus modelos de datos
		this.initTables();
		//Se cargan los comics en la tabla de comics
		this.loadproductos();
		
		//La tabla de productos se inserta en un panel con scroll
		JScrollPane scrollPaneProductos = new JScrollPane(this.tablaProductos);
		scrollPaneProductos.setBorder(new TitledBorder("Productos"));
		this.tablaProductos.setFillsViewportHeight(true);
		panel_6.add(scrollPaneProductos);
		
		JScrollPane scrollPaneSeleccionados = new JScrollPane(this.tablaSeleccionados);
		scrollPaneSeleccionados.setBorder(new TitledBorder("Carrito"));
		this.tablaSeleccionados.setFillsViewportHeight(true);
		panel_2.add(scrollPaneSeleccionados, BorderLayout.CENTER);
		
		JPanel panel_8 = new JPanel();
		panel_2.add(panel_8);
		
		JButton Borrar = new JButton("Borrar");
		panel_8.add(Borrar);
		Borrar.setBackground(new Color(177, 205, 248));
		
		JButton Carrito = new JButton("Carrito");
		panel_8.add(Carrito);
		Carrito.setBackground(new Color(177, 205, 248));
		
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7);
		
		JButton Anyadir = new JButton("Añadir");
		Anyadir.setBackground(new Color(177, 205, 248));
		panel_7.add(Anyadir);

		
		// ventana est�ndar
		this.setTitle("Cliente");
		this.pack();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
		
		Buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProductosFiltro(articulo.getSelectedItem().toString(), deporte.getSelectedItem().toString(), marca.getSelectedItem().toString(), genero.getSelectedItem().toString(), slider.getValue());
            }
        });
		
		Anyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadSeleccionados();
				
				
				
			}
		});
		
		Borrar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int seleccionado = tablaSeleccionados.getSelectedRow();
				modeloDatosSeleccionados.removeRow(seleccionado);
				tablaSeleccionados.repaint();
				
			}
			
		});

	}
	
	private void initTables() {
		//Cabecera del modelo de datos
		Vector<String> cabeceraProductos = new Vector<String>(Arrays.asList( "ID", "ARTICULO", "DEPORTE", "MARCA", "GENERO", "TALLA", "PRECIO"));
		
		//Se crea el modelo de datos para la tabla de productos solo con la cabecera		
		this.modeloDatosProductos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraProductos) {
			public boolean isCellEditable (int row, int column) {
				return false;
			}
		};
		
		this.modeloDatosSeleccionados = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraProductos) {
			public boolean isCellEditable (int row, int column) {
				return false;
			}
		};
		//Se crea la tabla de comics con el modelo de datos		
		tablaProductos = new JTable(this.modeloDatosProductos);	
		tablaSeleccionados = new JTable(this.modeloDatosSeleccionados);	
				
		//Render para las celdas numéricas ajuste de colores y texto centrado
		DefaultTableCellRenderer renderSencillo = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel label = new JLabel(value.toString());					
				label.setHorizontalAlignment(JLabel.CENTER);
						
				//Si la celda esta seleccionada se asocia un color de fondo y letra
				if (mouseRow == row) {
					label.setBackground(Color.PINK);
					label.setForeground(Color.WHITE);
				}
						
				//Si la celda esta seleccionada se asocia un color de fondo y letra
				if (isSelected) {
					label.setBackground(table.getSelectionBackground());
					label.setForeground(table.getSelectionForeground());
				}

				//Es necesaria esta sentencia para pintar correctamente el color de fondo
				label.setOpaque(true);
														
				return label;
			}
		};
		
		DefaultTableCellRenderer render2 = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel label = new JLabel(value.toString());					
				label.setHorizontalAlignment(JLabel.CENTER);
						
				//Si la celda esta seleccionada se asocia un color de fondo y letra
				if (mouseRow == row) {
					label.setBackground(Color.BLUE);
					label.setForeground(Color.WHITE);
				}
						
				//Si la celda esta seleccionada se asocia un color de fondo y letra
				if (isSelected) {
					label.setBackground(table.getSelectionBackground());
					label.setForeground(table.getSelectionForeground());
				}

				//Es necesaria esta sentencia para pintar correctamente el color de fondo
				label.setOpaque(true);
														
				return label;
			}
		};
		
		//Render para la alineacion personalizada del texto de las cabeceras
		DefaultTableCellRenderer centerHeadRenderer = new DefaultTableCellRenderer();
		centerHeadRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		//Se cambia el render de las cabeceras para ajustar en centrado del texto
		this.tablaProductos.getTableHeader().getColumnModel().getColumn(0).setHeaderRenderer(centerHeadRenderer);
		this.tablaProductos.getTableHeader().getColumnModel().getColumn(1).setHeaderRenderer(centerHeadRenderer);
		this.tablaProductos.getTableHeader().getColumnModel().getColumn(2).setHeaderRenderer(centerHeadRenderer);
		this.tablaProductos.getTableHeader().getColumnModel().getColumn(3).setHeaderRenderer(centerHeadRenderer);
		this.tablaProductos.getTableHeader().getColumnModel().getColumn(4).setHeaderRenderer(centerHeadRenderer);
		this.tablaProductos.getTableHeader().getColumnModel().getColumn(5).setHeaderRenderer(centerHeadRenderer);
		this.tablaProductos.getTableHeader().getColumnModel().getColumn(6).setHeaderRenderer(centerHeadRenderer);
		
		this.tablaSeleccionados.getTableHeader().getColumnModel().getColumn(0).setHeaderRenderer(centerHeadRenderer);
		this.tablaSeleccionados.getTableHeader().getColumnModel().getColumn(1).setHeaderRenderer(centerHeadRenderer);
		this.tablaSeleccionados.getTableHeader().getColumnModel().getColumn(2).setHeaderRenderer(centerHeadRenderer);
		this.tablaSeleccionados.getTableHeader().getColumnModel().getColumn(3).setHeaderRenderer(centerHeadRenderer);
		this.tablaSeleccionados.getTableHeader().getColumnModel().getColumn(4).setHeaderRenderer(centerHeadRenderer);
		this.tablaSeleccionados.getTableHeader().getColumnModel().getColumn(5).setHeaderRenderer(centerHeadRenderer);
		this.tablaSeleccionados.getTableHeader().getColumnModel().getColumn(6).setHeaderRenderer(centerHeadRenderer);
		
		//Se modifica el Renderer de las columnas		
		this.tablaProductos.getColumnModel().getColumn(0).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(1).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(2).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(3).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(4).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(5).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(6).setCellRenderer(renderSencillo);
		
		this.tablaSeleccionados.getColumnModel().getColumn(0).setCellRenderer(render2);
		this.tablaSeleccionados.getColumnModel().getColumn(1).setCellRenderer(render2);
		this.tablaSeleccionados.getColumnModel().getColumn(2).setCellRenderer(render2);
		this.tablaSeleccionados.getColumnModel().getColumn(3).setCellRenderer(render2);
		this.tablaSeleccionados.getColumnModel().getColumn(4).setCellRenderer(render2);
		this.tablaSeleccionados.getColumnModel().getColumn(5).setCellRenderer(render2);
		this.tablaSeleccionados.getColumnModel().getColumn(6).setCellRenderer(render2);
		
		//Se modifica el modelo de seleccion de la tabla para que se pueda selecciona unicamente una fila
		this.tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.tablaProductos.addMouseListener(new MouseAdapter() {						
			@Override
			public void mousePressed(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());
				
				//Tiene que ir al logger			
				System.out.println(String.format("Se ha pulsado el botón %d en la fila %d, columna %d", e.getButton(), row+1, col+1));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());
				
				System.out.println(String.format("Se ha liverado el botón %d en la fila %d, columna %d", e.getButton(), row+1, col+1));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());
				
				System.out.println(String.format("Se ha hecho click con el botón %d en la fila %d, columna %d", e.getButton(), row+1, col+1));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());
				
				System.out.println(String.format("Se ha entrado en la fila %d, columna %d", e.getButton(), row+1, col+1));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());

				System.out.println(String.format("Se ha salido de la fila %d, columna %d", e.getButton(), row+1, col+1));

				//Cuando el ratón sale de la tabla, se resetea la columna/fila sobre la que está el ratón				
				mouseRow = -1;
				mouseCol = -1;
			}
			
		});
		
		this.tablaSeleccionados.addMouseListener(new MouseAdapter() {						
			@Override
			public void mousePressed(MouseEvent e) {
				int row = tablaSeleccionados.rowAtPoint(e.getPoint());
				int col = tablaSeleccionados.columnAtPoint(e.getPoint());
				
				//Tiene que ir al logger			
				System.out.println(String.format("Se ha pulsado el botón %d en la fila %d, columna %d", e.getButton(), row+1, col+1));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = tablaSeleccionados.rowAtPoint(e.getPoint());
				int col = tablaSeleccionados.columnAtPoint(e.getPoint());
				
				System.out.println(String.format("Se ha liverado el botón %d en la fila %d, columna %d", e.getButton(), row+1, col+1));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaSeleccionados.rowAtPoint(e.getPoint());
				int col = tablaSeleccionados.columnAtPoint(e.getPoint());
				
				System.out.println(String.format("Se ha hecho click con el botón %d en la fila %d, columna %d", e.getButton(), row+1, col+1));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				int row = tablaSeleccionados.rowAtPoint(e.getPoint());
				int col = tablaSeleccionados.columnAtPoint(e.getPoint());
				
				System.out.println(String.format("Se ha entrado en la fila %d, columna %d", e.getButton(), row+1, col+1));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				int row = tablaSeleccionados.rowAtPoint(e.getPoint());
				int col = tablaSeleccionados.columnAtPoint(e.getPoint());

				System.out.println(String.format("Se ha salido de la fila %d, columna %d", e.getButton(), row+1, col+1));

				//Cuando el ratón sale de la tabla, se resetea la columna/fila sobre la que está el ratón				
				mouseRow = -1;
				mouseCol = -1;
			}
			
		});
				
		//Se define el comportamiento de los eventos de movimiento del ratón: MOVED DRAGGED
		this.tablaSeleccionados.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				//Se obtiene la fila/columna sobre la que están el ratón mientras se mueve
				int row = tablaSeleccionados.rowAtPoint(e.getPoint());
				int col = tablaSeleccionados.columnAtPoint(e.getPoint());

				//Cuando el ratón se mueve sobre tabla, actualiza la fila/columna sobre la que está el ratón
				//de esta forma se puede modificar el color de renderizado de la celda.				
				mouseRow = row;
				mouseCol = col;
						
				//Se fuerza el redibujado de la tabla para modificar el color de la celda sobre la que está el ratón.
				tablaSeleccionados.repaint();
			}
					
			@Override
			public void mouseDragged(MouseEvent e) { 
				int row = tablaSeleccionados.rowAtPoint(e.getPoint());
				int col = tablaSeleccionados.columnAtPoint(e.getPoint());

				System.out.println(String.format("Se está arrastrando con el botón %d pulsado sobre la fila %d, columna %d", e.getButton(), row+1, col+1));
			}			
		});
		
		//Se define el comportamiento de los eventos de movimiento del ratón: MOVED DRAGGED
				this.tablaSeleccionados.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseMoved(MouseEvent e) {
						//Se obtiene la fila/columna sobre la que están el ratón mientras se mueve
						int row = tablaSeleccionados.rowAtPoint(e.getPoint());
						int col = tablaSeleccionados.columnAtPoint(e.getPoint());

						//Cuando el ratón se mueve sobre tabla, actualiza la fila/columna sobre la que está el ratón
						//de esta forma se puede modificar el color de renderizado de la celda.				
						mouseRow = row;
						mouseCol = col;
								
						//Se fuerza el redibujado de la tabla para modificar el color de la celda sobre la que está el ratón.
						tablaSeleccionados.repaint();
					}
							
					@Override
					public void mouseDragged(MouseEvent e) { 
						int row = tablaSeleccionados.rowAtPoint(e.getPoint());
						int col = tablaSeleccionados.columnAtPoint(e.getPoint());

						System.out.println(String.format("Se está arrastrando con el botón %d pulsado sobre la fila %d, columna %d", e.getButton(), row+1, col+1));
					}			
				});
				
		
		
	}
	
	private void loadProductosFiltro(String articulo, String deporte, String marca, String genero, double precio) {
		this.productos = gestorBD.obtenerProductosFiltro(articulo, deporte, marca, genero, precio);
		//Se borran los datos del modelo de datos
		this.modeloDatosProductos.setRowCount(0);
		
		// Se anade al modelo una fila de datos por cada comic
		for (Producto p : this.productos) {
			this.modeloDatosProductos.addRow( new Object[] {p.getId(), p.getArticulo(), p.getDeporte(), p.getMarca(), p.getGenero(),p.getTalla(), p.getPrecio(),} );
		}
	}
	
	private void loadproductos() {
		this.productos = gestorBD.obtenerProductos();
		//Se borran los datos del modelo de datos
		this.modeloDatosProductos.setRowCount(0);
		
		// Se anade al modelo una fila de datos por cada comic
		for (Producto p : this.productos) {
			this.modeloDatosProductos.addRow( new Object[] {p.getId(), p.getArticulo(), p.getDeporte(), p.getMarca(), p.getGenero(),p.getTalla(), p.getPrecio(),} );
		}		
	}
	
	private void loadSeleccionados() {
		 DefaultTableModel tmO = (DefaultTableModel) tablaProductos.getModel(),
                 tmD = (DefaultTableModel) tablaSeleccionados.getModel();
		 /* Si utilizas otro TableModel, el tipo de estas variables debe ser el mismo 
  			que el TableModel que utilizas.
		  */
		 // Si tienes filas seleccionadas en la tabla de origen:
		 if(tablaProductos.getSelectedRowCount() > 0) {
			 // 1) Obtén los índices de las filas seleccionadas.
			 int[] indices = tablaProductos.getSelectedRows();
			 // 2) Para cada fila, crea un Array para guardar los valores... 
			 for(int i : indices) {
				 Object[] fila = new Object[tmO.getColumnCount()];
				 // ... y guarda los valores de la fila de origen.
				 for(int j = 0; j < fila.length; j++) {
					 fila[j] = tmO.getValueAt(i, j);
				 }
				 // 3) Agrega la fila al TableModel de la tabla de destino
				 tmD.addRow(fila);
			 }
		 }
		}
	}