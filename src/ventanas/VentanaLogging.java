package ventanas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.deusto.prog.III.Cliente;

public class VentanaLogging extends JFrame{
	protected JTextField gmail;
	protected JTextField contraseña;
	protected JButton entrar;
	
	public VentanaLogging() {
		Container cp = this.getContentPane();
		
		
		//JTextField/JButton
		gmail = new JTextField();
		contraseña = new JTextField();
		entrar = new JButton("Enviar");
				
		//Jpanel izquierda/derecha
		JPanel arriba = new JPanel();
		JPanel abajo = new JPanel();
		
		
		JLabel foto = new JLabel();
		foto.setIcon(new ImageIcon("data/portada.jpg"));
		arriba.add(foto);
		
		
		abajo.setLayout(new GridLayout(3,2));
		
		abajo.add(new JLabel("gmail:"));
		abajo.add(gmail);
		abajo.add(new JLabel("contraseña:"));
		abajo.add(contraseña);
		abajo.add(entrar);
		
		cp.setLayout(new GridLayout(2,1));
		cp.add(arriba);
		cp.add(abajo);
		
		//ActionListener
		/*entrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Cliente cliente: clientes)  {
					if (gmail == cliente.getGmail() && contraseña == cliente.getContraseña()) {
						ventanaCliente.setVisible(true);
					} else {
						System.err.println("Error: gmail y/o contraseña incorrectos");
					}
				}
				
			}
		});*/
		
		// ventana estándar
				this.setTitle("Logging");
				this.setSize(1920, 1080);
				this.setDefaultCloseOperation(EXIT_ON_CLOSE);
				this.setVisible(true);
	}
	
	public static void main(String[] args) {
		VentanaLogging v = new VentanaLogging();
	}
	
}
