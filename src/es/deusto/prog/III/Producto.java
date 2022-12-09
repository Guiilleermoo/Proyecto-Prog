package es.deusto.prog.III;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Producto {
	protected int id = -1;
	protected String articulo;
	protected String deporte;
	protected String marca;
	protected Genero genero;
	protected String talla;
	protected double precio;
	
	
	public enum Genero {
		HOMBRE,MUJER,NINO,NINA,UNISEX;
	}

	public Producto(String articulo, String deporte, String marca, Genero genero, String talla, double precio) {
		super();
		this.articulo = articulo;
		this.deporte = deporte;
		this.marca = marca;
		this.genero = genero;
		this.talla = talla;
		this.precio = precio;
	}
	
	public Producto() {
		super();
		this.articulo = "";
		this.deporte = "Baloncesto";
		this.marca = "Nike";
		this.genero = Genero.HOMBRE;
		this.talla = "M";
		this.precio = 10.5;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArticulo() {
		return articulo;
	}

	public void setArticulo(String articulo) {
		this.articulo = articulo;
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

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	//Crea un Producto a partir de una cadena de texto separada por comas.
		public static Producto parseCSV(String csvString) {
			if (csvString != null && !csvString.isBlank()) {		
				StringTokenizer tokenizer = new StringTokenizer(csvString, ";");
				
				Producto producto = new Producto();		
				
				producto.setArticulo(tokenizer.nextToken());
				producto.setDeporte(tokenizer.nextToken());
				producto.setMarca(tokenizer.nextToken());
				producto.setGenero(Genero.valueOf(tokenizer.nextToken().trim().toUpperCase()));
				producto.setTalla(tokenizer.nextToken());
				producto.setPrecio(Double.parseDouble(tokenizer.nextToken()));

				return producto;
			} else {
				return null;
			}
		}
	
	@Override
	public String toString() {
		return "Producto [id=" + id + ", articulo=" + articulo + ", deporte=" + deporte + ", marca=" + marca
				+ ", genero=" + genero + ", talla=" + talla + ", precio=" + precio + "]";
	}
	
}