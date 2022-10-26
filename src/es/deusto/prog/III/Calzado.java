package es.deusto.prog.III;

public class Calzado extends Producto {
	protected String articulo;
	protected Talla talla;
	
	public enum Talla {
		XS,S,M,L,XL,XXL;
	}

	public Calzado(String deporte, String marca, Genero genero, double precio, String articulo, Talla talla) {
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

	@Override
	public String toString() {
		return "Calzado [articulo=" + articulo + ", talla=" + talla + "]";
	}
	
}
