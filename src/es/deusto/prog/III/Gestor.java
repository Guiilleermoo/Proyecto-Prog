package es.deusto.prog.III;

import java.util.ArrayList;

public class Gestor {
	protected ArrayList<Producto> productos;
	protected ArrayList<Trabajador> empleados; 
	protected ArrayList<Cliente> clientes;
	
	
	public Gestor(ArrayList<Producto> productos, ArrayList<Trabajador> empleados, ArrayList<Cliente> clientes) {
		super();
		this.productos = productos;
		this.empleados = empleados;
		this.clientes = clientes;
	}
	
	public Gestor() {
		super();
		this.productos = new ArrayList<Producto>();
		this.empleados = new ArrayList<Trabajador>();
		this.clientes = new ArrayList<Cliente>();
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public ArrayList<Trabajador> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(ArrayList<Trabajador> empleados) {
		this.empleados = empleados;
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public double getCostoTotal() {
		double resultado = 0;
		for (Producto producto : productos) {
			resultado += producto.precio;
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return "Gestor [productos=" + productos + ", empleados=" + empleados + ", clientes=" + clientes + "]";
	}
	
}
