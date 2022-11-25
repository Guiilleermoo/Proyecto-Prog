package es.deusto.prog.III;

import java.util.StringTokenizer;

public class Ropa extends Producto{

	protected String articulo;
	protected Talla talla;
	
	public enum Talla {
		XS,S,M,L,XL,XXL;
	}

	public Ropa() {
		super(null, null, null, 0);
		this.articulo = null;
		this.talla = Talla.M;
	}
	
	public Ropa(String deporte, String marca, Genero genero, double precio, String articulo, Talla talla) {
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

	public Talla getTalla() {
		return talla;
	}

	public void setTalla(Talla talla) {
		this.talla = talla;
	}
	
	public static Ropa parseCSV(String linea) {
		StringTokenizer tokenizer = new StringTokenizer(linea, ",");
		Ropa ropa = new Ropa();
		
		ropa.setDeporte(tokenizer.nextToken());
		ropa.setMarca(tokenizer.nextToken());
		//ropa.setGenero(tokenizer.nextToken());
		//ropa.setPrecio(tokenizer.nextToken());
		ropa.setArticulo(tokenizer.nextToken());
		//ropa.setTalla(tokenizer.nextToken());

		return ropa;
	}
	
	@Override
	public String toString() {
		return "Ropa [articulo=" + articulo + ", talla=" + talla + "]";
	}
}
