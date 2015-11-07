package backend;

import java.util.ArrayList;

public abstract class MedioDeTransporte {
	protected String patente;
	protected int capacidadMax;  // Tiene que ser capacidad como cm3, hay que cambiar el valor del cami�n creado y los futuros camiones tienen que tener harta cap.
	protected int capacidadActual;
	// protected int pesoMax; Estamos analizando si ponerle el peso como variable, en gral no se env�an un monton de pedidos pesados, eso es mas para carga industrial, no de correos
	// protected int pesoDisponible;
	protected int enUso;
	protected boolean desocupado;
	protected Empleado conductor;
	protected Sucursal origen;
	protected Sucursal destino;
	protected Estado estado;
	protected ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();

	/*
	 * Constructor en caso de que este desocupado en origen
	 */
	public MedioDeTransporte (String patente, int capacidadMax, Sucursal origen) {
		this.patente = patente;
		this.capacidadMax = capacidadMax;
		this.origen = origen;
		this.destino = null;
		this.desocupado = true;
	}
	
	/*
	 * Constructor en caso de estar viajando
	 */
	public MedioDeTransporte (String patente, int capacidadMax, Sucursal origen, Sucursal destino) {
		this.patente = patente;
		this.capacidadMax = capacidadMax;
		this.origen = origen;
		this.destino = destino;
		this.desocupado = false;
	}

	public void setOrigen(Sucursal origen){
		this.origen = origen;
	}

	public void setDestino(Sucursal destino){
		this.destino = destino;
	}

	public void setConductor(Empleado conductor){
		this.conductor = conductor;
	}

	public void Viajar(){
		this.estado = Estado.EnTransito;
		for(int i = 0; i <= this.listaPedidos.size(); i++){
			Pedido pedido = this.listaPedidos.get(i);
			pedido.SetEnTransito();
			pedido.Enviado();
			pedido.SetConductor(conductor);
		}
	}

	public int GetCapacidadDisponible(){
		return (this.capacidadMax - this.capacidadActual);
	}

	public Estado GetEstado() {return this.estado;}

	public Sucursal GetOrigen(){return this.origen;}

	public Sucursal GetDestino(){return this.destino;}

	public String GetPatente() {return this.patente;}

	public int GetCapacidadMax() {return this.capacidadMax;}

	public int GetCapacidadActual() {return this.capacidadActual;}

	public boolean GetDesocupado(){ return this.desocupado; }

	public ArrayList<Pedido> GetPedidos(){
		return this.listaPedidos;
	}

	public boolean CargarPedido(Pedido pedido){
		if(this.capacidadActual < this.capacidadMax){
			listaPedidos.add(pedido);
			//avisar al pedido que fue cargado
			pedido.Cargado(this);
			this.capacidadActual += pedido.GetVolumen();
			return true;
		}
		else{
			this.desocupado = false;
			return false;
		}
	}

	public void Desocupar() {
		this.capacidadActual = 0;
		this.desocupado = true;
		this.listaPedidos.clear();
	}
}