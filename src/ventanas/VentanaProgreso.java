package ventanas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.deusto.prog.III.BD.GestorBD;


public class VentanaProgreso extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JProgressBar progreso;
	protected JButton compra;
	protected GestorBD gestorBD;
		
		public VentanaProgreso(GestorBD gestorBD) {
			this.gestorBD = gestorBD;
							
			Container cp = this.getContentPane();
			
			JPanel arriba = new JPanel();
			JPanel abajo = new JPanel();
			
			cp.add(arriba, BorderLayout.CENTER);
			cp.add(abajo, BorderLayout.SOUTH);
			
			
			progreso = new JProgressBar(0, 100);
			compra = new JButton("Finalizar Compra");
			progreso.setStringPainted(true);
			
			arriba.add(progreso);
			abajo.add(compra);
			
			
			
			compra.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Thread hilo = new Thread(new Runnable() {
						@Override
						public void run() {
							double n = 0.0;
							System.out.println("Empiezo el hilo...");
							for (int i = 0; i < 100; i++) {
								n = Math.pow(2, i);
								progreso.setValue(i+1);
								try {
									Thread.sleep(60);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							System.out.println("He terminado");
						}
					});
						hilo.start();
						
					}
				});
			
			progreso.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					if(progreso.getValue() == 100) {
						JOptionPane.showMessageDialog(VentanaProgreso.this, "Compra Finalizada con exito", "Atencion", JOptionPane.INFORMATION_MESSAGE);
						System.out.println("Se ha comprobado el usuario con exito");
						try {
							Thread.sleep(10);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						progreso.setValue(0);
						setVisible(false);
						
					}
					
				}
			});
			
			this.setTitle("Confirmacion");
			this.pack();
			this.setDefaultCloseOperation(HIDE_ON_CLOSE);
			this.setVisible(false);
		}
	}


