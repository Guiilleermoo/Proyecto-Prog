package ventanas;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.deusto.prog.III.BD.GestorBD;

public class VentanaTrabajador extends JFrame {
	protected GestorBD gestorBD;
	
	public VentanaTrabajador(GestorBD gestorBD) {
		this.gestorBD = gestorBD;
		
		// ventana estándar
		this.setTitle("Trabajador");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setVisible(false);
	}
	
}
