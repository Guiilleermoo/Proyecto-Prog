package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.deusto.prog.III.Cliente;

public class TestCliente {

	Cliente cliente;
	int id = 0;
	String nombreYApellidos = "NombreYApellidos";
	String gmail = "gmail";
	String contrasena = "contrasena";
	String direccion = "direccion";
	String telefono = "666444555";
	
	
	@Before
	public void setUp() throws Exception {
		cliente = new Cliente();
		cliente.setNombreYApellidos(nombreYApellidos);
		cliente.setGmail(gmail);
		cliente.setContrasena(contrasena);
		cliente.setDireccion(direccion);
		cliente.setTelefono(telefono);
	}

	@Test
	public void testCliente() {
		cliente = new Cliente(nombreYApellidos, gmail, contrasena, direccion, telefono);
	}

	@Test
	public void testGetNombreYApellidos() {
		assertEquals(cliente.getNombreYApellidos(), nombreYApellidos);
	}

	@Test
	public void testSetNombreYApellidos() {
		String nombreYApellidos1 = "nombreYApellidos1";
		cliente.setNombreYApellidos(nombreYApellidos1);
		assertEquals(cliente.getNombreYApellidos(), nombreYApellidos1);
	}

	@Test
	public void testGetGmail() {
		assertEquals(cliente.getGmail(), gmail);
	}

	@Test
	public void testSetGmail() {
		String gmail1 = "gmail1";
		cliente.setGmail(gmail1);
		assertEquals(cliente.getGmail(), gmail1);
	}

	@Test
	public void testGetContrasena() {
		assertEquals(cliente.getContrasena(), contrasena);
	}

	@Test
	public void testSetContrasena() {
		String contrasena1 = "contrasena1";
		cliente.setContrasena(contrasena1);
		assertEquals(cliente.getContrasena(), contrasena1);
	}

	@Test
	public void testGetDireccion() {
		assertEquals(cliente.getDireccion(), direccion);
	}

	@Test
	public void testSetDireccion() {
		String direccion1 = "direccion1";
		cliente.setDireccion(direccion1);
		assertEquals(cliente.getDireccion(), direccion1);
	}

	@Test
	public void testGetTelefono() {
		assertEquals(cliente.getTelefono(), telefono);
	}

	@Test
	public void testSetTelefono() {
		String telefono1 = "666444555";
		if (telefono.length() < 9 || telefono.length() > 9) {
			System.err.println("Error guardando el teléfono");
		} else {
			cliente.setTelefono(telefono1);
		}
		assertEquals(cliente.getTelefono(), telefono1);
	}

	@Test
	public void testToString() {
		String salida = "Cliente [ID = " + -1 + " nombreYApellidos=" + nombreYApellidos + ",gmail=" + gmail + ", contrasena=" + contrasena + ", direccion=" + direccion + ", telefono=" + telefono + "]";
		assertEquals(cliente.toString(), salida);
	}

}
