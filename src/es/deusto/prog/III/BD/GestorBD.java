package es.deusto.prog.III.BD;

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
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.JOptionPane;

import es.deusto.prog.III.*;
import es.deusto.prog.III.Trabajador.Estatus;

public class GestorBD {
	private Properties properties;
	protected static  String DRIVER_NAME;
	protected static  String DATABASE_FILE;
	protected static  String CONNECTION_STRING;
	
	private static Logger logger = Logger.getLogger(GestorBD.class.getName());
	
	public GestorBD() {		
		try (FileInputStream fis = new FileInputStream("conf/logger.properties")) {
			//Inicialización del Logger
			LogManager.getLogManager().readConfiguration(fis);
			
			properties = new Properties();
			properties.load(new FileInputStream("conf/app.properties"));
			DRIVER_NAME = (String) properties.get("driver");
			DATABASE_FILE = (String) properties.get("file");
			CONNECTION_STRING = (String) properties.get("connection");
		
			//Cargar el diver SQLite
			Class.forName(DRIVER_NAME);
		} catch (Exception ex) {
			logger.warning(String.format("Error al cargar el driver de BBDD: %s", ex.getMessage()));
		}
	}
		
	/**public void crearBBDD() {
		//Se abre la conexión y se obtiene el Statement
		//Al abrir la conexión, si no existía el fichero, se crea la base de datos
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
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
	        String sql = "DROP TABLE IF EXISTS CLIENTE";
			
	        //Se ejecuta la sentencia de creación de la tabla Estudiantes
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
		//Se define la plantilla de la sentencia SQL
		String sql = "INSERT INTO CLIENTES (NOMBRE, GMAIL, CONTRASENA, DIRECCION, TELEFONO) VALUES (?, ?, ?, ?, ?');";
				
			//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				 PreparedStatement pStmt = con.prepareStatement(sql)) {
					
				//Se recorren los clientes y se insertan uno a uno
				for (Cliente c : clientes) {
					//Se definen los parámetros de la sentencia SQL
					pStmt.setString(1, c.getNombreYApellidos());
					pStmt.setString(2, c.getGmail());
					pStmt.setString(3, c.getContrasena());
					pStmt.setString(4, c.getDireccion());
					pStmt.setString(5, c.getTelefono());
						
					if (pStmt.executeUpdate() != 1) {			
						logger.warning(String.format("No se ha insertado el cliente: %s", c));
					} else {
						//Se actualiza el ID del comic haciendo un Select
						//c.setId(this.getClienteByNombreYApellidos(c.getNombreYApellidos()).getId());				
							
						logger.info(String.format("Se ha insertado el cliente: %s", c));
					}
				}
					
				logger.info(String.format("%d Clientes insertados en la BBDD", clientes.length));
			} catch (Exception ex) {
				logger.warning(String.format("Error al insertar clientes: %s", ex.getMessage()));
			}		
	}
	
	public void insertarTrabajador(Trabajador... trabajadores ) {
		String sql = "INSERT INTO EMPLEADOS (NOMBRE, GMAIL, CONTRASENA, ESTATUS, SALARIO, TELEFONO) VALUES (?, ?, ?, ?, ?, ?);";
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			//Se recorren los clientes y se insertan uno a uno
			for (Trabajador t : trabajadores) {
				if (1 != pstmt.executeUpdate()) {					
					logger.warning(String.format("No se ha insertado el trabajador: %s", t));
				} else {
					logger.info(String.format("Se ha insertado el trabajador: %s", t));
				}
			}			
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar trabajadores: %s", ex.getMessage()));						
		}				
	}
	
	public List<Cliente> obtenerClientes() {
		List<Cliente> clientes = new ArrayList<>();
		String sql = "SELECT * FROM CLIENTES WHERE ID >= 0";
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement(sql)) {
			
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
			
			logger.info(String.format("Se ha recuperado %d clientes...", clientes.size()));		
		} catch (Exception ex) {
			logger.warning(String.format("Error al obtener datos de la BD: %s", ex.getMessage()));					
		}		
		
		return clientes;
	}
	
	public Cliente getClienteByNombreYApellidos(String nombreYApellidos) {
		Cliente cliente = null;
		String sql = "SELECT * FROM cliente WHERE nombreYApellidos = ? LIMIT 1";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setString(1, nombreYApellidos);
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			//Se procesa el unico resultado
			if (rs.next()) {
				cliente = new Cliente();
				
				cliente.setId(rs.getInt("ID"));
				cliente.setNombreYApellidos(rs.getString("NOMBRE"));
				cliente.setGmail(rs.getString("GMAIL"));
				cliente.setContrasena(rs.getString("CONTRASENA"));
				cliente.setDireccion(rs.getString("DIRECCION"));
				cliente.setTelefono(rs.getString("TELEFONO"));
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			logger.info(String.format("Se ha recuperado el cliente %s", cliente));			
		} catch (Exception ex) {
			logger.warning(String.format("Error al recuperar el cliente con nombre %s: %s", nombreYApellidos, ex.getMessage()));						
		}		
		
		return cliente;
	}
	
	public List<Trabajador> obtenerTrabajadores() {
		List<Trabajador> trabajadores = new ArrayList<>();
		String sql = "SELECT * FROM EMPLEADOS WHERE ID >= 0";
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement(sql)) {
			
			
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
			
			logger.info(String.format("Se han recuperado %d trabajadores...", trabajadores.size()));			
		} catch (Exception ex) {
			logger.warning(String.format("Error al obtener datos de la BD: %s", ex.getMessage()));						
		}		
		return trabajadores;
	}

	public void actualizarContrasena(Cliente cliente, String contrasenaNueva) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "UPDATE CLIENTES SET CONTRASENA = '%s' WHERE ID = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, contrasenaNueva, cliente.getId()));
			
			logger.info(String.format("Se ha actualizado la contrasena del cliente %d a %d", cliente, contrasenaNueva));
		} catch (Exception ex) {
			logger.warning(String.format("Error actualizando datos de la BD: %s", ex.getMessage()));					
		}		
	}
	
	public void actualizarSalario(Trabajador trabajador, Float salario) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "UPDATE EMPLEADOS SET SALARIO = '%s' WHERE ID = %d;";

			int result = stmt.executeUpdate(String.format(sql, salario, trabajador.getId()));
			
			logger.info(String.format("Se ha actualizado el salario de %d", result));
		} catch (Exception ex) {
			logger.warning(String.format("Error actualizando datos de la BD: %s", ex.getMessage()));					
		}		
	}
	
	public boolean comprobarCliente(String gmail, String contrasena) {
		String sql = "SELECT GMAIL,CONTRASENA FROM CLIENTE WHERE GMAIL = ? and CONTRASENA = ? LIMIT 1";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();
			if (rs.first()) {
				return true;
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ("Error: cliente inexistente"));
			logger.warning(String.format("Error cliente inexistente en la BD: %s", ex.getMessage()));
		}
		return false;		
	}
	
	public boolean comprobarTrabajador(String gmail, String contrasena) {
		String sql = "SELECT GMAIL,CONTRASENA FROM empleados WHERE GMAIL = ? and CONTRASENA = ? LIMIT 1";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();
			if (rs.first()) {
				return true;
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ("Error: trabajador inexistente"));					
			logger.warning(String.format("Error trabajador inexistente en la BD: %s", ex.getMessage()));
		}
		return false;		
	}
	
	public void borrarCliente(Cliente cliente) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "DELETE FROM CLIENTES WHERE ID = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, cliente.getId()));
			
			logger.info(String.format("Se ha borrado al cliente %d", result));
		} catch (Exception ex) {
			logger.warning(String.format("Error al borrar el cliente en la BD: %s", ex.getMessage()));					
		}		
	}
	
	public void borrarTrabajador(Trabajador trabajador) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "DELETE FROM CLIENTES WHERE ID = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, trabajador.getId()));
			
			logger.info(String.format("Se ha borrado al trabajador %d", result));
		} catch (Exception ex) {
			logger.warning(String.format("Error al borrar el trabajador en la BD: %s", ex.getMessage()));						
		}		
	}
	
	
	
	public void borrarDatos(String tabla) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "DELETE FROM " + tabla + ";";			
			int result = stmt.executeUpdate(sql);
			
			logger.info(String.format("Se ha borrado la tabla %d", tabla));
		} catch (Exception ex) {
			logger.warning(String.format("Error al borrar datos de la BD: %s", ex.getMessage()));					
		}		
	}	
}
 