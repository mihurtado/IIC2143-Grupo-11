import java.util.ArrayList;
import java.util.List;

public class Pedido {
	private int id;
	private Cliente cliente;
	private Sucursal origen;
  	private Sucursal destino;
  	public Estado estado;
    private PrioridadPedido urgencia;

  	private OrdenCompra orden_compra;
  	private List<Encomienda> encomiendas;

  	public Pedido(Cliente cliente, Sucursal origen, Sucursal destino, PrioridadPedido urgencia) {
    	this.id = Sistema.GetInstance().Get_id_pedido();
      Initialize(cliente, origen, destino, urgencia);
  	}

    public Pedido(int id, Cliente cliente, Sucursal origen, Sucursal destino, PrioridadPedido urgencia) {
      this.id = id;
      Initialize(cliente, origen, destino, urgencia);
    }

    private void Initialize(Cliente cliente, Sucursal origen, Sucursal destino, PrioridadPedido urgencia) {
      this.cliente = cliente;
      this.origen = origen;
      this.destino = destino;
      this.urgencia = urgencia;
      this.estado = Estado.EnSucursalOrigen;
      this.encomiendas = new ArrayList<Encomienda>();
    }

  	public int GetId() {return id;}

    public PrioridadPedido GetUrgencia () {return urgencia;}

  	public void AgregarEncomienda (Encomienda encomienda) {
    	this.encomiendas.add(encomienda);
  	}

  	public int CalcularMonto() {
  		int monto = 0;
  		for (Encomienda e : encomiendas) {
  			monto += e.GenerarPresupuesto();
  		}
  		return monto;
  	}

 	public void GenerarOrden() {
    	this.orden_compra = new OrdenCompra(CalcularMonto());
  	}
}
