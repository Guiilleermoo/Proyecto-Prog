package es.deusto.prog.III;

import java.util.StringTokenizer;

public class Calzado extends Producto {
	protected String articulo;
	protected int talla;
	
	
	public Calzado() {
		super(null, null, null, 0);
		this.articulo = null;
		this.talla = 42;
	}

	public Calzado(String deporte, String marca, Genero genero, double precio, String articulo, int talla) {
		super(deporte, marca, genero, precio);
		this.articulo = articulo;
		this.talla = talla;
	}

	public String getArticulo() {
		return articulo;
	}

	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}

	public int getTalla() {
		return talla;
	}

	public void setTalla(int talla) {
		this.talla = talla;
	}
	
	public static Calzado parseCSV(String linea) {
		StringTokenizer tokenizer = new StringTokenizer(linea, ",");
		Calzado calzado = new Calzado();
		
		calzado.setDeporte(tokenizer.nextToken());
		calzado.setMarca(tokenizer.nextToken());
		//calzado.setGenero(tokenizer.nextToken());
		//calzado.setPrecio(tokenizer.nextToken());
		calzado.setArticulo(tokenizer.nextToken());
		//calzado.setTalla(tokenizer.nextToken());

		return calzado;
	}
	
	@Override
	public String toString() {
		return "Calzado [articulo=" + articulo + ", talla=" + talla + "]";
	}
	
}
