package test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.deusto.prog.III.Producto;
import es.deusto.prog.III.Producto.Genero;

public class TestProducto {
	
	private Producto producto;
	private String articulo = "Zapatillas";
	private String deporte = "Baloncesto";
	private String marca = "Nike";
	private Genero genero = Genero.HOMBRE;
	private String talla = "M";
	private Double precio = 20.0;
	private Integer cantidad = 1;
	
	@Before
	public void setUp() throws Exception {
		producto = new Producto();
		producto.setArticulo(articulo);
		producto.setDeporte(deporte);
		producto.setMarca(marca);
		producto.setGenero(genero);
		producto.setTalla(talla);
		producto.setPrecio(precio);
		producto.setCantidad(cantidad);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProducto() {
		//Producto productoNuevo = new Producto(articulo, deporte, marca, genero, talla, precio, cantidad);
		assertEquals(producto.getArticulo(), articulo);
		assertEquals(producto.getDeporte(), deporte);
		assertEquals(producto.getMarca(), marca);
		assertEquals(producto.getGenero(), genero);
		assertEquals(producto.getTalla(), talla);
		assertEquals(producto.getPrecio(), precio, 0.1);
		assertEquals(producto.getCantidad(), cantidad, 0.1);
	}

	@Test
	public void testGetArticulo() {
		assertEquals(producto.getArticulo(), articulo);
	}

	@Test
	public void testSetArticulo() {
		producto.setArticulo("Camiseta");
		assertEquals(producto.getArticulo(), "Camiseta");
	}

	@Test
	public void testGetDeporte() {
		assertEquals(producto.getDeporte(), deporte);
	}

	@Test
	public void testSetDeporte() {
		producto.setDeporte("Futbol");
		assertEquals(producto.getDeporte(), "Futbol");
	}

	@Test
	public void testGetMarca() {
		assertEquals(producto.getMarca(), marca);
	}

	@Test
	public void testSetMarca() {
		producto.setMarca("Adidas");
		assertEquals(producto.getMarca(), "Adidas");
	}

	@Test
	public void testGetGenero() {
		assertEquals(producto.getGenero(), genero);
	}

	@Test
	public void testSetGenero() {
		producto.setGenero(genero.NINA);
		assertEquals(producto.getGenero(), genero.NINA);
	}

	@Test
	public void testGetTalla() {
		assertEquals(producto.getTalla(), talla);
	}

	@Test
	public void testSetTalla() {
		producto.setTalla("L");
		assertEquals(producto.getTalla(), "L");
	}

	@Test
	public void testGetPrecio() {
		assertEquals(producto.getPrecio(), precio, 0.1);
	}

	@Test
	public void testSetPrecio() {
		producto.setPrecio(123.45);
		assertEquals(producto.getPrecio(), 123.45, 0.1);
	}

	@Test
	public void testGetCantidad() {
		assertEquals(producto.getCantidad(), cantidad, 0.1);
	}

	@Test
	public void testSetCantidad() {
		producto.setCantidad(2);
		assertEquals(producto.getCantidad(), 2, 0.1);
	}

	@Test
	public void testToString() {
		String salida = "Producto [id=" + -1 + ", articulo=" + articulo + ", deporte=" + deporte + ", marca=" + marca
				+ ", genero=" + genero + ", talla=" + talla + ", precio=" + precio + ", cantidad=" + cantidad + "]";
		assertEquals(salida, producto.toString());
	}

}
