package es.deusto.prog.III;

import java.util.ArrayList;


public class Cliente {
	protected int id = -1;
	protected String nombreYApellidos;
	protected String gmail;
	protected String contrasena;
	protected String direccion;
	protected String telefono;
	protected ArrayList<Producto> articulos;
	
	public Cliente() {
		super();
		
		this.nombreYApellidos = null;
		this.gmail = null;
		this.contrasena = null;
		this.direccion = null;
		this.telefono = null;
	}
	
	public Cliente(String nombreYApellidos, String gmail, String contrasena, String direccion,
			String telefono) {
		super();
		this.nombreYApellidos = nombreYApellidos;
		this.gmail = gmail;
		this.contrasena = contrasena;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreYApellidos() {
		return nombreYApellidos;
	}

	public void setNombreYApellidos(String nombreYApellidos) {
		this.nombreYApellidos = nombreYApellidos;
	}
	
	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}
	
	public ArrayList<Producto> getArticulos() {
		return articulos;
	}

	public void setArticulos(ArrayList<Producto> articulos) {
		this.articulos = articulos;
	}

	public void setTelefono(String telefono) {
		if (telefono.length() < 9 || telefono.length() > 9) {
			System.err.println("Error guardando el teléfono");
		} else {
			this.telefono = telefono;
		}
	}
	
	public void comprobarCleinte(String gmail, String contrasena) {
		
	}

	@Override
	public String toString() {
		return "Cliente [ID = " + id + " nombreYApellidos=" + nombreYApellidos + ",gmail=" + gmail + ", contrasena="
				+ contrasena + ", direccion=" + direccion + ", telefono=" + telefono + "]";
	}
	
}
