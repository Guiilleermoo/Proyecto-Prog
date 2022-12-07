package ventanas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.deusto.prog.III.BD.GestorBD;

public class VentanaTrabajador extends JFrame {
	protected GestorBD gestorBD;
	protected GestionEmpleados gestion1;
	
	public VentanaTrabajador(GestorBD gestorBD) {
		this.gestorBD = gestorBD;
		gestion1 = new GestionEmpleados(gestorBD);
		Container cp = this.getContentPane();
		
		JPanel centro = new JPanel();

		
		JButton gestionEmpleados = new JButton("Gestionar Empleados");
		JButton gestionStock = new JButton("Gestionar Existencias");
		
		cp.add(centro , BorderLayout.CENTER);
		
		centro.setLayout(new GridLayout(1,2));
		
		centro.add(gestionEmpleados);
		centro.add(gestionStock);
		
		gestionEmpleados.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gestion1.setVisible(true);
				
			}
		});
		
		// ventana estándar
		this.setTitle("Menu");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}
	
}
