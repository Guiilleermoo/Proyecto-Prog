package test;

import static org.junit.Assert.*;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import es.deusto.prog.III.*;
import es.deusto.prog.III.Producto.Genero;
import es.deusto.prog.III.Ropa;
import es.deusto.prog.III.Ropa.Talla;
import es.deusto.prog.III.Calzado;

import es.deusto.prog.III.Pedido;
import es.deusto.prog.III.Pedido.Estado;

public class TestPedido {

	Calzado calzado = new Calzado("deporte_calzado", "marca_calzado", Genero.HOMBRE, 0, "articulo_calzado", 38);
	Ropa ropa = new Ropa("deporte_ropa", "marca_ropa", Genero.HOMBRE, 0, "articulo_ropa", Talla.M);
	
	private Pedido pedido;
	private String cliente = "cliente";
	private Date fecha = new Date(System.currentTimeMillis());
	private ArrayList<Producto> productos = new ArrayList<Producto>();
	private Estado estado = Estado.FINALIZADO;
	private float precio = 0;
	
	
	@Before
	public void setUp() throws Exception {
		productos.add(calzado);
		productos.add(ropa);
		pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setFecha(fecha);
		pedido.setProductos(productos);
		pedido.setEstado(estado);		
	}

	@Test
	public void testPedido() {
		pedido = new Pedido(cliente, fecha,  productos, estado);
		assertEquals(pedido.getCliente(), cliente);
		assertEquals(pedido.getFecha(), fecha);
		assertEquals(pedido.getProductos(), productos);
		assertEquals(pedido.getEstado(), estado);
	}

	@Test
	public void testGetCliente() {
		assertEquals(pedido.getCliente(), cliente);
	}

	@Test
	public void testSetCliente() {
		String cliente1 = "cliente1";
		pedido.setCliente(cliente1);
		assertEquals(pedido.getCliente(), cliente1);
	}

	@Test
	public void testGetFecha() {
		assertEquals(pedido.getFecha(), fecha);
	}

	@Test
	public void testSetFecha() {
		Date fecha1 = new Date(System.currentTimeMillis());
		pedido.setFecha(fecha1);
		assertEquals(pedido.getFecha(), fecha1);
	}

	@Test
	public void testGetProductos() {
		assertEquals(pedido.getProductos(), productos);
	}

	@Test
	public void testSetProductos() {
		Ropa ropa1 = new Ropa("deporte_ropa1", "marca_ropa1", Genero.HOMBRE, 1, "articulo_ropa1", Talla.L);
		ArrayList<Producto> productos1 = new ArrayList<Producto>();
		productos1.add(ropa1);
		pedido.setProductos(productos1);
		assertEquals(pedido.getProductos(), productos1);
	}

	@Test
	public void testGetEstado() {
		assertEquals(pedido.getEstado(), estado);
	}

	@Test
	public void testSetEstado() {
		Estado estado1 = Estado.PREPARACION;
		pedido.setEstado(estado1);
		assertEquals(pedido.getEstado(), estado1);
	}

	@Test
	public void testGetPrecio() {
		
		for (Producto producto : productos) {
			precio += producto.getPrecio();
		}
		assertEquals(pedido.getPrecio(), precio, 0.1);
	}

	@Test
	public void testToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("DD//MM/YYYY");
		String salida = "Pedido de " + cliente + ", " + sdf.format(fecha) + ", " + pedido.getPrecio() + " euros (" + estado + ")";
		assertEquals(salida, pedido.toString());
	}

}
