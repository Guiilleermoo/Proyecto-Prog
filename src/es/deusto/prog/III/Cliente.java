package es.deusto.prog.III;

public class Cliente {
	protected int id = -1;
	protected String nombreYApellidos;
	protected String gmail;
	protected String contrasena;
	protected String direccion;
	protected String telefono;
	
	public Cliente(String nombreYApellidos, String gmail, String contrasena, String direccion,
			String telefono) {
		super();
		this.id = id++;
		this.nombreYApellidos = nombreYApellidos;
		this.gmail = gmail;
		this.contrasena = contrasena;
		this.direccion = direccion;
		this.telefono = telefono;
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

	public void setTelefono(String telefono) {
		if (telefono.length() < 9 || telefono.length() > 9) {
			System.err.println("Error guardando el teléfono");
		} else {
			this.telefono = telefono;
		}
	}

	@Override
	public String toString() {
		return "Cliente [nombreYApellidos=" + nombreYApellidos + ",gmail=" + gmail + ", contrasena="
				+ contrasena + ", direccion=" + direccion + ", telefono=" + telefono + "]";
	}
	
}
