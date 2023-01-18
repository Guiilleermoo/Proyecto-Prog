package test;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.deusto.prog.III.Cliente;
import es.deusto.prog.III.Pedido;
import es.deusto.prog.III.Pedido.Estado;
import es.deusto.prog.III.Producto;
import es.deusto.prog.III.Producto.Genero;
import es.deusto.prog.III.Trabajador;
import es.deusto.prog.III.BD.GestorBD;
import es.deusto.prog.III.Trabajador.Estatus;

public class TestGestorBD {
	GestorBD gestorBD = new GestorBD();

	@Test
	public void testGetClienteByNombreYApellidos() {
		Cliente clientePrueba = new Cliente("Pruebas", "1", "1", "Deusto", "666333444");
		clientePrueba.setId(5);
		assertEquals(clientePrueba.toString(), gestorBD.getClienteByNombreYApellidos("Pruebas").toString());
	}

	@Test
	public void testComprobarCliente() {
		String gmail = "1";
		String contrasena = "1";
		assertTrue(gestorBD.comprobarCliente(gmail, contrasena));
	}

	@Test
	public void testObtenerTrabajadores() {
		assertTrue(gestorBD.obtenerTrabajadores().size() == 3);
	}

	@Test
	public void testGetTrabajadorByGmail() {
		Trabajador trabajadorPrueba = new Trabajador("Pruebas", "1", "1", Estatus.JEFE, 3800.0, "678987124");
		trabajadorPrueba.setId(5);
		String gmail = "1";
		assertEquals(trabajadorPrueba.toString(), gestorBD.getTrabajadorByGmail(gmail).toString());
	}

	@Test
	public void testComprobarTrabajador() {
		String gmail = "1";
		String contrasena = "1";
		assertTrue(gestorBD.comprobarTrabajador(gmail, contrasena));
	}

	@Test
	public void testObtenerProductosTodos() {
		assertTrue(gestorBD.obtenerProductosTodos().size() == 38);
	}

	@Test
	public void testGetProductoById() {
		Producto productoPrueba = new Producto("Zapatillas", "Running", "Adidas", Genero.MUJER, "40", 100.0, 70);
		assertEquals(productoPrueba.toString(), gestorBD.getProductoById(19).toString());
		
	}
}
