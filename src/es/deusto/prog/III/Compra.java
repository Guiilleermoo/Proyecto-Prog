package es.deusto.prog.III;

import java.util.ArrayList;

public class Compra {
	protected Cliente cliente;
	protected ArrayList<Producto> articulos;
	
	public Compra(Cliente cliente, ArrayList<Producto> articulos) {
		super();
		this.cliente = cliente;
		this.articulos = articulos;
	}
	
	public Compra() {
		super();
		this.cliente = new Cliente();
		this.articulos = new ArrayList<>();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ArrayList<Producto> getArticulos() {
		return articulos;
	}

	public void setArticulos(ArrayList<Producto> articulos) {
		this.articulos = articulos;
	}

	@Override
	public String toString() {
		return "Compra [cliente=" + cliente + ", articulos=" + articulos + "]";
	}
	
	
}
