package ventanas;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import es.deusto.prog.III.BD.GestorBD;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JButton;

public class GestionPedidos extends JFrame{
	protected GestorBD gestorBD;
	
	public GestionPedidos(GestorBD gestorBD) {
		
		this.setTitle("Gestion Pedidos");
		this.setSize(800,600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPanePedidos = new JScrollPane();
		scrollPanePedidos.setBounds(10, 43, 370, 357);
		panel.add(scrollPanePedidos);
		
		JList listaPedidos = new JList();
		scrollPanePedidos.setViewportView(listaPedidos);
		
		JScrollPane scrollPaneEntregando = new JScrollPane();
		scrollPaneEntregando.setBounds(404, 43, 370, 357);
		panel.add(scrollPaneEntregando);
		
		JList listaEntrega = new JList();
		scrollPaneEntregando.setViewportView(listaEntrega);
		
		JLabel jLabelPedidos = new JLabel("Pedidos");
		jLabelPedidos.setBounds(162, 11, 202, 21);
		panel.add(jLabelPedidos);
		
		JLabel jLabelEntrega = new JLabel("Entregando/Entregados");
		jLabelEntrega.setBounds(537, 14, 122, 18);
		panel.add(jLabelEntrega);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 426, 370, 37);
		panel.add(progressBar);
		
		JButton botonEnviar = new JButton("Enviar");
		botonEnviar.setBounds(10, 504, 89, 23);
		panel.add(botonEnviar);
		
		JButton BotonPedir = new JButton("Pedir");
		BotonPedir.setBounds(135, 504, 89, 23);
		panel.add(BotonPedir);
		
		JButton botonEliminar = new JButton("Eliminar");
		botonEliminar.setBounds(404, 504, 89, 23);
		panel.add(botonEliminar);
		
		

	}
}
