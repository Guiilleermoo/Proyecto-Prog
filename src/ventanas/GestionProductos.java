package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
		
		panel.setLayout(new BorderLayout());
		
		tablaProductos = new JTable(modeloDatosProductos);
		
		this.initTables();
		
		this.loadProductos();
		
		JScrollPane scrollPaneProductos = new JScrollPane(this.tablaProductos);
		scrollPaneProductos.setBorder(new TitledBorder("Productos"));
		this.tablaProductos.setFillsViewportHeight(true);
		panel.add(scrollPaneProductos, BorderLayout.WEST);
		
		JLabel selectLabel = new JLabel("Nombre de articulo: ");
		JTextField filtroArticulo = new JTextField();
		filtroArticulo.setColumns(15);
		panel.add(filtroArticulo, BorderLayout.NORTH);
		
		filtroArticulo.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				selectRows(filtroArticulo.getText());
				tablaProductos.repaint();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				selectRows(filtroArticulo.getText());
				tablaProductos.repaint();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				selectRows(filtroArticulo.getText());
				tablaProductos.repaint();
			}
		});
		
		TableCellRenderer render = (table, value, isSelected, hasFocus, row, column) -> {
			
		JLabel label = new JLabel(value.toString());
			
		if (table.getValueAt(row, 0).toString().contains(filtroArticulo.getText()) && !filtroArticulo.getText().isEmpty()) {
			label.setBackground(Color.BLUE);
		} else {
			label.setBackground(table.getBackground());
		}
		
		for (Producto producto : productos) {
			if (producto.getCantidad() == 0) {
				label.setBackground(Color.RED);
			}
		}
		
		if (isSelected) {
			label.setBackground(table.getSelectionBackground());
		}
		
		label.setOpaque(true);

		return label;			
		};
		
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
			this.modeloDatosProductos.addRow( new Object[] {p.getId(), p.getArticulo(), p.getDeporte(), p.getMarca(), p.getGenero(), p.getTalla(), p.getPrecio(), p.getCantidad()} );
		}	

	}
}
