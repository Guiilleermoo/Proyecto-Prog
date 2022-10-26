package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.deusto.prog.III.Calzado;
import es.deusto.prog.III.Calzado.Talla;
import es.deusto.prog.III.Producto.Genero;

public class TestCalzado {

	private Calzado calzado;
	private String deporte = "Deporte";
	private String marca = "Marca";
	private Genero genero = Genero.HOMBRE;
	private float precio = 1000;
	private String articulo = "Articulo";
	private Talla talla = Talla.L;
	
	@Before
	public void setup() throws Exception  {
		calzado = new Calzado();
		calzado.setDeporte(deporte);
		calzado.setMarca(marca);
		calzado.setGenero(genero);
		calzado.setPrecio(precio);
		calzado.setArticulo(articulo);
		calzado.setTalla(talla);
	}

	@Test
	public void testCalzado() {
		calzado = new Calzado(deporte, marca, genero, precio, articulo, talla);
		assertEquals(calzado.getDeporte(), deporte);
		assertEquals(calzado.getMarca(), marca);
		assertEquals(calzado.getGenero(), genero);
		assertEquals(calzado.getPrecio(), precio, 0.1);
		assertEquals(calzado.getArticulo(), articulo);
		assertEquals(calzado.getTalla(), talla);
	}

	@Test
	public void testGetArticulo() {
		assertEquals(calzado.getArticulo(), articulo);
	}

	@Test
	public void testSetArticulo() {
		String articulo1 = "Articulo1";
		calzado.setArticulo(articulo1);
		assertEquals(calzado.getArticulo(), articulo1);
	}

	@Test
	public void testGetTalla() {
		assertEquals(calzado.getTalla(), talla);
	}

	@Test
	public void testSetTalla() {
		Talla talla1 = Talla.S;
		calzado.setTalla(talla1);
		assertEquals(calzado.getTalla(), talla1);
	}

//	@Test
//	public void testProducto() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetDeporte() {
		assertEquals(calzado.getDeporte(), deporte);
	}

	@Test
	public void testSetDeporte() {
		String deporte1 = "Deporte1";
		calzado.setDeporte(deporte1);
		assertEquals(calzado.getDeporte(), deporte1);
	}

	@Test
	public void testGetMarca() {
		assertEquals(calzado.getMarca(), marca);
	}

	@Test
	public void testSetMarca() {
		String marca1 = "Marca1";
		calzado.setMarca(marca1);
		assertEquals(calzado.getMarca(), marca1);
	}

	@Test
	public void testGetGenero() {
		assertEquals(calzado.getGenero(), genero);
	}

	@Test
	public void testSetGenero() {
		Genero genero1 = Genero.MUJER;
		calzado.setGenero(genero1);
		assertEquals(calzado.getGenero(), genero1);
	}

	@Test
	public void testGetPrecio() {
		assertEquals(calzado.getPrecio(), precio, 0.1);
	}

	@Test
	public void testSetPrecio() {
		float precio1 = 0;
		calzado.setPrecio(precio1);
		assertEquals(calzado.getPrecio(), precio1, 0.1);
	}
	
	@Test
	public void testToString() {
		String salida = "Calzado [articulo=" + articulo + ", talla=" + talla + "]";
		assertEquals(salida, calzado.toString());
	}

}
