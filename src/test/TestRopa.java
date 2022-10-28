package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.deusto.prog.III.Producto.Genero;
import es.deusto.prog.III.Ropa;
import es.deusto.prog.III.Ropa.Talla;

public class TestRopa {

	private Ropa ropa;
	private String deporte = "Deporte";
	private String marca = "Marca";
	private Genero genero = Genero.HOMBRE;
	private float precio = 1000;
	private String articulo = "Articulo";
	private Talla talla = Talla.M;
	
	@Before
	public void setUp() throws Exception {
		ropa = new Ropa();
		ropa.setDeporte(deporte);
		ropa.setMarca(marca);
		ropa.setGenero(genero);
		ropa.setPrecio(precio);
		ropa.setArticulo(articulo);
		ropa.setTalla(talla);
	}

	@Test
	public void testRopa() {
		ropa = new Ropa(deporte, marca, genero, precio, articulo, talla);
		assertEquals(ropa.getDeporte(), deporte);
		assertEquals(ropa.getMarca(), marca);
		assertEquals(ropa.getGenero(), genero);
		assertEquals(ropa.getPrecio(), precio, 0.1);
		assertEquals(ropa.getArticulo(), articulo);
		assertEquals(ropa.getTalla(), talla);
	}

	@Test
	public void testGetArticulo() {
		assertEquals(ropa.getArticulo(), articulo);
	}

	@Test
	public void testSetArticulo() {
		String articulo1 = "Articulo1";
		ropa.setArticulo(articulo1);
		assertEquals(ropa.getArticulo(), articulo1);
	}

	@Test
	public void testGetTalla() {
		assertEquals(ropa.getTalla(), talla);
	}

	@Test
	public void testSetTalla() {
		Talla talla1 = Talla.M;
		ropa.setTalla(talla1);
		assertEquals(ropa.getTalla(), talla1);
	}

//	@Test
//	public void testProducto() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetDeporte() {
		assertEquals(ropa.getDeporte(), deporte);
	}

	@Test
	public void testSetDeporte() {
		String deporte1 = "Deporte1";
		ropa.setDeporte(deporte1);
		assertEquals(ropa.getDeporte(), deporte1);
	}

	@Test
	public void testGetMarca() {
		assertEquals(ropa.getMarca(), marca);
	}

	@Test
	public void testSetMarca() {
		String marca1 = "Marca1";
		ropa.setMarca(marca1);
		assertEquals(ropa.getMarca(), marca1);
	}

	@Test
	public void testGetGenero() {
		assertEquals(ropa.getGenero(), genero);
	}

	@Test
	public void testSetGenero() {
		Genero genero1 = Genero.MUJER;
		ropa.setGenero(genero1);
		assertEquals(ropa.getGenero(), genero1);
	}

	@Test
	public void testGetPrecio() {
		assertEquals(ropa.getPrecio(), precio, 0.1);
	}

	@Test
	public void testSetPrecio() {
		float precio1 = 0;
		ropa.setPrecio(precio1);
		assertEquals(ropa.getPrecio(), precio1, 0.1);
	}

	@Test
	public void testToString() {
		String salida = "Ropa [articulo=" + articulo + ", talla=" + talla + "]";
		assertEquals(salida, ropa.toString());
	}
}
