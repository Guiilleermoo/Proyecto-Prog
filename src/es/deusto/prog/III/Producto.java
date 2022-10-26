package es.deusto.prog.III;

public abstract class Producto {
	protected int id = -1;
	protected String deporte;
	protected String marca;
	protected Genero genero;
	protected double precio;
	
	public enum Genero {
		HOMBRE,MUJER,NIÑO,NIÑA,UNISEX;
	}

	public Producto(String deporte, String marca, Genero genero, double precio) {
		super();
		this.id = id++;
		this.deporte = deporte;
		this.marca = marca;
		this.genero = genero;
		this.precio = precio;
	}

	public String getDeporte() {
		return deporte;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", deporte=" + deporte + ", marca=" + marca + ", genero=" + genero + ", precio="
				+ precio + "]";
	}
	
}
