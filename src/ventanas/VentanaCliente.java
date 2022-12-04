package ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import es.deusto.prog.III.BD.GestorBD;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Vector;

public class VentanaCliente extends JFrame{
	protected GestorBD gestorBD;

	public VentanaCliente(GestorBD gestorBD) {
		this.gestorBD = gestorBD;
		
		
		// ventana estándar
		this.setTitle("Cliente");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(false);

	}
}