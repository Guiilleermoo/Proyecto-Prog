package ventanas;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ventanaCliente {

	public static void setVisible(boolean b) {
		
		JFrame frame = new JFrame("Demo application");
		frame.setSize(900, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		placeComponents(panel);

		frame.setVisible(true);
		
		ventanaCliente v = new ventanaCliente();
		
	}

	private static void placeComponents(JPanel panel) {
		JLabel gmailLabel = new JLabel("Estan en la pantalla clientes");
		gmailLabel.setBounds(340, 270, 80, 25);
		panel.add(gmailLabel);
	}
	
}
