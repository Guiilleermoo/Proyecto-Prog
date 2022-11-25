package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import es.deusto.prog.III.Cliente;
import es.deusto.prog.III.GestorBD;
import es.deusto.prog.III.Producto;

public class VentanaLogging extends JFrame{
			
	protected static GestorBD gestorBD;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Demo application");
		frame.setSize(900, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		placeComponents(panel);

		frame.setVisible(true);	
	}
	

	private static void placeComponents(JPanel panel) {
		
		panel.setLayout(null);

		JLabel foto = new JLabel();
		foto.setIcon(new ImageIcon("data/portada.jpg"));
		foto.setBounds(0, 0, 80, 25);
		
		JLabel gmailLabel = new JLabel("Gmail");
		gmailLabel.setBounds(340, 270, 80, 25);
		panel.add(gmailLabel);

		JTextField gmailText = new JTextField(30);
		gmailText.setBounds(430, 270, 160, 25);
		panel.add(gmailText);

		JLabel contrasenaLabel = new JLabel("Contraseña");
		contrasenaLabel.setBounds(340, 300, 80, 25);
		panel.add(contrasenaLabel);

		JPasswordField contrasenaText = new JPasswordField(20);
		contrasenaText.setBounds(430, 300, 160, 25);
		panel.add(contrasenaText);

		JButton accesoButton = new JButton("Acceder");
		accesoButton.setBounds(340, 340, 120, 25);
		panel.add(accesoButton);
		
		JButton registroButton = new JButton("Registarse");
		registroButton.setBounds(490, 340, 120, 25);
		panel.add(registroButton);
		
		
		accesoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String gmail = gmailText.getText();
				String contrasena = contrasenaText.getText();
				
				if(gestorBD.comprobarCliente(gmail, contrasena)) {
					ventanaCliente.setVisible(true);
				} else if (gestorBD.comprobarTrabajador(gmail, contrasena)) {
					//ventanaTrabajador.setVisible(true);
				} else {
					JOptionPane.showInputDialog("Error: gmail y/o contraseña incorrectos");
				}
			}
		
		});
		
	}
}
