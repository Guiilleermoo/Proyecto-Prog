package ventanas;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import es.deusto.prog.III.Cliente;
import es.deusto.prog.III.BD.GestorBD;
import java.awt.Font;

public class VentanaRegistro extends JFrame {
	protected GestorBD gestorBD;

	public VentanaRegistro(GestorBD gestorBD) {
		this.gestorBD = gestorBD;
		
		Container panel = this.getContentPane();
		panel.setLayout(null);
		panel.setBackground(new Color(245, 240, 190));
		
		// Creacion de los botones
		JLabel registro = new JLabel("REGISTRO:");
		registro.setFont(new Font("Gill Sans Nova Light", Font.BOLD | Font.ITALIC, 20));
		registro.setBounds(200, 50, 204, 25);
		panel.add(registro);
		
		JLabel nombreLabel = new JLabel("Nombre y");
		nombreLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		nombreLabel.setBounds(100, 87, 80, 25);
		panel.add(nombreLabel);
		
		JLabel apellidoLabel = new JLabel("Apellidos: ");
		apellidoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		apellidoLabel.setBounds(100, 105, 80, 25);
		panel.add(apellidoLabel);
		
		JTextField nombreYApellidoText = new JTextField(30);
		nombreYApellidoText.setBounds(200, 100, 300, 30);
		panel.add(nombreYApellidoText);
		
		JLabel gmailLabel = new JLabel("Gmail: ");
		gmailLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		gmailLabel.setBounds(100, 175, 80, 25);
		panel.add(gmailLabel);
		
		JTextField gmailText = new JTextField(30);
		gmailText.setBounds(200, 175, 300, 30);
		panel.add(gmailText);
		
		JLabel contrasenaLabel = new JLabel("Contrasena: ");
		contrasenaLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		contrasenaLabel.setBounds(100, 250, 90, 25);
		panel.add(contrasenaLabel);
		
		JPasswordField contrasenaText = new JPasswordField(30);
		contrasenaText.setBounds(200, 250, 300, 30);
		panel.add(contrasenaText);
		
		JLabel direccionLabel = new JLabel("Direccion: ");
		direccionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		direccionLabel.setBounds(100, 325, 80, 25);
		panel.add(direccionLabel);
		
		JTextField direccionText = new JTextField(30);
		direccionText.setBounds(200, 325, 300, 30);
		panel.add(direccionText);
		
		JLabel telefonoLabel = new JLabel("Telefono: ");
		telefonoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		telefonoLabel.setBounds(100, 400, 80, 25);
		panel.add(telefonoLabel);
		
		JTextField telefonoText = new JTextField(30);
		telefonoText.setBounds(200, 400, 300, 30);
		panel.add(telefonoText);
		
		JButton registroboton = new JButton("Registrar");
		registroboton.setBackground(new Color(192, 181, 255));
		registroboton.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		registroboton.setBounds(223, 450, 117, 25);
		panel.add(registroboton);
		
		JButton volverboton = new JButton("Volver");
		volverboton.setBackground(new Color(192, 181, 255));
		volverboton.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		volverboton.setBounds(378, 450, 100, 25);
		panel.add(volverboton);
		
		registroboton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
			if (nombreYApellidoText.getText().isEmpty() || gmailText.getText().isEmpty() || String.valueOf(contrasenaText.getPassword()).isEmpty() || direccionText.getText().isEmpty() || telefonoText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Error: completa los campos vacios");
			} else if (telefonoText.getText().length() < 9 || telefonoText.getText().length() > 9) {
				JOptionPane.showMessageDialog(null, "Error: tel�fono no v�lido");
			} else {
				String nombreYApellidos = nombreYApellidoText.getText();
				String gmail = gmailText.getText();
				String contrasena = String.valueOf(contrasenaText.getPassword());
				String direccion = direccionText.getText();
				String telefono = telefonoText.getText();
				
				Cliente nuevo = new Cliente(nombreYApellidos, gmail, contrasena, direccion, telefono);
				nuevo.setId(gestorBD.getLastIdCliente() + 1);
				gestorBD.insertarClientes(nuevo);
				
				dispose();
			}
			}
		});
		
		volverboton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		// ventana est�ndar
		this.setTitle("Registro");
		this.setSize(800, 600);
		this.setIconImage(new ImageIcon("data/logo.png").getImage());
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}
	
}
