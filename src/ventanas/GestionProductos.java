package ventanas;

import java.awt.Container;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

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
		
		tablaProductos = new JTable(modeloDatosProductos);
		
		this.initTables();
		
		this.loadProductos();
		
		JScrollPane scrollPaneProductos = new JScrollPane(this.tablaProductos);
		scrollPaneProductos.setBorder(new TitledBorder("Productos"));
		this.tablaProductos.setFillsViewportHeight(true);
		panel.add(scrollPaneProductos);
		
		this.setTitle("Gestion Productos");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void initTables() {
		Vector<String> cabeceraProductos = new Vector<String>(Arrays.asList( "ID", "ARTICULO", "DEPORTE", "MARCA", "GENERO", "TALLA", "PRECIO"));
		
		this.modeloDatosProductos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraProductos) {
		};
		
		tablaProductos = new JTable(this.modeloDatosProductos);	
		
		JComboBox<Genero> jComboGenero = new JComboBox<>(Genero.values());		
		DefaultCellEditor generoEditor = new DefaultCellEditor(jComboGenero) {
			private static final long serialVersionUID = 1L;
		};
		
		this.tablaProductos.getColumnModel().getColumn(4).setCellEditor(generoEditor);
	}
	
	public void loadProductos() {
		this.productos = gestorBD.obtenerProductos();
		//Se borran los datos del modelo de datos
		this.modeloDatosProductos.setRowCount(0);
		
		// Se anade al modelo una fila de datos por cada comic
		for (Producto p : this.productos) {
			this.modeloDatosProductos.addRow( new Object[] {p.getId(), p.getArticulo(), p.getDeporte(), p.getMarca(), p.getGenero(),p.getTalla(), p.getPrecio(),} );
		}	

	}
}
