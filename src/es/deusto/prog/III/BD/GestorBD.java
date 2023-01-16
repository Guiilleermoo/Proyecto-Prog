 package es.deusto.prog.III.BD;

import java.io.*;
import java.nio.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.*;

import javax.net.ssl.SSLEngineResult.Status;

import es.deusto.prog.III.*;
import es.deusto.prog.III.Pedido.Estado;
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

	public void insertarClientes(Cliente... clientes ) {
		//Se define la plantilla de la sentencia SQL
		String sql = "INSERT INTO CLIENTE (NOMBREYAPELLIDOS, GMAIL, CONTRASENA, DIRECCION, TELEFONO) VALUES (?, ?, ?, ?, ?);";
				
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
		String sql = "INSERT INTO TRABAJADOR (ID_T, NOMBREYAPELLIDOS, GMAIL, CONTRASENA, ESTATUS, SALARIO, TELEFONO) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			//Se recorren los clientes y se insertan uno a uno
			for (Trabajador t : trabajadores) {
				// Si el trabajador no existe que se a�ada el trabajador a la BD
				if(comprobarTrabajador(t.getGmail(), t.getContrasena()) == false) {
					pstmt.setInt(1, t.getId());
					pstmt.setString(2, t.getNombreYApellidos());
					pstmt.setString(3, t.getGmail());
					pstmt.setString(4, t.getContrasena());
					pstmt.setString(5, t.getStatus().toString());
					pstmt.setDouble(6, t.getSalario());
					pstmt.setString(7, t.getTelefono());
					
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
		String sql = "INSERT INTO PRODUCTO (ARTICULO, DEPORTE, MARCA, GENERO, TALLA, PRECIO, STOCK) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
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
				pstmt.setInt(7, p.getCantidad());
				
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
			
			String sql = "SELECT * FROM CLIENTE WHERE ID_C >= 0";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			Cliente cliente;
			
			//Se recorre el ResultSet y se crean objetos Cliente
			while (rs.next()) {
				cliente = new Cliente();
				
				cliente.setId(rs.getInt("ID_C"));
				cliente.setNombreYApellidos(rs.getString("NOMBREYAPELLIDOS"));
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
		String sql = "SELECT * FROM CLIENTE WHERE NOMBREYAPELLIDOS = ? LIMIT 1";
		
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
				
				cliente.setId(rs.getInt("ID_C"));
				cliente.setNombreYApellidos(rs.getString("NOMBREYAPELLIDOS"));
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
	
	public Cliente getClienteById(int id) {
		Cliente cliente = null;
		String sql = "SELECT * FROM CLIENTE WHERE ID_C = ? LIMIT 1";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setInt(1, id);
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			//Se procesa el unico resultado
			if (rs.next()) {
				cliente = new Cliente();
				
				cliente.setId(rs.getInt("ID_C"));
				cliente.setNombreYApellidos(rs.getString("NOMBREYAPELLIDOS"));
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
		String sql = "SELECT * FROM CLIENTE WHERE GMAIL = ? LIMIT 1";
		
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
				
				cliente.setId(rs.getInt("ID_C"));
				cliente.setNombreYApellidos(rs.getString("NOMBREYAPELLIDOS"));
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
			
			String sql = "SELECT * FROM TRABAJADOR WHERE ID_T >= 0";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			Trabajador trabajador;
			
			//Se recorre el ResultSet y se crean objetos Trabajador
			while (rs.next()) {
				trabajador = new Trabajador();
				
				trabajador.setId(rs.getInt("ID_T"));
				trabajador.setNombreYApellidos(rs.getString("NOMBREYAPELLIDOS"));
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
			System.out.println(ex);
			log(Level.SEVERE, "Error al obtener los trabajadores de la BD", ex);					
		}		
		return trabajadores;
	}

	public Trabajador getTrabajadorByGmail(String gmail) {
		Trabajador trabajador = null;
		String sql = "SELECT * FROM TRABAJADOR WHERE GMAIL = ? LIMIT 1";
		
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
				
				trabajador.setId(rs.getInt("ID_T"));
				trabajador.setNombreYApellidos(rs.getString("NOMBREYAPELLIDOS"));
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
			log(Level.SEVERE, "Error al recuperar el trabajador", ex);					
		}		
		return trabajador;
	}
	public List<Pedido> obtenerPedidos() {
		List<Pedido> pedidos = new ArrayList<>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT * FROM PEDIDO";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			Pedido pedido;
			
			while (rs.next()) {
				pedido = new Pedido();
				
				pedido.setCliente(rs.getString("ID_C"));
				pedido.setFecha(rs.getDate("FECHA"));
				pedido.setEstado(Estado.valueOf(rs.getString("ESTADO")));
				

				pedidos.add(pedido);
			}

			//Se cierra el ResultSet
			rs.close();
			
			log(Level.INFO, "Se han recuperado " + pedidos.size() + " pedidos", null);			
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener los pedidos de la BD", ex);					
		}		
		return pedidos;
	}
	
	public List<Pedido> obtenerPedidosCliente(int id) {
		List<Pedido> pedidos = new ArrayList<>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT * FROM PEDIDO WHERE ID_C = " + id;
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			Pedido pedido;
			

			while (rs.next()) {
				pedido = new Pedido();
				
				pedido.setId(rs.getInt("ID_P"));
				pedido.setCliente(rs.getString("ID_C"));
				pedido.setFecha(rs.getDate("FECHA"));
				pedido.setEstado(Estado.valueOf(rs.getString("ESTADO")));

				pedidos.add(pedido);
			}
			//Se cierra el ResultSet
			rs.close();
			
			log(Level.INFO, "Se han recuperado " + pedidos.size() + " pedidos", null);			
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener los pedidos de la BD", ex);					
		}		
		return pedidos;
	}
	
	public List<Producto> obtenerProductosTodos() {
		List<Producto> productos = new ArrayList<>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT * FROM PRODUCTO;";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);
			
			Producto producto;
			
			//Se recorre el ResultSet y se crean objetos Producto
			while (rs.next()) {
				producto = new Producto();
				
				producto.setId(rs.getInt("ID_PROD"));
				producto.setArticulo(rs.getString("ARTICULO"));
				producto.setDeporte(rs.getString("DEPORTE"));
				producto.setMarca(rs.getString("MARCA"));
				producto.setGenero(Genero.valueOf(rs.getString("GENERO").toUpperCase()));
				producto.setTalla(rs.getString("TALLA"));
				producto.setPrecio(rs.getDouble("PRECIO"));
				producto.setCantidad(rs.getInt("STOCK"));

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
	
	public List<Producto> obtenerProductos() {
		List<Producto> productos = new ArrayList<>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT * FROM PRODUCTO GROUP BY ARTICULO, DEPORTE, MARCA;";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);
			
			Producto producto;
			
			//Se recorre el ResultSet y se crean objetos Producto
			while (rs.next()) {
				producto = new Producto();
				
				producto.setArticulo(rs.getString("ARTICULO"));
				producto.setDeporte(rs.getString("DEPORTE"));
				producto.setMarca(rs.getString("MARCA"));
				producto.setGenero(Genero.valueOf(rs.getString("GENERO").toUpperCase()));
				producto.setTalla(rs.getString("TALLA"));
				producto.setPrecio(rs.getDouble("PRECIO"));
				producto.setCantidad(rs.getInt("STOCK"));

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
	
	public int getLastId() {
		int id = 0;
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT MAX(ID_PROD) AS 'MAXIMO' FROM PRODUCTO;";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);

			//Se recorre el ResultSet y se crean objetos Producto
			while (rs.next()) {
				id = rs.getInt("MAXIMO");
			}
			//Se cierra el ResultSet
			rs.close();
			
			
			log(Level.INFO, "Se ha recuperado el id maximo: " + id, null);			
		} catch (Exception ex) {

			log(Level.SEVERE, "Error al obtener el id maximo: " + id + " de la BD", ex);
		}
		return id;
	}
	
	public Producto getProductoById(int id) {
		Producto producto = null;
		String sql = "SELECT * FROM PRODUCTO WHERE ID_PROD = ? LIMIT 1";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setInt(1, id);
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			//Se procesa el unico resultado
			if (rs.next()) {
				producto = new Producto();
				
				producto.setId(rs.getInt("ID_PROD"));
				producto.setArticulo(rs.getString("ARTICULO"));
				producto.setDeporte(rs.getString("DEPORTE"));
				producto.setMarca(rs.getString("MARCA"));
				producto.setTalla(rs.getString("TALLA"));
				producto.setGenero(Genero.valueOf(rs.getString("GENERO").toUpperCase()));
				producto.setPrecio(rs.getDouble("PRECIO"));
				producto.setCantidad(rs.getInt("STOCK"));
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			log(Level.INFO, "Se ha recuperado el producto", null);			
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al recuperar el producto", ex);
			System.out.println(ex);
		}		
		return producto;
	}
	
	public String[] obtenerMarcas() {
		String[] marcas = null;
		List<String> marcasList = new ArrayList<String>();
		marcasList.add("Cualquiera");
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT DISTINCT MARCA FROM PRODUCTO;";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			
			//Se recorre el ResultSet y se crean objetos Producto (Calzado/Ropa)
			while (rs.next()) {
				marcasList.add(rs.getString("MARCA"));
			}
			//Se cierra el ResultSet
			rs.close();

			marcas = marcasList.toArray(new String[0]);

			log(Level.INFO, "Se han recuperado " + marcas.length +  " marcas", null);			 
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener marcas de la BD", ex);						
		}		
		return marcas;
	}
	
	public String[] obtenerArticulos() {
		String[] articulos = null;
		List<String> articulosList = new ArrayList<String>();
		articulosList.add("Cualquiera");
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT DISTINCT ARTICULO FROM PRODUCTO;";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			
			//Se recorre el ResultSet y se crean objetos Producto (Calzado/Ropa)
			while (rs.next()) {
				articulosList.add(rs.getString("ARTICULO"));
			}
			//Se cierra el ResultSet
			rs.close();

			articulos = articulosList.toArray(new String[0]);

			log(Level.INFO, "Se han recuperado " + articulos.length +  " articulos", null);			 
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener articulos de la BD", ex);						
		}		
		return articulos;
	}
	
	public String[] obtenerDeportes() {
		String[] deportes = null;
		List<String> deportesList = new ArrayList<String>();
		deportesList.add("Cualquiera");
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT DISTINCT DEPORTE FROM PRODUCTO;";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			
			//Se recorre el ResultSet y se crean objetos Producto (Calzado/Ropa)
			while (rs.next()) {
				deportesList.add(rs.getString("DEPORTE"));
			}
			//Se cierra el ResultSet
			rs.close();

			deportes = deportesList.toArray(new String[0]);

			log(Level.INFO, "Se han recuperado " + deportes.length +  " deportes", null);			 
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener deportes de la BD", ex);						
		}		
		return deportes;
	}
	
	public String[] obtenerGenero(String articulo, String deporte, String marca) {
		String[] generos = null;
		List<String> generosList = new ArrayList<String>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT * FROM PRODUCTO WHERE ID_PROD >= 0 AND ARTICULO = '" + articulo + "' AND DEPORTE = '" + deporte +"' AND MARCA = '" + marca + "' GROUP BY GENERO";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			
			//Se recorre el ResultSet y se crean objetos Producto (Calzado/Ropa)
			while (rs.next()) {
				generosList.add(rs.getString("GENERO"));
			}
			//Se cierra el ResultSet
			rs.close();
			
			generos = generosList.toArray(new String[0]);

			log(Level.INFO, "Se han recuperado " + generos.length +  " productos", null);			 
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener generos de la BD", ex);						
		}		
		return generos;
	}
	
	public String[] obtenerTalla(String articulo, String deporte, String marca, String genero) {
		String[] tallas = null;
		List<String> tallasList = new ArrayList<String>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			
			String sql = "SELECT DISTINCT TALLA FROM PRODUCTO WHERE ARTICULO = '" + articulo + "' AND DEPORTE = '" + deporte +"' AND MARCA = '" + marca + "' AND GENERO = '" + genero + "';";

			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			

			//Se recorre el ResultSet y se crean objetos Producto (Calzado/Ropa)
			while (rs.next()) {
				tallasList.add(rs.getString("TALLA"));
			}
			
			//Se cierra el ResultSet
			rs.close();

			tallas = tallasList.toArray(new String[0]);

			log(Level.INFO, "Se han recuperado " + tallas.length +  " productos", null);			 
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener las tallas de la BD", ex);						
		}
		return tallas;
	}
	
	public Double obtenerDinero(String articulo, String deporte, String marca, String genero, String talla) {
		Double precio = null;
		
		String sql = "SELECT PRECIO FROM PRODUCTO WHERE ARTICULO = ? AND DEPORTE = ? AND MARCA = ? AND TALLA = ? AND GENERO = ? LIMIT 1";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se definen los parámetros de la sentencia SQL
			pStmt.setString(1, articulo);
			pStmt.setString(2, deporte);
			pStmt.setString(3, marca);
			pStmt.setString(4, talla);
			pStmt.setString(5, genero);
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = pStmt.executeQuery();			

			//Se procesa el unico resultado
			if (rs.next()) {
				precio = rs.getDouble("PRECIO");
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			log(Level.INFO, "Se ha recuperado el precio del producto", null);			
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al recuperar el precio del producto", ex);
			System.out.println(ex);
		}		
		return precio;
	}
	
	public List<Producto> obtenerProductosFiltro(String articulo, String deporte, String marca, double precio) {
		List<Producto> productos = new ArrayList<>();
		
		String sql = "SELECT * FROM PRODUCTO WHERE ID_PROD >= 0 ";
		
		if (articulo != "Cualquiera") {
			sql += " AND ARTICULO = '" + articulo + "' ";
		}
		if (deporte != "Cualquiera") {
			sql += " AND DEPORTE = '" + deporte + "' ";
		}
		if (marca != "Cualquiera") {
			sql += " AND MARCA = '" + marca + "' ";
		}
		sql += " AND PRECIO <= " + precio + " GROUP BY ARTICULO, DEPORTE, MARCA";

		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
	
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);
			Producto producto;

			//Se recorre el ResultSet y se crean objetos Producto (Calzado/Ropa)
			while (rs.next()) {
				producto = new Producto();
				
				producto.setId(rs.getInt("ID_PROD"));
				producto.setArticulo(rs.getString("ARTICULO"));
				producto.setDeporte(rs.getString("DEPORTE"));
				producto.setMarca(rs.getString("MARCA"));
				producto.setGenero(Genero.valueOf(rs.getString("GENERO").toUpperCase()));
				producto.setTalla(rs.getString("TALLA"));
				producto.setPrecio(rs.getDouble("PRECIO"));
				producto.setCantidad(rs.getInt("STOCK"));

				productos.add(producto);
			}
			//Se cierra el ResultSet
			rs.close();
			
			log(Level.INFO, "Se han recuperado " + productos.size() +  " productos", null);				
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener productos de la BD", ex);	
			System.out.println(ex);
		}		
		return productos;
	}
	
	public List<Producto> obtenerProductosFiltro2(String articulo, String deporte, String marca) {
		List<Producto> productos = new ArrayList<>();
		
		String sql = "SELECT * FROM PRODUCTO WHERE ID_PROD >= 0 ";
		
		if (articulo != "Cualquiera") {
			sql += " AND ARTICULO = '" + articulo + "' ";
		}
		if (deporte != "Cualquiera") {
			sql += " AND DEPORTE = '" + deporte + "' ";
		}
		if (marca != "Cualquiera") {
			sql += " AND MARCA = '" + marca + "' ";
		}
		sql += " GROUP BY ARTICULO, DEPORTE, MARCA";

		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
	
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);
			Producto producto;

			//Se recorre el ResultSet y se crean objetos Producto (Calzado/Ropa)
			while (rs.next()) {
				producto = new Producto();
				
				producto.setId(rs.getInt("ID_PROD"));
				producto.setArticulo(rs.getString("ARTICULO"));
				producto.setDeporte(rs.getString("DEPORTE"));
				producto.setMarca(rs.getString("MARCA"));
				producto.setGenero(Genero.valueOf(rs.getString("GENERO").toUpperCase()));
				producto.setTalla(rs.getString("TALLA"));
				producto.setPrecio(rs.getDouble("PRECIO"));
				producto.setCantidad(rs.getInt("STOCK"));

				productos.add(producto);
			}
			//Se cierra el ResultSet
			rs.close();
			
			log(Level.INFO, "Se han recuperado " + productos.size() +  " productos", null);				
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al obtener productos de la BD", ex);	
			System.out.println(ex);
		}		
		return productos;
	}
	
	public void actualizarContrasena(Cliente cliente, String contrasenaNueva) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "UPDATE CLIENTE SET CONTRASENA = '%s' WHERE ID_C = %d;";
			
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
			String sql = "UPDATE TRABAJADOR SET SALARIO = '%s' WHERE ID_T = %d;";

			int result = stmt.executeUpdate(String.format(sql, salario, trabajador.getId()));
			
			log(Level.INFO, "Se ha actualizado el salario de " + trabajador, null);	
		} catch (Exception ex) {
			log(Level.SEVERE, "Error actualizando el salario del trabajador " + trabajador, ex);					
		}		
	}
	
	public void actualizarPrecio(int id, double precio) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "UPDATE PRODUCTO SET PRECIO = '%s' WHERE ID_PROD = %d;";

			int result = stmt.executeUpdate(String.format(sql, precio, id));
			
			log(Level.INFO, "Se ha actualizado el precio del producto con id: " + id , null);	
		} catch (Exception ex) {
			log(Level.SEVERE, "Error actualizando el precio del producto con id: " + id, ex);
		}		
	}
	
	public void actualizarStock(int id, int stock) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "UPDATE PRODUCTO SET STOCK = STOCK + '%s' WHERE ID_PROD = %d;";

			int result = stmt.executeUpdate(String.format(sql, stock, id));
			
			log(Level.INFO, "Se ha actualizado el stock del producto con id:  " + id, null);	
		} catch (Exception ex) {
			log(Level.SEVERE, "Error actualizando el stock del producto con id: " + id, ex);
		}		
	}
	
	public void actualizarStockCompra(int id, int stock) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "UPDATE PRODUCTO SET STOCK = STOCK - '%s' WHERE ID_PROD = %d;";

			int result = stmt.executeUpdate(String.format(sql, stock, id));
			
			log(Level.INFO, "Se ha actualizado el stock del producto con id:  " + id, null);	
		} catch (Exception ex) {
			log(Level.SEVERE, "Error actualizando el stock del producto con id: " + id, ex);
		}		
	}
	
	public boolean comprobarCliente(String gmail, String contrasena) {
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement pStmt = con.createStatement()) {		
			String sql = "SELECT GMAIL,CONTRASENA FROM CLIENTE WHERE GMAIL = '" + gmail + "' and CONTRASENA = '" + contrasena + "' LIMIT 1";
			
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
		String sql = "SELECT GMAIL,CONTRASENA FROM TRABAJADOR WHERE GMAIL = '" + gmail + "' and CONTRASENA = '" + contrasena + "' LIMIT 1";
		
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
	
	private void setLogger( Logger logger ) {
		this.logger = logger;
	}
	
	public void log(Level level, String msg, Throwable excepcion) {
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
			String sql = "DELETE FROM CLIENTE WHERE ID_C = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, cliente.getId()));
			
			log(Level.INFO, "Se ha borrado al cliente" + cliente, null );
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al borrar el cliente en la BD", ex );				
		}		
	}
	
	public void borrarProducto(int id) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			//Se ejecuta la sentencia de borrado de datos
			String sql = "DELETE FROM PRODUCTO WHERE ID_PROD = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, id));
			
			log(Level.INFO, "Se ha borrado el producto con id: " + id, null );
		} catch (Exception ex) {
			log(Level.SEVERE, "Error al borrar el producto con id: " + id + " en la BD", ex );				
		}		
	}
	
	public void borrarTrabajador(int id) {
			//Se abre la conexión y se obtiene el Statement
			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
			     Statement stmt = con.createStatement()) {
				//Se ejecuta la sentencia de borrado de datos
				String sql = "DELETE FROM TRABAJADOR WHERE ID_T = %d;";
				
				int result = stmt.executeUpdate(String.format(sql, id));
				
				log(Level.INFO, "Se ha borrado al cliente" + id, null);
			} catch (Exception ex) {
				log(Level.SEVERE, "Error al borrar el trabajador en la BD", ex);						
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
 