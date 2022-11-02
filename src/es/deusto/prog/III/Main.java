package es.deusto.prog.III;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		GestorBD gestorBD = new GestorBD();		
		
		///////CREATE DATABASE: Se crea la BBDD
		//gestorBD.crearBBDD();
		
		///////INSERT: Insertar datos en la BBDD		
		List<Cliente> clientes = initClientes();
		gestorBD.insertarDatos(clientes.toArray(new Cliente[clientes.size()]));
		
		///////SELECT: Se obtienen datos de la BBDD
		clientes = gestorBD.obtenerDatos();
		printClientes(clientes);
		
		///////UPDATE: Se actualiza la password de un cliente
		//String newPassword = "hWaPvd6R28%1";
		//gestorBD.actualizarPassword(clientes.get(0), newPassword);

		///////SELECT: Se obtienen datos de la BBDD
		//clientes = gestorBD.obtenerDatos();
		//printClientes(clientes);

		///////DELETE: Se borran datos de la BBDD de la tabla clientes
		//gestorBD.borrarDatos("CLIENTES");
		
		///////DROP DATABASE: Se borra la BBDD
		//gestorBD.borrarBBDD();
		
		///////Borrar un cliente de la base de datos
		//gestorBD.borrarCliente(clientes.get(0));

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
		cliente1.setNombreYApellidos("Unai Aira");
		cliente1.setGmail("unai.aira@gmail.com");
		cliente1.setContrasena("NUcRn8h85RZZTjg6UBwa");
		cliente1.setDireccion("Gurutzeta");
		cliente1.setTelefono("678456536");
		clientes.add(cliente1);
		
		Cliente cliente2 = new Cliente();
		cliente2.setNombreYApellidos("Bruce Banner");
		cliente2.setGmail("hulk@gmail.com");
		cliente2.setContrasena("NUcRn8h85RZZTjg6UBwa");
		cliente2.setDireccion("NUcRn8h85RZZTjg6UBwa");
		cliente2.setTelefono("678456530");
		clientes.add(cliente2);
		
		Cliente cliente3 = new Cliente();
		cliente3.setNombreYApellidos("Bruce Banner");
		cliente3.setGmail("hulk@gmail.com");
		cliente3.setContrasena("NUcRn8h85RZZTjg6UBwa");
		cliente3.setDireccion("NUcRn8h85RZZTjg6UBwa");
		cliente3.setTelefono("678456538");
		clientes.add(cliente3);
		
		Cliente cliente4 = new Cliente();
		cliente4.setNombreYApellidos("Bruce Banner");
		cliente4.setGmail("hulk@gmail.com");
		cliente4.setContrasena("NUcRn8h85RZZTjg6UBwa");
		cliente4.setDireccion("NUcRn8h85RZZTjg6UBwa");
		cliente4.setTelefono("678456537");
		clientes.add(cliente4);
		
		return clientes;
	}
}
