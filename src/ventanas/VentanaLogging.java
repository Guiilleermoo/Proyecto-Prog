package ventanas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import es.deusto.prog.III.Cliente;

public class VentanaLogging extends JFrame{
			
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

		//JLabel lbImage = new JLabel();
		/**ImageIcon img = new ImageIcon(Cliente.class.getResource("/data/portada.jpg"));
		ImageIcon imgIcon = new ImageIcon(getClass().getResource("/informatica.gif"));
        Image imgEscalada = img.getImage().getScaledInstance(lbImage.getWidth(), lbImage.getHeight(), Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        lbImage.setIcon(iconoEscalado);**/
		
		panel.setLayout(null);
		
		
		//lbImage.setIcon(new ImageIcon("/data/portada.jpg"));
		
		//lbImage.setBounds(340, 270, 80, 25);
		//panel.add(lbImage);

		JLabel foto = new JLabel();
		foto.setIcon(new ImageIcon("data/portada.jpg"));
		foto.setBounds(0, 0, 80, 25);
		
		JLabel gmailLabel = new JLabel("Gmail");
		gmailLabel.setBounds(340, 270, 80, 25);
		panel.add(gmailLabel);

		JTextField gmailText = new JTextField(30);
		gmailText.setBounds(430, 270, 160, 25);
		panel.add(gmailText);

		JLabel contraseñaLabel = new JLabel("Contraseña");
		contraseñaLabel.setBounds(340, 300, 80, 25);
		panel.add(contraseñaLabel);

		JPasswordField contraseñaText = new JPasswordField(20);
		contraseñaText.setBounds(430, 300, 160, 25);
		panel.add(contraseñaText);

		JButton accesoButton = new JButton("Acceder");
		accesoButton.setBounds(340, 340, 120, 25);
		panel.add(accesoButton);
		
		JButton registroButton = new JButton("Registarse");
		registroButton.setBounds(490, 340, 120, 25);
		panel.add(registroButton);
		
		
		accesoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Cliente cliente: clientes)  {
					if (gmailText.toString() == cliente.getGmail() && contraseñaText.toString() == cliente.getContraseña()) {
						ventanaCliente.setVisible(true);
					} else {
						System.err.println("Error: gmail y/o contraseña incorrectos");
					}
				}
				
			}
		
		});
		
	
	
	}

	
}