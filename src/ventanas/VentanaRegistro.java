package ventanas;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import es.deusto.prog.III.Cliente;
import es.deusto.prog.III.BD.GestorBD;

public class VentanaRegistro extends JFrame {
	protected GestorBD gestorBD;

	public VentanaRegistro(GestorBD gestorBD) {
		this.gestorBD = gestorBD;
		
		Container panel = this.getContentPane();
		panel.setLayout(null);
		panel.setBackground(Color.MAGENTA);
		
		// Creacion de los botones
		JLabel registro = new JLabel("REGISTRO:");
		registro.setBounds(200, 50, 80, 25);
		panel.add(registro);
		
		JLabel nombreLabel = new JLabel("Nombre y");
		nombreLabel.setBounds(100, 85, 80, 25);
		panel.add(nombreLabel);
		
		JLabel apellidoLabel = new JLabel("Apellidos: ");
		apellidoLabel.setBounds(100, 100, 80, 25);
		panel.add(apellidoLabel);
		
		JTextField nombreYApellidoText = new JTextField(30);
		nombreYApellidoText.setBounds(200, 100, 300, 30);
		panel.add(nombreYApellidoText);
		
		JLabel gmailLabel = new JLabel("Gmail: ");
		gmailLabel.setBounds(100, 175, 80, 25);
		panel.add(gmailLabel);
		
		JTextField gmailText = new JTextField(30);
		gmailText.setBounds(200, 175, 300, 30);
		panel.add(gmailText);
		
		JLabel contrasenaLabel = new JLabel("Contrasena: ");
		contrasenaLabel.setBounds(100, 250, 80, 25);
		panel.add(contrasenaLabel);
		
		JTextField contrasenaText = new JTextField(30);
		contrasenaText.setBounds(200, 250, 300, 30);
		panel.add(contrasenaText);
		
		JLabel direccionLabel = new JLabel("Direccion: ");
		direccionLabel.setBounds(100, 325, 80, 25);
		panel.add(direccionLabel);
		
		JTextField direccionText = new JTextField(30);
		direccionText.setBounds(200, 325, 300, 30);
		panel.add(direccionText);
		
		JLabel telefonoLabel = new JLabel("Telefono: ");
		telefonoLabel.setBounds(100, 400, 80, 25);
		panel.add(telefonoLabel);
		
		JTextField telefonoText = new JTextField(30);
		telefonoText.setBounds(200, 400, 300, 30);
		panel.add(telefonoText);
		
		JButton registroboton = new JButton("Registrar");
		registroboton.setBounds(100, 450, 100, 25);
		panel.add(registroboton);
		
		JButton volverboton = new JButton("Volver");
		volverboton.setBounds(350, 450, 100, 25);
		panel.add(volverboton);
		
		registroboton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
			if (nombreYApellidoText.getText().isEmpty() || gmailText.getText().isEmpty() || contrasenaText.getText().isEmpty() || direccionText.getText().isEmpty() || telefonoText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Error: completa los campos vacios");
			} else if (telefonoText.getText().length() < 9 || telefonoText.getText().length() > 9) {
				JOptionPane.showMessageDialog(null, "Error: teléfono no válido");
			} else {
				String nombreYApellidos = nombreYApellidoText.getText();
				String gmail = gmailText.getText();
				String contrasena = contrasenaText.getText();
				String direccion = direccionText.getText();
				String telefono = telefonoText.getText();
				
				Cliente nuevo = new Cliente(nombreYApellidos, gmail, contrasena, direccion, telefono);
				gestorBD.insertarClientes(nuevo);
			}
			}
		});
		
		volverboton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		// ventana estándar
		this.setTitle("Registro");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}
	
}
