package ventanas;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import es.deusto.prog.III.Producto;
import es.deusto.prog.III.BD.GestorBD;

public class VentanaProgreso extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JProgressBar progreso;
	protected JButton compra;
	protected GestorBD gestorBD;
		
		public VentanaProgreso(GestorBD gestorBD, List<Producto> productosComprados) {
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
							gestorBD.log(Level.INFO, "Ha empezado la compra", null);
							for (int i = 0; i < 100; i++) {
								progreso.setValue(i+1);
								try {
									Thread.sleep(60);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							gestorBD.log(Level.INFO, "La compra ha finalizado", null);
						}
					});
						hilo.start();
					}
				});
			
			progreso.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					if(progreso.getValue() == 100) {
						factura(productosComprados);
						JOptionPane.showMessageDialog(VentanaProgreso.this, "Compra Finalizada con exito, revise su factura.", "Atencion", JOptionPane.INFORMATION_MESSAGE);
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
			this.setVisible(true);
		}
		
		private void factura(List<Producto> productos) {
				try (PrintWriter out = new PrintWriter("factura.txt")){
					out.println("Productos Comprados: ");
					for (Producto p : productos) {
						out.println(new Producto(p.getArticulo(), p.getDeporte(), p.getMarca(), p.getGenero(), p.getTalla(), p.getPrecio()));
					}
					out.println("Total: " + calcularTotal(productos) + "");
				} catch (Exception e) {
					gestorBD.log(Level.INFO, "Error exportando datos", e);
				}
		}
		
		private Double calcularTotal(List<Producto> productos) {
			Double total = 0.0;
			for (Producto p : productos) {
				total += p.getPrecio();
			}
			return total;
		}
}