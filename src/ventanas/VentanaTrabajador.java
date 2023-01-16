package ventanas;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import es.deusto.prog.III.Trabajador.Estatus;
import es.deusto.prog.III.BD.GestorBD;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;

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
		getContentPane().setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton botonEmpleados = new JButton("Gestion Empleados");
		botonEmpleados.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		botonEmpleados.setBackground(new Color(0, 191, 255));
		panel.add(botonEmpleados, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton botonPedidos = new JButton("Gestion Pedidos");
		botonPedidos.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		botonPedidos.setBackground(new Color(152, 251, 152));
		panel_1.add(botonPedidos, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JButton botonProductos = new JButton("Gestion Productos");
		botonProductos.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		botonProductos.setBackground(new Color(127, 255, 212));
		panel_2.add(botonProductos, BorderLayout.CENTER);
		
		
		
		
		
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
		this.setIconImage(new ImageIcon("data/logo.png").getImage());
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	

}
