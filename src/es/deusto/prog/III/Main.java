package es.deusto.prog.III;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		GestorBD gestorBD = new GestorBD();		
		
		//CREATE DATABASE: Se crea la BBDD
		gestorBD.crearBBDD();
		
		//INSERT: Insertar datos en la BBDD		
		List<Cliente> clientes = initClientes();
		gestorBD.insertarDatos(clientes.toArray(new Cliente[clientes.size()]));
		
		//SELECT: Se obtienen datos de la BBDD
		clientes = gestorBD.obtenerDatos();
		printClientes(clientes);
		
		//UPDATE: Se actualiza la password de un cliente
		String newPassword = "hWaPvd6R28%1";
		gestorBD.actualizarPassword(clientes.get(0), newPassword);

		//SELECT: Se obtienen datos de la BBDD
		clientes = gestorBD.obtenerDatos();
		printClientes(clientes);

		//DELETE: Se borran datos de la BBDD
		gestorBD.borrarDatos();
		
		//DROP DATABASE: Se borra la BBDD
		gestorBD.borrarBBDD();
	}
	
	private static void printClientes(List<Cliente> clientes) {
		if (!clientes.isEmpty()) {		
			for(Cliente cliente : clientes) {
				System.out.println(String.format(" - %s", cliente.toString()));
			}
		}		
	}
	
	public static List<Cliente> initClientes() {
		List<Cliente> clientes = new ArrayList<>();
		
		Cliente cliente = new Cliente();
		cliente.setNombreYApellidos("Bruce Banner");
		cliente.setGmail("hulk@gmail.com");
		cliente.setContrasena("NUcRn8h85RZZTjg6UBwa");
		cliente.setDireccion("Barakaldo");
		cliente.setTelefono("678985456");
		clientes.add(cliente);
		
		Cliente cliente1 = new Cliente();
		cliente.setNombreYApellidos("Unai Aira");
		cliente.setGmail("unai.aira@gmail.com");
		cliente.setContrasena("NUcRn8h85RZZTjg6UBwa");
		cliente.setDireccion("Gurutzeta");
		cliente.setTelefono("678456536");
		clientes.add(cliente1);
		
		Cliente cliente2 = new Cliente();
		cliente.setNombreYApellidos("Bruce Banner");
		cliente.setGmail("hulk@gmail.com");
		cliente.setContrasena("NUcRn8h85RZZTjg6UBwa");
		cliente.setDireccion("NUcRn8h85RZZTjg6UBwa");
		cliente.setTelefono("NUcRn8h85RZZTjg6UBwa");
		clientes.add(cliente2);
		
		Cliente cliente3 = new Cliente();
		cliente.setNombreYApellidos("Bruce Banner");
		cliente.setGmail("hulk@gmail.com");
		cliente.setContrasena("NUcRn8h85RZZTjg6UBwa");
		cliente.setDireccion("NUcRn8h85RZZTjg6UBwa");
		cliente.setTelefono("NUcRn8h85RZZTjg6UBwa");
		clientes.add(cliente3);
		
		Cliente cliente4 = new Cliente();
		cliente.setNombreYApellidos("Bruce Banner");
		cliente.setGmail("hulk@gmail.com");
		cliente.setContrasena("NUcRn8h85RZZTjg6UBwa");
		cliente.setDireccion("NUcRn8h85RZZTjg6UBwa");
		cliente.setTelefono("NUcRn8h85RZZTjg6UBwa");
		clientes.add(cliente4);
		
		return clientes;
	}
}
