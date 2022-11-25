package es.deusto.prog.III;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.JOptionPane;

import es.deusto.prog.III.Trabajador.Estatus;

public class GestorBD {
	protected static  String DRIVER_NAME;
	protected static  String DATABASE_FILE;
	protected static  String CONNECTION_STRING;
	
	private Properties properties;
	
	public GestorBD() {		
		try {
			properties.load(new FileInputStream(new File("properties/app.properties")));
			DRIVER_NAME = (String) properties.get("driver");
			DATABASE_FILE = (String) properties.get("file");
			CONNECTION_STRING = (String) properties.get("connection");
		
			//Cargar el diver SQLite
			Class.forName(DRIVER_NAME);
		} catch (Exception ex) {
			System.err.println(String.format("* Error al cargar el driver de BBDD: %s", ex.getMessage()));
			ex.printStackTrace();
		}
	}
		
	/**public void crearBBDD() {
		//Se abre la conexi贸n y se obtiene el Statement
		//Al abrir la conexi贸n, si no exist铆a el fichero, se crea la base de datos
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
	        String sql = "CREATE TABLE IF NOT EXISTS CLIENTE (\n"
	                   + " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                   + " NOMBREYAPELLIDOS TEXT NOT NULL,\n"
	                   + " GMAIL TEXT NOT NULL,\n"
	                   + " CONTRASENA TEXT NOT NULL, \n"
	                   + " DIRECCION TEXT NOT NULL, \n"
	                   + " TELEFONO TEXT NOT NULL\n"
	                   + ");";
	        	        
	        if (!stmt.execute(sql)) {
	        	System.out.println("- Se ha creado la tabla Cliente");
	        }
		} catch (Exception ex) {
			System.err.println(String.format("* Error al crear la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();			
		}
	}**/
	
	/**public void borrarBBDD() {
		//Se abre la conexi贸n y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
	        String sql = "DROP TABLE IF EXISTS CLIENTE";
			
	        //Se ejecuta la sentencia de creaci贸n de la tabla Estudiantes
	        if (!stmt.execute(sql)) {
	        	System.out.println("- Se ha borrado la tabla Cliente");
	        }
		} catch (Exception ex) {
			System.err.println(String.format("* Error al borrar la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();			
		}
		
		try {
			//Se borra el fichero de la BBDD
			Files.delete(Paths.get(DATABASE_FILE));
			System.out.println("- Se ha borrado el fichero de la BBDD");
		} catch (Exception ex) {
			System.err.println(String.format("* Error al borrar el archivo de la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();						
		}
	}**/
	
