package es.deusto.prog.III;

import java.util.ArrayList;
import java.util.List;

import es.deusto.prog.III.Trabajador.Estatus;

public class Main {
	public static void main(String[] args) {
		GestorBD gestorBD = new GestorBD();		
		
		///////CREATE DATABASE: Se crea la BBDD
		//gestorBD.crearBBDD();
		
		///////INSERT: Insertar datos en la tabla CLIENTES		
		List<Cliente> clientes = initClientes();
		gestorBD.insertarClientes(clientes.toArray(new Cliente[clientes.size()]));
		
		///////Insetar datos en la tabla EMPLEADOS
		List<Trabajador> trabajadores = initTrabajadores();
		gestorBD.insertarTrabajador(trabajadores.toArray(new Trabajador[trabajadores.size()]));
		
		///////SELECT: Se obtienen datos de la tabla CLIENTES
		clientes = gestorBD.obtenerClientes();
		printClientes(clientes);
		
		///////SELECT: Se obtienen datos de la tabla EMPLEADOS
		trabajadores = gestorBD.obtenerTrabajadores();
		printTrabajadores(trabajadores);
		
		///////UPDATE: Se actualiza la password de un cliente
		String newPassword = "nueva";
		gestorBD.actualizarContrasena(clientes.get(0), newPassword);
		
		///////UPDATE: se actualiza el salario de un trabajador
		float nuevoSalario = 1400;
		gestorBD.actualizarSalario(trabajadores.get(7), nuevoSalario);
		trabajadores = gestorBD.obtenerTrabajadores();
		printTrabajadores(trabajadores);

		///////SELECT: Se obtienen datos de la BBDD
		clientes = gestorBD.obtenerClientes();
		printClientes(clientes);

		///////DELETE: Se borran datos de la BBDD de la tabla clientes
		gestorBD.borrarDatos("CLIENTES");
		
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
	
	private static void printTrabajadores(List<Trabajador> trabajadores) {
		if (!trabajadores.isEmpty()) {		
			for(Trabajador trabajador : trabajadores) {
				System.out.println(String.format(" - %s", trabajador.toString()));
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
	
	public static List<Trabajador> initTrabajadores() {
		List<Trabajador> trabajadores = new ArrayList<>();
		
		Trabajador trabajador = new Trabajador();
		trabajador.setNombreYApellidos("Bruce Banner");
		trabajador.setGmail("hulk@gmail.com");
		trabajador.setContrasena("NUcRn8h85RZZTjg6UBwa");
		trabajador.setStatus(Estatus.EMPLEADO);
		trabajador.setSalario(1000);
		trabajador.setTelefono("678985456");
		trabajadores.add(trabajador);
		
		Trabajador trabajador2 = new Trabajador();
		trabajador2.setNombreYApellidos("Bruce Banner2");
		trabajador2.setGmail("hulk@gmail.com2");
		trabajador2.setContrasena("NUcRn8h85RZZTjg6UBwa");
		trabajador2.setStatus(Estatus.JEFE);
		trabajador2.setSalario(1000);
		trabajador2.setTelefono("678985456");
		trabajadores.add(trabajador2);
		
		return trabajadores;
	}
}
