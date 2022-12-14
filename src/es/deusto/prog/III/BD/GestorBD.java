 package es.deusto.prog.III.BD;

import java.io.*;
import java.nio.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.*;

import javax.net.ssl.SSLEngineResult.Status;

import es.deusto.prog.III.*;
import es.deusto.prog.III.Producto.Genero;
import es.deusto.prog.III.Trabajador.Estatus;

public class GestorBD {
	private Properties properties;
	protected static  String DRIVER_NAME;
	protected static  String DATABASE_FILE;
	protected static  String CONNECTION_STRING;
	
	private static Logger logger = null;
	
	public GestorBD() {		
		try (FileInputStream fis = new FileInputStream("log/logger.properties")) {
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
			log(Level.SEVERE, "Error al cargar el driver de BBDD", ex);
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
	
	/**
	 * Inicializa la BBDD leyendo los datos de los ficheros CSV 
	 */
	public void initilizeFromCSV() {
		//Sólo se inicializa la BBDD si la propiedad initBBDD es true.
		if (properties.get("loadCSV").equals("true")) {
			
			//Se leen los productos del CSV
			List<Producto> productos = this.loadCSVProductos();
			//Se insertan los productos en la BBDD
			this.insertarProducto(productos.toArray(new Producto[productos.size()]));	
		}
	}
	
	public void insertarClientes(Cliente... clientes ) {
		//Se define la plantilla de la sentencia SQL
		String sql = "INSERT INTO CLIENTES (NOMBRE, GMAIL, CONTRASENA, DIRECCION, TELEFONO) VALUES (?, ?, ?, ?, ?);";
				
			//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				 PreparedStatement pStmt = con.prepareStatement(sql)) {
				//Se recorren los clientes y se insertan uno a uno
				for (Cliente c : clientes) {
					// Si el cliente no existe que se a�ada el cliente a la BD
					if (comprobarCliente(c.getGmail(), c.getContrasena()) == false) {
						//Se definen los parámetros de la sentencia SQL
						pStmt.setString(1, c.getNombreYApellidos());
						pStmt.setString(2, c.getGmail());
						pStmt.setString(3, c.getContrasena());
						pStmt.setString(4, c.getDireccion());
						pStmt.setString(5, c.getTelefono());

						if (pStmt.executeUpdate() != 1) {			
							log(Level.INFO, "No se ha insertado el cliente", null);
						} else {
							//Se actualiza el ID del cliente haciendo un Select
							c.setId(this.getClienteByNombreYApellidos(c.getNombreYApellidos()).getId());
							log(Level.INFO, "Se ha insertado el cliente", null);
						}
					}
					log(Level.INFO, clientes.length + "Clientes insertados en la BBDD", null);
				}
			} catch (Exception ex) {
				log(Level.SEVERE, "Error al insertar clientes", ex);
			}		
	}
	
	public void insertarTrabajador(Trabajador... trabajadores ) {
		String sql = "INSERT INTO EMPLEADOS (NOMBRE, GMAIL, CONTRASENA, ESTATUS, SALARIO, TELEFONO) VALUES (?, ?, ?, ?, ?, ?);";
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			//Se recorren los clientes y se insertan uno a uno
			for (Trabajador t : trabajadores) {
				// Si el trabajador no existe que se a�ada el trabajador a la BD
				if(comprobarTrabajador(t.getGmail(), t.getContrasena()) == false) {
					pstmt.setString(1, t.getNombreYApellidos());
					pstmt.setString(2, t.getGmail());
					pstmt.setString(3, t.getContrasena());
					pstmt.setString(4, t.getStatus().toString());
					pstmt.setDouble(5, t.getSalario());
					pstmt.setString(6, t.getTelefono());
					if (1 != pstmt.executeUpdate()) {
						log(Level.SEVERE, "No se ha insertado el trabajador" + t, null);
					} else {
						log(Level.INFO, "Se ha insertado el trabajador" + t, null);
					}
				}
				log(Level.INFO, trabajadores.length + "	Trabajadores insertados en la BBDD", null);
			}		
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al insertar trabajadores", ex);						
		}				
	}
	
