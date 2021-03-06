package backend;

public class Mensaje {
	
	private int id;
	private String texto;
	private Sucursal origen;
	private Sucursal destino;
	private Empleado creadoPor;
	
	public Mensaje (String texto, Sucursal destino, Empleado creador) {
		this.id = Sistema.GetInstance().Get_id_mensaje();
		this.Initialize(texto, destino, creador);
	}

	public Mensaje (int id, String texto, Sucursal destino, Empleado creador) {
		this.id = id; 
		this.Initialize(texto, destino, creador);
	}

	private void Initialize(String texto, Sucursal destino, Empleado creador) {
		this.texto = texto;
		this.origen = creador.GetSucursal();
		this.destino = destino;
		this.creadoPor = creador;
	}
	
	public int GetId() {return id;}
	public String GetTexto() {return texto;}
	public Sucursal GetOrigen() {return origen;}
	public Sucursal GetDestino() {return destino;}	
	public String GetCreador() {
		return creadoPor.GetNombre();
	}

		public String GetCreadorRut() {
		return creadoPor.GetRut();
	}
}
