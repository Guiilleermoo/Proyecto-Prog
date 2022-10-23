package es.deusto.prog.III;

public class Cliente {
	protected String nombre;
	protected String apellidos;
	protected String gmail;
	protected String contrase�a;
	protected String direccion;
	protected String telefono;
	
	public Cliente(String nombre, String apellidos, String gmail, String contrase�a, String direccion,
			String telefono) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.gmail = gmail;
		this.contrase�a = contrase�a;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getContrase�a() {
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
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
			System.err.println("Error guardando el tel�fono");
		} else {
			this.telefono = telefono;
		}
	}

	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", apellidos=" + apellidos + ", gmail=" + gmail + ", contrase�a="
				+ contrase�a + ", direccion=" + direccion + ", telefono=" + telefono + "]";
	}
	
}
