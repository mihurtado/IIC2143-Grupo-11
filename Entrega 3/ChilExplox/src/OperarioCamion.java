
public class OperarioCamion extends Empleado {

	public OperarioCamion(String nombre, String rut, int telefono, int sueldo, Sucursal sucursal){
		super(nombre, rut, telefono, sueldo, sucursal);
		this.tipo_empleado = "camion";
	}

	public void DespacharCamion(Camion camion, Sucursal origen){
		origen.EnviarCamion(camion);
	}

	public void AvisarArrivoCamion(Camion camion, Sucursal destino){
		destino.AgregarCamionEsperando(camion);
		camion.estado = Estado.EnSucursalDestino;
	}
  
}
