package es.deusto.prog.III;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public abstract class Producto {
	protected String deporte;
	protected String marca;
	protected Genero genero;
	protected double precio;
	
	
	public enum Genero {
		HOMBRE,MUJER,NINO,NINA,UNISEX;
	}

	public Producto( String deporte, String marca, Genero genero, double precio) {
		super();
		this.deporte = deporte;
		this.marca = marca;
		this.genero = genero;
		this.precio = precio;
		
	}

	public Producto() {
		super();
		this.deporte = "Futbol";
		this.marca = "Nike";
		this.genero = Genero.HOMBRE;
		this.precio = 20;
		
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
	public static void cargarProductos() {
	//Se abre el fichero usando "try-catch with closeable resources"
	//NOTA: la ruta relativa exige que el fichero CVS se ponga en la carpeta data.
		try (BufferedReader in = new BufferedReader(new FileReader("data/PRODUCTOS.csv"))) {
			String linea;
			List<Producto> productos = new ArrayList<>();
				
			//Lectura saltar la cabecera del fichero CSV.
			in.readLine();
				
			//Se leen líneas hasta llegar al final del fichero.
			while( (linea = in.readLine()) != null ) {
				//Se trasnforma cada línea en un objeto User y se añade a la lista.
				if (linea.contains("Calzado")) {
					productos.add(Calzado.parseCSV(linea));
				} else if (linea.contains("Ropa")) {
					productos.add(Ropa.parseCSV(linea));
				}
				
			}
				
			//Se recorre la lista de usuarios y se muestra su contenido por pantalla.
			for(Producto p : productos) {
				System.out.println(p);
			}
				
		} catch(Exception ex) {
			System.err.println(String.format("Error abriendo el fichero: %s", ex.getMessage()));
		}
	}
	
	@Override
	public String toString() {
		return "Producto [deporte=" + deporte + ", marca=" + marca + ", genero=" + genero + ", precio="
				+ precio + ", comprador="  + "]";
	}
	
}
