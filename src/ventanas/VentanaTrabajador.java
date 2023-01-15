package ventanas;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import es.deusto.prog.III.Trabajador.Estatus;
import es.deusto.prog.III.BD.GestorBD;

public class VentanaTrabajador extends JFrame {
	protected GestorBD gestorBD;
	
	// Guardamos el gmail y contrasena en variables
	protected String gmail;
	protected String contrasena;
	
	protected GestionEmpleados gestionEmpleados;
	protected GestionProductos gestionProductos;
	protected GestionPedidos gestionPedidos;
	
	public VentanaTrabajador(GestorBD gestorBD, String gmail, String contrasena) {
		this.gestorBD = gestorBD;
		this.gmail = gmail;
		this.contrasena = contrasena;
		
		gestionPedidos = new GestionPedidos(gestorBD, gmail, contrasena);
		gestionEmpleados = new GestionEmpleados(gestorBD, gmail, contrasena);
		gestionProductos = new GestionProductos(gestorBD, gmail, contrasena);
		
		Container cp = this.getContentPane();
		
		JPanel centro = new JPanel();

		JButton botonPedidos = new JButton("Gestionar Pedidos");
		JButton botonEmpleados = new JButton("Gestionar Empleados");
		JButton botonProductos = new JButton("Gestionar Existencias");
		
		cp.add(centro , BorderLayout.CENTER);
		
		centro.setLayout(new GridLayout(1,3));
		
		centro.add(botonPedidos);
		centro.add(botonEmpleados);
		centro.add(botonProductos);
		
		botonEmpleados.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gestorBD.getTrabajadorByGmail(gmail).getStatus() == Estatus.JEFE) {
					gestionEmpleados.setVisible(true);
				}
			}
		});
		
		botonProductos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					gestionProductos.setVisible(true);
			}
		});
		
		botonPedidos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gestionPedidos.setVisible(true);
			}
		});
		
		// ventana est�ndar
		this.setTitle("Menu");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	

}
