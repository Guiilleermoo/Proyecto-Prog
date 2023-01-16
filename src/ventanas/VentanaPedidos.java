package ventanas;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Editors.GeneroEditor;
import Editors.TallaEditor;
import es.deusto.prog.III.BD.GestorBD;
import es.deusto.prog.III.Pedido;
import es.deusto.prog.III.Producto;

public class VentanaPedidos extends JFrame {
	protected GestorBD gestorBD;
	protected List<Pedido> pedidos;
	
	protected JScrollPane scrollPanePedidos;
	protected DefaultTableModel modeloDatosPedidos;
	protected JTable tablaPedidos;
	
	public VentanaPedidos(GestorBD gestorBD, int id) {
		this.gestorBD = gestorBD;
		
		Container cp = this.getContentPane();
		
		this.initTables();
		this.loadPedidos(id);

		JScrollPane scrollPanePedidos = new JScrollPane(this.tablaPedidos);
		scrollPanePedidos.setBorder(new TitledBorder("Pedidos de " + gestorBD.getClienteById(id).getNombreYApellidos()));
		this.tablaPedidos.setFillsViewportHeight(true);
		cp.add(scrollPanePedidos);

		
		this.setTitle("Ventana Pedidos");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void initTables() {
		//Cabecera del modelo de datos
		Vector<String> cabeceraPedidos = new Vector<String>(Arrays.asList("ID", "FECHA", "ESTADO"));
		
		//Se crea el modelo de datos para la tabla de productos solo con la cabecera		
		this.modeloDatosPedidos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraPedidos) {
			public boolean isCellEditable (int row, int column) {
				return false;
			}
		};
		
		//Se crea la tabla de productos con el modelo de datos		
		tablaPedidos = new JTable(this.modeloDatosPedidos);	
	}
	
	public void loadPedidos(int id) {
		this.pedidos = gestorBD.obtenerPedidosCliente(id);
		//Se borran los datos del modelo de datos
		this.modeloDatosPedidos.setRowCount(0);

		// Se anade al modelo una fila de datos por cada comic
		for (Pedido p : this.pedidos) {
			this.modeloDatosPedidos.addRow( new Object[] {p.getId(), p.getFecha(), p.getEstado()} );
			
		}	
	}
}
