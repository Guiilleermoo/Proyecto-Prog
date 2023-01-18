package ventanas;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import es.deusto.prog.III.Producto;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Caret;

public class VentanaRecursividad extends JFrame{
	
	protected double importe;
	private JTextField textField;
	
	public VentanaRecursividad(List<List<Producto>> comprasPosibles, Double importe2) {
		
		this.importe = importe2;
		
		JLabel lblNewLabel = new JLabel("Tus posibles compras con " + importe2 + " €");
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
	
		DefaultListModel<String> modeloListaRecursivad = new DefaultListModel<String>();
		JList<String> listaRecursividad = new JList<String>(modeloListaRecursivad);
		JScrollPane scrollRecursividad = new JScrollPane(listaRecursividad);
		getContentPane().add(scrollRecursividad, BorderLayout.CENTER);
		
		
		for (List<Producto> list : comprasPosibles) {
			String string = "";
			for (Producto list2 : list) {
				string = string + list2.toString() + " ";
			}
			modeloListaRecursivad.addElement(string);
			
		}
		
		this.setTitle("Recursividad");
		this.setSize(800, 600);
		this.setIconImage(new ImageIcon("data/logo.png").getImage());
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		
		
	}
}