	public void insertarClientes(Cliente... clientes ) {
		//Se abre la conexi贸n y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se define la plantilla de la sentencia SQL
			String sql = "INSERT INTO CLIENTES (NOMBRE, GMAIL, CONTRASENA, DIRECCION, TELEFONO) VALUES ('%s', '%s', '%s', '%s', '%s');";
			
			System.out.println("- Insertando clientes...");
			
			//Se recorren los clientes y se insertan uno a uno
			for (Cliente c : clientes) {
				if (1 == stmt.executeUpdate(String.format(sql, c.getNombreYApellidos(), c.getGmail(), c.getContrasena(), c.getDireccion(), c.getTelefono()))) {					
					System.out.println(String.format(" - Cliente insertado: %s", c.toString()));
				} else {
					System.out.println(String.format(" - No se ha insertado el cliente: %s", c.toString()));
				}
			}			
		} catch (Exception ex) {
			System.err.println(String.format("* Error al insertar datos de la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();						
		}				
	}
	
	public void insertarTrabajador(Trabajador... trabajadores ) {
		//Se abre la conexi贸n y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se define la plantilla de la sentencia SQL
			String sql = "INSERT INTO EMPLEADOS (NOMBRE, GMAIL, CONTRASENA, ESTATUS, SALARIO, TELEFONO) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');";
			
			System.out.println("- Insertando Trabajadores...");
			
			//Se recorren los clientes y se insertan uno a uno
			for (Trabajador t : trabajadores) {
				if (1 == stmt.executeUpdate(String.format(sql, t.getNombreYApellidos(), t.getGmail(), t.getContrasena(), t.getStatus().toString(), t.getSalario(), t.getTelefono()))) {					
					System.out.println(String.format(" - Trabajador insertado: %s", t.toString()));
				} else {
					System.out.println(String.format(" - No se ha insertado el Trabajador: %s", t.toString()));
				}
			}			
		} catch (Exception ex) {
			System.err.println(String.format("* Error al insertar datos de la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();						
		}				
	}
	
	public List<Cliente> obtenerClientes() {
		List<Cliente> clientes = new ArrayList<>();
		
		//Se abre la conexi贸n y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM CLIENTES WHERE ID >= 0";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			Cliente cliente;
			
			//Se recorre el ResultSet y se crean objetos Cliente
			while (rs.next()) {
				cliente = new Cliente();
				
				cliente.setId(rs.getInt("ID"));
				cliente.setNombreYApellidos(rs.getString("NOMBRE"));
				cliente.setGmail(rs.getString("GMAIL"));
				cliente.setContrasena(rs.getString("CONTRASENA"));
				cliente.setDireccion(rs.getString("DIRECCION"));
				cliente.setTelefono(rs.getString("TELEFONO"));
				
				//Se inserta cada nuevo cliente en la lista de clientes
				clientes.add(cliente);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.println(String.format("- Se han recuperado %d clientes...", clientes.size()));			
		} catch (Exception ex) {
			System.err.println(String.format("* Error al obtener datos de la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
		
		return clientes;
	}
	
	public List<Trabajador> obtenerTrabajadores() {
		List<Trabajador> trabajadores = new ArrayList<>();
		
		//Se abre la conexi贸n y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM EMPLEADOS WHERE ID >= 0";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			Trabajador trabajador;
			
			//Se recorre el ResultSet y se crean objetos Cliente
			while (rs.next()) {
				trabajador = new Trabajador();
				
				trabajador.setId(rs.getInt("ID"));
				trabajador.setNombreYApellidos(rs.getString("NOMBRE"));
				trabajador.setGmail(rs.getString("GMAIL"));
				trabajador.setContrasena(rs.getString("CONTRASENA"));
				trabajador.setStatus(Estatus.valueOf(rs.getString("ESTATUS")));
				trabajador.setSalario(rs.getFloat("SALARIO"));
				trabajador.setTelefono(rs.getString("TELEFONO"));
				
				//Se inserta cada nuevo cliente en la lista de clientes
				trabajadores.add(trabajador);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.println(String.format("- Se han recuperado %d trabajadores...", trabajadores.size()));			
		} catch (Exception ex) {
			System.err.println(String.format("* Error al obtener datos de la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
		
		return trabajadores;
	}

	public void actualizarContrasena(Cliente cliente, String contrasenaNueva) {
		//Se abre la conexi贸n y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "UPDATE CLIENTES SET CONTRASENA = '%s' WHERE ID = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, contrasenaNueva, cliente.getId()));
			
			System.out.println(String.format("- Se ha actulizado %d clientes", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error actualizando datos de la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
	}
	
	public void actualizarSalario(Trabajador trabajador, Float salario) {
		//Se abre la conexi贸n y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "UPDATE EMPLEADOS SET SALARIO = '%s' WHERE ID = %d;";

			int result = stmt.executeUpdate(String.format(sql, salario, trabajador.getId()));
			
			System.out.println(String.format("- Se ha actulizado %d clientes", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error actualizando datos de la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
	}
	
	public boolean comprobarCliente(String gmail, String contrasena) {
		String sql = "SELECT GMAIL,CONTRASENA FROM CLIENTE WHERE GMAIL = ? and CONTRASENA = ? LIMIT 1";
		
		//Se abre la conexi贸n y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();
			if (rs.first()) {
				return true;
			}
		} catch (Exception ex) {
			JOptionPane.showInputDialog(String.format("Error en el correo y/o contrase馻", ex.getMessage()));						
		}
		return false;		
	}
	
	public boolean comprobarTrabajador(String gmail, String contrasena) {
		String sql = "SELECT GMAIL,CONTRASENA FROM empleados WHERE GMAIL = ? and CONTRASENA = ? LIMIT 1";
		
		//Se abre la conexi贸n y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();
			if (rs.first()) {
				return true;
			}
		} catch (Exception ex) {
			JOptionPane.showInputDialog(String.format("Error en el correo y/o contrase馻", ex.getMessage()));						
		}
		return false;		
	}
	
	public void borrarCliente(Cliente cliente) {
		//Se abre la conexi贸n y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "DELETE FROM CLIENTES WHERE ID = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, cliente.getId()));
			
			System.out.println(String.format("- Se ha borrado %d clientes", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error borrando datos de la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
	}
	
	public void borrarTrabajador(Trabajador trabajador) {
		//Se abre la conexi贸n y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "DELETE FROM CLIENTES WHERE ID = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, trabajador.getId()));
			
			System.out.println(String.format("- Se ha borrado %d trabajadores", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error borrando datos de la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
	}
	
	
	
	public void borrarDatos(String tabla) {
		//Se abre la conexi贸n y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "DELETE FROM " + tabla + ";";			
			int result = stmt.executeUpdate(sql);
			
			System.out.println(String.format("- Se han borrado %d clientes", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error al borrar datos de la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
	}	
}
 