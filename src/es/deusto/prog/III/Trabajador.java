package es.deusto.prog.III;

public class Trabajador {
	protected int id = -1;
	protected String nombreYApellidos;
	protected String gmail;
	protected String contrasena;
	protected Status status;
	protected double salario;
	protected String telefono;
	
	public enum Status {
		JEFE,EMPLEADO;
	}
	
	public Trabajador() {
		super();
		this.nombreYApellidos = null;
		this.gmail = null;
		this.contrasena = null;
		this.status = null;
		this.salario = 0;
		this.telefono = null;
	}

	public Trabajador(String nombreYApellidos, String gmail, String contrasena, Status status, double salario,
			String telefono) {
		super();
		this.nombreYApellidos = nombreYApellidos;
		this.gmail = gmail;
		this.contrasena = contrasena;
		this.status = status;
		this.salario = salario;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
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
		return "Trabajador [id=" + id + ", nombreYApellidos=" + nombreYApellidos + ", gmail=" + gmail + ", contrasena="
				+ contrasena + ", status=" + status + ", salario=" + salario + ", telefono=" + telefono + "]";
	}
	
}
