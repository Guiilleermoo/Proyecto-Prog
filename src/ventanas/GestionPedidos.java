package ventanas;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import es.deusto.prog.III.Pedido;
import es.deusto.prog.III.Pedido.Estado;
import es.deusto.prog.III.BD.GestorBD;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import javax.swing.*;

public class GestionPedidos extends JFrame{
	protected DefaultListModel<Pedido> modeloPreparacion;
	protected JList<Pedido> listaPreparacion;
	protected DefaultListModel<Pedido> modeloListo;
	protected JList<Pedido> listaListo;
	protected DefaultListModel<Pedido> modeloFinalizado;
	protected JList<Pedido> listaFinalizado;
	protected JButton botonPreparacionAListo;
	protected JButton botonListoAPreparacion;
	protected JButton botonListoAFinalizado;
	protected JButton botonFinalizadoAListo;
	
	protected List<Pedido> pedidos;
	protected HashMap<Estado, ArrayList<Pedido>> pedidosPorEstado;
	
	public GestionPedidos(GestorBD gestorBD, String gmail, String contrasena) {
		Container cp = this.getContentPane();
		cp.setLayout(new GridLayout(1, 3));
		
		pedidos = gestorBD.obtenerPedidos();
		
		pedidosPorEstado = new HashMap<>();
		
		this.regenerarPedidosPorEstado();
		
		JPanel preparacion = new JPanel(new BorderLayout());
		JPanel listo = new JPanel(new BorderLayout());
		JPanel finalizado = new JPanel(new BorderLayout());
		
		modeloPreparacion = new DefaultListModel<Pedido>();
		listaPreparacion = new JList(modeloPreparacion);
		listaPreparacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPreparacion = new JScrollPane(listaPreparacion);
		
		botonPreparacionAListo = new JButton(">>");
		
		botonPreparacionAListo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Pedido seleccionado = listaPreparacion.getSelectedValue();
				if (seleccionado != null) {
					Thread hilo = new Thread(new Runnable() {
						
						@Override
						public void run() {
							System.out.print("Preparando pedido");
							gestorBD.log(Level.INFO, "Preparando pedido", null);
//							for (int i = seleccionado.getElementos().size(); i > 0; i--) {
//								try {
//									Thread.sleep(1000);
//								} catch (InterruptedException e) {				
//								}
//							}
							System.out.println("Pedido preparado.");
							gestorBD.log(Level.INFO, "Pedido preparado", null);
							modeloListo.addElement(seleccionado);
							modeloPreparacion.removeElement(seleccionado);
							//gestorBD.actualizarEstadoPedido(seleccionado, Estado.LISTO);
							actualizarBotones();
						}
					});
					hilo.start();
				}
			}
		});
		
		JPanel PreparacionAListo = new JPanel();
		PreparacionAListo.add(botonPreparacionAListo);
		
		preparacion.add(new JLabel("En preparación..."), BorderLayout.NORTH);
		preparacion.add(scrollPreparacion, BorderLayout.CENTER);
		preparacion.add(PreparacionAListo, BorderLayout.SOUTH);
		
		modeloListo = new DefaultListModel<Pedido>();
		listaListo = new JList(modeloListo);
		listaListo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollListo = new JScrollPane(listaListo);
		
		botonListoAPreparacion = new JButton("<<");
		botonListoAFinalizado= new JButton(">>");
		
		botonListoAPreparacion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Pedido seleccionado = listaListo.getSelectedValue();
				if (seleccionado != null) {
					modeloPreparacion.addElement(seleccionado);
					modeloListo.removeElement(seleccionado);			
					//gestorBD.actualizarEstadoPedido(seleccionado, Estado.PREPARACION);
					actualizarBotones();
				}
			}
		});

		botonListoAFinalizado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Pedido seleccionado = listaListo.getSelectedValue();
				if (seleccionado != null) {
					modeloFinalizado.addElement(seleccionado);
					modeloListo.removeElement(seleccionado);			
					//gestorBD.actualizarEstadoPedido(seleccionado, Estado.FINALIZADO);
					actualizarBotones();
				}
			}
		});
		
		JPanel listoAOtros = new JPanel();
		listoAOtros.add(botonListoAPreparacion);
		listoAOtros.add(botonListoAFinalizado);

		listo.add(new JLabel("Listos..."), BorderLayout.NORTH);
		listo.add(scrollListo, BorderLayout.CENTER);
		listo.add(listoAOtros, BorderLayout.SOUTH);
		
		modeloFinalizado = new DefaultListModel<Pedido>();
		listaFinalizado = new JList(modeloFinalizado);
		listaFinalizado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollFinalizado = new JScrollPane(listaFinalizado);
		
		botonFinalizadoAListo = new JButton("<<");
		
		botonFinalizadoAListo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Pedido seleccionado = listaFinalizado.getSelectedValue();
				if (seleccionado != null) {
					modeloListo.addElement(seleccionado);
					modeloFinalizado.removeElement(seleccionado);			
					//gestorBD.actualizarEstadoPedido(seleccionado, Estado.LISTO);
					actualizarBotones();
				}
			}
		});
		
		JPanel finalizadoAListo = new JPanel();
		finalizadoAListo.add(botonFinalizadoAListo);

		finalizado.add(new JLabel("Finalizados..."), BorderLayout.NORTH);
		finalizado.add(scrollFinalizado, BorderLayout.CENTER);	
		finalizado.add(finalizadoAListo, BorderLayout.SOUTH);	
		
		cp.add(preparacion);
		cp.add(listo);
		cp.add(finalizado);
		
		this.setTitle("Preparación de pedidos");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setVisible(false);
	}
	
	public void recargarModelos(GestorBD gestorBD) {
		modeloPreparacion.removeAllElements();
		for (Pedido pedido : this.getPedidosPorEstado().get(Estado.PREPARACION)) {
			modeloPreparacion.addElement(pedido);
		}
		modeloListo.removeAllElements();
		for (Pedido pedido : this.getPedidosPorEstado().get(Estado.LISTO)) {
			modeloListo.addElement(pedido);
		}
		modeloFinalizado.removeAllElements();
		for (Pedido pedido : this.getPedidosPorEstado().get(Estado.FINALIZADO)) {
			modeloFinalizado.addElement(pedido);
		}
		actualizarBotones();
	}

	public void actualizarBotones() {
		if (modeloPreparacion.isEmpty()) {
			botonPreparacionAListo.setEnabled(false);
		} else {
			botonPreparacionAListo.setEnabled(true);
		}
		
		if (modeloListo.isEmpty()) {
			botonListoAPreparacion.setEnabled(false);
			botonListoAFinalizado.setEnabled(false);
		} else {
			botonListoAPreparacion.setEnabled(true);
			botonListoAFinalizado.setEnabled(true);
		}
		
		if (modeloFinalizado.isEmpty()) {
			botonFinalizadoAListo.setEnabled(false);
		} else {
			botonFinalizadoAListo.setEnabled(true);
		}
	}

	public HashMap<Estado, ArrayList<Pedido>> getPedidosPorEstado() {
		return pedidosPorEstado;
	}

	
	public void actualizarEstadoPedido(Pedido pedido, Estado estado) {
		int posicion = pedidos.lastIndexOf(pedido);
		pedidosPorEstado.get(pedido.getEstado()).remove(pedido);
		pedidosPorEstado.get(estado).add(pedido);
		pedidos.get(posicion).setEstado(estado);
	}
	
	public void regenerarPedidosPorEstado() {
		for (Estado estado : Estado.values()) {
			this.pedidosPorEstado.put(estado, new ArrayList<Pedido>());
		}
		for (Pedido pedido : pedidos) {
			this.pedidosPorEstado.get(pedido.getEstado()).add(pedido);
		}
	}

}
