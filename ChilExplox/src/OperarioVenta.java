
public class OperarioVenta extends Empleado {
  public int Crear_Pedido (Cliente cliente, Sucursal origen, Sucursal destino, int urgencia) {
    Pedido p = new Pedido (cliente, origen, destino, urgencia);
    Sistema.AgregarPedido (p);
    return p.GetId();
  }

  public int CrearEncomienda (Pedido pedido, int peso, int volumen) {
  	Encomienda e = new Encomienda(peso, volumen);
  	Sistema.AgregarEncomienda (e);
  	return e.GetId();
  }
}
