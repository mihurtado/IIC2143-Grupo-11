import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//Fachada, Sistema
//Será una clase Singleton
public class Sistema {
	private static Sistema INSTANCE = new Sistema();

	// Estos no se si los dejamos aca o en empresa
	private int precioPorKilo;
	private int id_pedido;
	private int id_encomienda;
	private int id_sucursal;

	private Empresa empresa;

	//Constructor privado por singleton
	private Sistema() {

		//Definir buferes
		String sCurrentLine = "";
		BufferedReader br = null;
		String [] parametros;

		//leer archivo de empresa
		try {
			br = new BufferedReader(new FileReader("archivos/empresa.data"));
		} catch (FileNotFoundException e3) {
			// Archivo no encontrado
		}

		try {
			sCurrentLine = br.readLine();
		} catch (IOException e3) {
			// Error en la lectura
		}
		parametros = sCurrentLine.split(";");

		//instanciar clase empresa
		this.empresa = new Empresa(parametros[0], parametros[1]); //nombre, rut
		try {
			this.precioPorKilo = Integer.parseInt(parametros[2]);
			this.id_pedido = Integer.parseInt(parametros[3]);
			this.id_encomienda = Integer.parseInt(parametros[4]);
		}
		catch(Exception e) {
			// Error en los archivos	
		}

		//leer archivo de sucursales
		try {
			br = new BufferedReader(new FileReader("archivos/sucursales.data"));
		} catch (FileNotFoundException e2) {
			// Archivo no encontrado
		}

		int id_sucursal;
		String direccion;
		int telefono;
		int capacidad;
		Sucursal sucursal;
		
		try {
			while ((sCurrentLine = br.readLine()) != null) {
				parametros = sCurrentLine.split(";");
				
				try {
					id_sucursal = Integer.parseInt(parametros[0]);
					direccion = parametros[1];
					telefono = Integer.parseInt(parametros[2]);
					capacidad = Integer.parseInt(parametros[3]);
					sucursal = new Sucursal(id_sucursal, direccion, telefono, capacidad);
					this.empresa.AgregarSucursal(id_sucursal, sucursal);
				}
				catch(Exception e) {
					// Error en los archivos
				}

			}
		} catch (IOException e2) {
			// Error en la lectura
		}

		//leer archivo de empleados
		try {
			br = new BufferedReader(new FileReader("archivos/empleados.data"));
		} catch (FileNotFoundException e1) {
			// Archivo no encontrado
		}

		Empleado empleado;
		String empleado_id;
		String tipo_empleado;
		String nombre;
		int sueldo;

		try {
			while ((sCurrentLine = br.readLine()) != null) {

				try {
					parametros = sCurrentLine.split(";");
					id_sucursal = Integer.parseInt(parametros[0]);
					empleado_id = parametros[1]; // es el rut
					tipo_empleado = parametros[2];
					nombre = parametros[3];
					telefono = Integer.parseInt(parametros[4]);
					sueldo = Integer.parseInt(parametros[5]);
					sucursal = this.empresa.GetSucursal(id_sucursal);

					if (tipo_empleado == "venta") {
						empleado = new OperarioVenta(empleado_id, nombre, telefono, sueldo, sucursal);
					} else if (tipo_empleado == "camion") {
						empleado = new OperarioCamion(empleado_id, nombre, telefono, sueldo, sucursal);
					} else if (tipo_empleado == "bodega") {
						empleado = new OperarioBodega(empleado_id, nombre, telefono, sueldo, sucursal);
					} else {
						//error dato
						continue;
					}

					sucursal.AgregarEmpleado(empleado_id, empleado);

				}
				catch(Exception e) {
					// Error en los archivos
				}

			}
		} catch (IOException e1) {
			// Error en lectura
		}

		//leer archivo de clientes
		try {
			br = new BufferedReader(new FileReader("archivos/clientes.data"));
		} catch (FileNotFoundException e1) {
			// Archivo no encontrado
		}

		Cliente cliente;
		String rut;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				try {
					parametros = sCurrentLine.split(";");
					rut = parametros[0];  // es el rut
					nombre = parametros[1];
					telefono = Integer.parseInt(parametros[2]);
					direccion = parametros[3];

					cliente = new Cliente(rut, nombre, telefono, direccion);

					this.empresa.AgregarCliente(cliente);
				}
				catch(Exception e) {
					// Error en los archivos
				}

			}
		} catch (IOException e) {
			// Error en la lectura
		}
		
	}

	//Get instance para singleton
	public static Sistema GetInstance() {
		return INSTANCE;
	}
	
	public int GetPrecioPorKilo() {
		return precioPorKilo;
	}

	public int Get_id_pedido() {
		int valor = id_pedido;
		id_pedido++;
		return valor;
	}

	public int Get_id_encomienda() {
		int valor = id_encomienda;
		id_encomienda++;
		return valor;
	}

	public int Get_id_sucursal() {
		int valor = id_sucursal;
		id_sucursal++;
		return valor;
	}

	// Interaccion con usuario
	public int CrearPedido (OperarioVenta vendedor, Cliente cliente, Sucursal origen, Sucursal destino, int urgencia) {
	    return vendedor.CrearPedido(cliente, origen, destino, urgencia);
	}

	public int CrearEncomienda (OperarioVenta vendedor, Pedido pedido, int peso, int volumen) {
	  	return vendedor.CrearEncomienda(pedido, peso, volumen);
	}

	public void EnviarMensaje (OperarioBodega creador, String texto, Sucursal destino) {
		Mensaje m = creador.CrearMensaje(texto, destino);
		// Enviar mensaje
	}





	// Metodos de Empresa
	public  void AgregarPedido(Pedido pedido) {
		empresa.AgregarPedido(pedido);
	}

	public  Pedido GetPedido (int id) {
		return empresa.GetPedido(id);
	}

	public  void AgregarEncomienda(Encomienda encomienda) {
		empresa.AgregarEncomienda(encomienda);
	}

	public  Encomienda GetEncomienda (int id) {
		return empresa.GetEncomienda(id);
	}

	public void AgregarTransporte(MedioDeTransporte vehiculo){
		empresa.AgregarTransporte(vehiculo);
	}

	public MedioDeTransporte GetVehiculo (String patente){
		return empresa.GetTransporte(patente);
	}
}
