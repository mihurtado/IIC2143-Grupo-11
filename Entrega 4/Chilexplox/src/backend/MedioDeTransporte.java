package backend;

import java.util.ArrayList;

public abstract class MedioDeTransporte {
	protected String patente;
	protected int capacidadMax;
	protected int capacidadActual;
	protected int enUso;
	protected Sucursal origen;
	protected Sucursal destino;
	protected Estado estado;
	protected ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();

	public MedioDeTransporte (String patente, int capacidadMax) {
		this.patente = patente;
		this.capacidadMax = capacidadMax;
		this.origen = null;
		this.destino = null;
	}

	public void setOrigenDestino(Sucursal origen, Sucursal destino){
		this.origen = origen;
		this.destino = destino;
	}

	public void Viajar(){
		this.estado = Estado.Viajando;
	}

	public int GetCapacidadDisponible(){
		return (this.capacidadMax - this.capacidadActual);
	}

	public String GetPatente() {return this.patente;}

	public int GetCapacidadMax() {return this.capacidadMax;}

	public void CargarPedido(Pedido pedido){
		if(this.capacidadActual < this.capacidadMax){
			listaPedidos.add(pedido);
			this.capacidadActual += 1;
		}
		else{
			// Mensaje que ya esta lleno
		}
	}
}