	public void insertarProducto(Producto... productos) {
		String sql = "INSERT INTO PRODUCTOS (ARTICULO, DEPORTE, MARCA, GENERO, TALLA, PRECIO) VALUES (?, ?, ?, ?, ?, ?);";
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			//Se recorren los clientes y se insertan uno a uno
			for (Producto p : productos) {
				pstmt.setString(1, p.getArticulo());
				pstmt.setString(2, p.getDeporte());
				pstmt.setString(3, p.getMarca());
				pstmt.setString(4, p.getGenero().toString());
				pstmt.setString(5, p.getTalla());
				pstmt.setDouble(6, p.getPrecio());
				if (1 != pstmt.executeUpdate()) {	
					log(Level.SEVERE, "No se ha insertado el producto" + p, null);
				} else {
					log(Level.INFO, "Se ha insertado el producto" + p, null);
				}
			}			
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al insertar productos", ex);						
		}				
	}
	
	public List<Cliente> obtenerClientes() {
		List<Cliente> clientes = new ArrayList<>();
		
		
		//Se abre la conexión y se obtiene el Statement
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
			
			log(Level.INFO, "Se ha recuperado " + clientes.size() + " clientes", null);	
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener los clientes de la BD", ex);					
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
			
			log(Level.INFO, "Se ha recuperado el cliente" + cliente, null);				
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al recuperar el cliente" + cliente, ex);					
		}		
		return cliente;
	}
	
	public Cliente getClienteByGmail(String gmail) {
		Cliente cliente = null;
		String sql = "SELECT * FROM cliente WHERE gmail = ? LIMIT 1";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setString(1, gmail);
			
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
			
			log(Level.INFO, "Se ha recuperado el cliente" + cliente, null);		
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al recuperar el cliente" + cliente, ex);						
		}		
		return cliente;
	}
	
	public List<Trabajador> obtenerTrabajadores() {
		List<Trabajador> trabajadores = new ArrayList<>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT * FROM EMPLEADOS WHERE ID >= 0";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			Trabajador trabajador;
			
			//Se recorre el ResultSet y se crean objetos Trabajador
			while (rs.next()) {
				trabajador = new Trabajador();
				
				trabajador.setId(rs.getInt("ID"));
				trabajador.setNombreYApellidos(rs.getString("NOMBRE"));
				trabajador.setGmail(rs.getString("GMAIL"));
				trabajador.setContrasena(rs.getString("CONTRASENA"));
				trabajador.setStatus(Estatus.valueOf(rs.getString("ESTATUS")));
				trabajador.setSalario(rs.getFloat("SALARIO"));
				trabajador.setTelefono(rs.getString("TELEFONO"));
				
				//Se inserta cada nuevo trabajador en la lista de trabajadores
				trabajadores.add(trabajador);
			}
			//Se cierra el ResultSet
			rs.close();
			
			log(Level.INFO, "Se han recuperado " + trabajadores.size() + " trabajadores", null);			
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener los trabajadores de la BD", ex);					
		}		
		return trabajadores;
	}

	public Trabajador getTrabajadorByGmail(String gmail) {
		Trabajador trabajador = null;
		String sql = "SELECT * FROM EMPLEADOS WHERE gmail = ? LIMIT 1";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setString(1, gmail);
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			//Se procesa el unico resultado
			if (rs.next()) {
				trabajador = new Trabajador();
				
				trabajador.setId(rs.getInt("ID"));
				trabajador.setNombreYApellidos(rs.getString("NOMBRE"));
				trabajador.setGmail(rs.getString("GMAIL"));
				trabajador.setContrasena(rs.getString("CONTRASENA"));
				trabajador.setStatus(Estatus.valueOf(rs.getString("ESTATUS")));
				trabajador.setSalario(rs.getDouble("SALARIO"));
				trabajador.setTelefono(rs.getString("TELEFONO"));
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			log(Level.INFO, "Se ha recuperado el trabajador", null);			
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al recuperar el cliente", ex);					
		}		
		return trabajador;
	}
	
	public List<Producto> obtenerProductos() {
		List<Producto> productos = new ArrayList<>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT * FROM PRODUCTOS WHERE ID >= 0";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			Producto producto;
			
			//Se recorre el ResultSet y se crean objetos Producto (Calzado/Ropa)
			while (rs.next()) {
				producto = new Producto();
				
				producto.setId(rs.getInt("ID"));
				producto.setArticulo(rs.getString("ARTICULO"));
				producto.setDeporte(rs.getString("DEPORTE"));
				producto.setMarca(rs.getString("MARCA"));
				producto.setGenero(Genero.valueOf(rs.getString("GENERO")));
				producto.setTalla(rs.getString("TALLA"));
				producto.setPrecio(rs.getDouble("PRECIO"));
				
				productos.add(producto);
			}
			//Se cierra el ResultSet
			rs.close();
			
			
			log(Level.INFO, "Se han recuperado " + productos.size() +  " productos", null);			
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener productos de la BD", ex);						
		}		
		return productos;
	}
	
	public List<Producto> obtenerProductosFiltro(String articulo, String deporte, String marca, String genero, double precio) {
		List<Producto> productos = new ArrayList<>();

		String sql = "SELECT * FROM PRODUCTOS WHERE ID >= 0 AND ARTICULO = '" + articulo + "' AND DEPORTE = '" + deporte +"' AND MARCA = '" + marca + "' AND GENERO = '" + genero.toUpperCase() + "' AND PRECIO <= " + precio;
		//Se abre la conexión y se obtiene el Statement
		System.out.println("Esta es la sql ----------" + sql);
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
	
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			Producto producto;
			
			//Se recorre el ResultSet y se crean objetos Producto (Calzado/Ropa)
			while (rs.next()) {
				producto = new Producto();
				
				producto.setId(rs.getInt("ID"));
				producto.setArticulo(rs.getString("ARTICULO"));
				producto.setDeporte(rs.getString("DEPORTE"));
				producto.setMarca(rs.getString("MARCA"));
				producto.setGenero(Genero.valueOf(rs.getString("GENERO")));
				producto.setTalla(rs.getString("TALLA"));
				producto.setPrecio(rs.getDouble("PRECIO"));
				
				productos.add(producto);
			}
			//Se cierra el ResultSet
			rs.close();
			
			log(Level.INFO, "Se han recuperado " + productos.size() +  " productos", null);				
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener productos de la BD", ex);						
		}		
		return productos;
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
			
			log(Level.INFO, "Se ha actualizado el salario de " + trabajador, null);	
		} catch (Exception ex) {
			log(Level.SEVERE, "Error actualizando el salario del trabajador " + trabajador, ex);					
		}		
	}
	
	public boolean comprobarCliente(String gmail, String contrasena) {
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement pStmt = con.createStatement()) {		
			String sql = "SELECT GMAIL,CONTRASENA FROM CLIENTES WHERE GMAIL = '" + gmail + "' and CONTRASENA = '" + contrasena + "' LIMIT 1";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery(sql);

			if (rs.next()) {
				log(Level.INFO, "Cliente confirmado", null);	
				return true;
			}
			rs.close();
		} catch (Exception ex) {
			log(Level.SEVERE, "Error cliente inexistente en la BD", ex );
		}
		return false;		
	}
	
	public boolean comprobarTrabajador(String gmail, String contrasena) {
		String sql = "SELECT GMAIL,CONTRASENA FROM empleados WHERE GMAIL = '" + gmail + "' and CONTRASENA = '" + contrasena + "' LIMIT 1";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				log(Level.INFO, "Trabajador confirmado", null );
				return true;
			}
		} catch (Exception ex) {				
			log(Level.SEVERE, "Error trabajador inexistente en la BD", ex );
		}
		return false;		
	}
	
	public List<Producto> loadCSVProductos() {
		List<Producto> productos = new ArrayList<>();
		
		try (BufferedReader in = new BufferedReader(new FileReader("data/PRODUCTOS.csv"))) {
			String linea = null;
			
			//Omitir la cabecera
			in.readLine();			
			
			while ((linea = in.readLine()) != null) {
				productos.add(Producto.parseCSV(linea));
			}	
			log(Level.INFO, "Productos cargados", null );
		} catch (Exception ex) {
			log(Level.SEVERE, "Error leyendo productos del CSV", ex );
		}
		return productos;
	}
	
	private void setLogger( Logger logger ) {
		this.logger = logger;
	}
	
	private void log(Level level, String msg, Throwable excepcion) {
		if (logger == null) {
			logger = logger.getLogger("BD-local");
			logger.setLevel(Level.ALL);
			try {
				logger.addHandler(new FileHandler("log/bd.log.xml", true));
			} catch(Exception e) {
				logger.log(Level.SEVERE, "No se pudo crear el fichero de log", e);
			}
		}
		if (excepcion == null) {
			logger.log(level, msg);
		} else {
			logger.log(level, msg, excepcion);
		}
	}
	
	public void borrarCliente(Cliente cliente) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "DELETE FROM CLIENTES WHERE ID = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, cliente.getId()));
			
			log(Level.INFO, "Se ha borrado al cliente" + cliente, null );
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al borrar el cliente en la BD", ex );				
		}		
	}
	
	public void borrarTrabajador(Trabajador trabajador) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "DELETE FROM CLIENTES WHERE ID = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, trabajador.getId()));
			
			log(Level.INFO, "Se ha borrado al cliente" + trabajador, null );
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al borrar el trabajador en la BD", ex );						
		}		
	}
	
	
	
	public void borrarDatos(String tabla) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "DELETE FROM " + tabla + ";";			
			int result = stmt.executeUpdate(sql);
			
			log(Level.INFO, "Se ha borrado la tabla " + tabla, null );
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al borrar la tabla " + tabla + " de la BD", ex );					
		}		
	}	
}
 