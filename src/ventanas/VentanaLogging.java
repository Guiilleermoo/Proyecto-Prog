package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.System.Logger;

import javax.swing.*;

import es.deusto.prog.III.Cliente;
import es.deusto.prog.III.Producto;
import es.deusto.prog.III.Trabajador;
import es.deusto.prog.III.Trabajador.Estatus;
import es.deusto.prog.III.BD.GestorBD;

public class VentanaLogging extends JFrame{
	
	protected static GestorBD gestorBD;
	
	// Guardamos el gmail y contrasena en variables
	protected String gmail;
	protected String contrasena;
	
	// Ventanas Secundarias
	protected VentanaRegistro ventanaRegistro;
	
	public VentanaLogging() {
		gestorBD = new GestorBD();
		
		// Ventanas Secundarias (inicializacion)
		ventanaRegistro = new VentanaRegistro(gestorBD);
		
		Container panel = this.getContentPane();
		panel.setLayout(null);
		panel.setBackground(new Color(183, 255, 183));
		
		// Creacion de los botones
		JLabel foto = new JLabel();
		foto.setIcon(new ImageIcon("data/portada.jpg"));
		foto.setBounds(50, 20, 700, 390);
		panel.add(foto);
		
		JLabel gmailLabel = new JLabel("Gmail");
		gmailLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		gmailLabel.setBounds(200, 450, 80, 25);
		panel.add(gmailLabel);
		
		JTextField gmailText = new JTextField(30);
		gmailText.setBounds(290, 450, 160, 25);
		panel.add(gmailText);
		
		JLabel contrasenaLabel = new JLabel("Contrasena");
		contrasenaLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		contrasenaLabel.setBounds(200, 480, 80, 25);
		panel.add(contrasenaLabel);
		
		JPasswordField contrasenaText = new JPasswordField(20);
		contrasenaText.setBounds(290, 480, 160, 25);
		panel.add(contrasenaText);
		
		JButton accesoButton = new JButton("Acceder");
		accesoButton.setBackground(new Color(181, 218, 255));
		accesoButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		accesoButton.setBounds(200, 520, 120, 25);
		panel.add(accesoButton);
		
		JButton registroButton = new JButton("Registarse");
		registroButton.setBackground(new Color(181, 218, 255));
		registroButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		registroButton.setBounds(350, 520, 120, 25);
		panel.add(registroButton);
		
		// Crear los JRadioButton de cliente/trabajador y a�adirlos a un grupo de botones
		JRadioButton cliente = new JRadioButton("Cliente", true);
		cliente.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		JRadioButton trabajador = new JRadioButton("Trabajador", false);
		trabajador.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		ButtonGroup jbg = new ButtonGroup();
		jbg.add(cliente);
		jbg.add(trabajador);
		panel.add(cliente);
		cliente.setBounds(290, 420, 70, 25);
		panel.add(trabajador);
		trabajador.setBounds(360, 420, 90, 25);
		
		accesoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				gmail = gmailText.getText();
				contrasena = String.valueOf(contrasenaText.getPassword());
				// Trabajador: hulk@gmail.com, NUcRn8h85RZZTjg6UBwa 
				// Cliente: guille, 123
				
				// Comprobar si el gmail/contrasena ingresado es de un cliente
				if (cliente.isSelected()) {
					if (gestorBD.comprobarCliente(gmail, contrasena)) {
						VentanaCliente ventanaCliente = new VentanaCliente(gestorBD, gmail, contrasena);
					}
				}
				
				// Comprobar si el gmail/contrasena ingresado es de un trabajador
				if (trabajador.isSelected()) {
					if (gestorBD.comprobarTrabajador(gmail, contrasena)) {
						VentanaTrabajador ventanaTrabajador = new VentanaTrabajador(gestorBD, gmail, contrasena);
					}
				}
			}
			
		});
		
		contrasenaText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					accesoButton.doClick();
				}
				
			}

		});
		
		gmailText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					accesoButton.doClick();
				}
				
			}

		});
		
		registroButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Hace visible la ventanaRegistro para registrar un nuevo cliente
				ventanaRegistro.setVisible(true);
			}
		});
		
		// ventana est�ndar
		this.setTitle("Ventana Logging");
		this.setSize(800, 600);
		this.setIconImage(new ImageIcon("data/logo.png").getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	public static void main(String[] args) {
		VentanaLogging v = new VentanaLogging();
	}
}
