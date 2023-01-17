package es.deusto.prog.III;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Pedido {
	protected int id;
	protected String cliente;
	protected Date fecha;
	protected ArrayList<Producto> productos;
	protected Estado estado;
	
	public enum Estado {
		PREPARACION, LISTO, FINALIZADO;
	}
	
	public Pedido() {
		super();
		this.id = -1;
		this.cliente = "";
		this.fecha = null;
		this.productos = new ArrayList<>();
		this.estado = Estado.LISTO;
	}

	public Pedido( String cliente, Date fecha, ArrayList<Producto> productos, Estado estado) {
		super();
		this.cliente = cliente;
		this.fecha = fecha;
		this.productos = productos;
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public double getPrecio() {
		double precio = 0;
		
		for (Producto producto : productos) {
			precio += producto.getPrecio();
		}
		
		return precio;
	}
	
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
		return "Pedido de " + cliente + ", " + sdf.format(fecha) + ", " + estado;
	}
}
