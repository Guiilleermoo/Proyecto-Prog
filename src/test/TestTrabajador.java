package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.deusto.prog.III.Trabajador;
import es.deusto.prog.III.Trabajador.Estatus;

public class TestTrabajador {

	Trabajador trabajador;
	int id = 0;
	String nombreYApellidos = "NombreYApellidos";
	String gmail = "gmail";
	String contrasena = "contrasena";
	Estatus status = Estatus.EMPLEADO;
	double salario = 0;
	String telefono = "666444555";
	
	
	@Before
	public void setUp() throws Exception {
		trabajador = new Trabajador();
		trabajador.setNombreYApellidos(nombreYApellidos);
		trabajador.setGmail(gmail);
		trabajador.setContrasena(contrasena);
		trabajador.setStatus(status);
		trabajador.setSalario(salario);
		trabajador.setTelefono(telefono);
	}

	@Test
	public void testTrabajador() {
		trabajador = new Trabajador(nombreYApellidos, gmail, contrasena, status, salario, telefono);
		assertEquals(trabajador.getNombreYApellidos(), nombreYApellidos);
		assertEquals(trabajador.getGmail(), gmail);
		assertEquals(trabajador.getContrasena(), contrasena);
		assertEquals(trabajador.getStatus(), status);
		assertEquals(trabajador.getSalario(), salario, 0.1);
		assertEquals(trabajador.getTelefono(), telefono);
	}

	@Test
	public void testGetNombreYApellidos() {
		assertEquals(trabajador.getNombreYApellidos(), nombreYApellidos);
	}

	@Test
	public void testSetNombreYApellidos() {
		String nombreYApellidos1 = "nombreYApellidos1";
		trabajador.setNombreYApellidos(nombreYApellidos1);
		assertEquals(trabajador.getNombreYApellidos(), nombreYApellidos1);
	}

	@Test
	public void testGetGmail() {
		assertEquals(trabajador.getGmail(), gmail);
	}

	@Test
	public void testSetGmail() {
		String gmail1 = "gmail1";
		trabajador.setGmail(gmail1);
		assertEquals(trabajador.getGmail(), gmail1);
	}

	@Test
	public void testGetContrasena() {
		assertEquals(trabajador.getContrasena(), contrasena);
	}

	@Test
	public void testSetContrasena() {
		String contrasena1 = "contrasena1";
		trabajador.setContrasena(contrasena1);
		assertEquals(trabajador.getContrasena(), contrasena1);
	}

	@Test
	public void testGetStatus() {
		assertEquals(trabajador.getStatus(), status);
	}

	@Test
	public void testSetStatus() {
		Estatus status1 = Estatus.EMPLEADO;
		trabajador.setStatus(status1);
		assertEquals(trabajador.getStatus(), status1);
	}

	@Test
	public void testGetSalario() {
		assertEquals(trabajador.getSalario(), salario, 0.1);
	}

	@Test
	public void testSetSalario() {
		double salario1 = 10;
		trabajador.setSalario(salario1);
		assertEquals(trabajador.getSalario(), salario1, 0.1);
	}

	@Test
	public void testGetTelefono() {
		assertEquals(trabajador.getTelefono(), telefono);
	}

	@Test
	public void testSetTelefono() {
		String telefono1 = "666444555";
		if (telefono.length() < 9 || telefono.length() > 9) {
			System.err.println("Error guardando el teléfono");
		} else {
			trabajador.setTelefono(telefono1);
		}
		assertEquals(trabajador.getTelefono(), telefono1);
	}

	@Test
	public void testToString() {
		String salida = "Trabajador [id=" + id + ", nombreYApellidos=" + nombreYApellidos + ", gmail=" + gmail + ", contrasena="
				+ contrasena + ", status=" + status + ", salario=" + salario + ", telefono=" + telefono + "]";
		assertEquals(trabajador.toString(), salida);
	}

}
