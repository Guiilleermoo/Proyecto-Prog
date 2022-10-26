package es.deusto.prog.III;

public class Ropa extends Producto{

	protected String articulo;
	protected int talla;

	public Ropa() {
		super(null, null, null, 0);
		this.articulo = null;
		this.talla = 0;
	}
	
	public Ropa(String deporte, String marca, Genero genero, double precio, String articulo, int talla) {
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

	@Override
	public String toString() {
		return "Ropa [articulo=" + articulo + ", talla=" + talla + "]";
	}
	
	
}
