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
		
		Container cp = this.getContentPane();
		
		JPanel arriba = new JPanel();
		
		cp.add(arriba, BorderLayout.NORTH);
		
		
		//Categorias del filtrado
		
		
		JLabel Articulo = new JLabel("Articulo: ");
		JLabel Deporte = new JLabel("Deporte: ");
		JLabel Marca = new JLabel("Marca: ");
		JLabel Genero = new JLabel("Genero: ");
		JLabel Talla = new JLabel("Talla: ");
		JLabel Precio = new JLabel("Precio: ");
		JButton buscar = new JButton("Buscar");
		
		//Filtrado Articulo
		JComboBox<String> articulo = new JComboBox<String>();
		articulo.addItem("Cualquiera");
		articulo.addItem("Zapatillas");
		articulo.addItem("Camiseta");
		articulo.addItem("Pantalon");
		articulo.addItem("Accesorios");
		
		//Filtrado Deporte
		JComboBox<String> deporte = new JComboBox<String>();
		deporte.addItem("Cualquiera");
		deporte.addItem("Futbol");
		deporte.addItem("Running");
		
		//Filtrado Marca
		JComboBox<String> marca = new JComboBox<String>();
		marca.addItem("Cualquiera");
		marca.addItem("Nike");
		marca.addItem("Adidas");
		marca.addItem("Asics");
		marca.addItem("Puma");
		marca.addItem("Joma");
		marca.addItem("New Balance");
		
		//Filtrado Genero
		JComboBox<String> genero = new JComboBox<String>();
		genero.addItem("Cualquiera");
		genero.addItem("Unisex");
		genero.addItem("Hombre");
		genero.addItem("Mujer");
		genero.addItem("Nino");
		genero.addItem("Nina");

		
		//Filtrado Talla
		JRadioButton XS = new JRadioButton("XS", false);
		JRadioButton S = new JRadioButton("S", false);
		JRadioButton M = new JRadioButton("M", false);
		JRadioButton L = new JRadioButton("L", false);
		JRadioButton XL = new JRadioButton("XL", false);
		JRadioButton XXL = new JRadioButton("XXL", false);
		JRadioButton cualquiera = new JRadioButton("Cualquiera", true);
		JRadioButton numero = new JRadioButton("Numero Talla: ", false);
		
		SpinnerNumberModel spinner = new SpinnerNumberModel(28, 28, 47, 1);
		JSpinner talla2 = new JSpinner(spinner);
		
		ButtonGroup grupo = new ButtonGroup();
		grupo.add(XS);
		grupo.add(S);
		grupo.add(M);
		grupo.add(L);
		grupo.add(XL);
		grupo.add(XXL);
		grupo.add(cualquiera);
		grupo.add(numero);
		
		
		
		
		//Filtro de precio
		JSlider precio = new JSlider(0,300, 30);
		precio.setMajorTickSpacing(50);
		precio.setMinorTickSpacing(5);
		precio.setPaintTicks(true);
		precio.setPaintLabels(true);
		precio.setFont(new Font("Arial", Font.BOLD, 13));
		
		
		//Añadir las cosas al JPanel
		arriba.add(Articulo);
		arriba.add(articulo);
		arriba.add(Deporte);
		arriba.add(deporte);
		arriba.add(Marca);
		arriba.add(marca);
		arriba.add(Genero);
		arriba.add(genero);
		arriba.add(Talla);
		arriba.add(XS);
		arriba.add(S);
		arriba.add(M);
		arriba.add(L);
		arriba.add(XL);
		arriba.add(XXL);
		arriba.add(cualquiera);
		arriba.add(numero);
		arriba.add(talla2);
		arriba.add(Precio);
		arriba.add(precio);
		
		
		
		arriba.add(buscar);
		
		

		//Se inicializan las tablas y sus modelos de datos
		this.initTables();
		//Se cargan los comics en la tabla de comics
		this.loadproductos();
		
		//La tabla de productos se inserta en un panel con scroll
		JScrollPane scrollPaneProductos = new JScrollPane(this.tablaProductos);
		scrollPaneProductos.setBorder(new TitledBorder("Productos"));
		this.tablaProductos.setFillsViewportHeight(true);
	
		
		//El Layout del panel principal es un matriz con 2 filas y 1 columna
		this.getContentPane().setLayout(new GridLayout(2, 1));
		this.getContentPane().add(scrollPaneProductos);
		
		// ventana est�ndar
		this.setTitle("Cliente");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

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
		//Se crea la tabla de comics con el modelo de datos		
		this.tablaProductos = new JTable(this.modeloDatosProductos);	
				
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
		
		//Se modifica el Renderer de las columnas		
		this.tablaProductos.getColumnModel().getColumn(0).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(1).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(2).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(3).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(4).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(5).setCellRenderer(renderSencillo);
		this.tablaProductos.getColumnModel().getColumn(6).setCellRenderer(renderSencillo);
		
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
				
		//Se define el comportamiento de los eventos de movimiento del ratón: MOVED DRAGGED
		this.tablaProductos.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				//Se obtiene la fila/columna sobre la que están el ratón mientras se mueve
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());

				//Cuando el ratón se mueve sobre tabla, actualiza la fila/columna sobre la que está el ratón
				//de esta forma se puede modificar el color de renderizado de la celda.				
				mouseRow = row;
				mouseCol = col;
						
				//Se fuerza el redibujado de la tabla para modificar el color de la celda sobre la que está el ratón.
				tablaProductos.repaint();
			}
					
			@Override
			public void mouseDragged(MouseEvent e) { 
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());

				System.out.println(String.format("Se está arrastrando con el botón %d pulsado sobre la fila %d, columna %d", e.getButton(), row+1, col+1));
			}			
		});
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
}