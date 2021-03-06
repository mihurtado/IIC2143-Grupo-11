package chilexplox.view;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import backend.Pedido;
import backend.Sistema;
import backend.Sucursal;
import backend.Viaje;
import chilexplox.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReporteGananciasController {
	
	private MainApp mainApp;
	
	/*
	 * Tabla pedidos y sus columnas
	 */
	@FXML
	private TableView<ReportePedidoTableModel> tabla_pedidos;

	@FXML
    private TableColumn<ReportePedidoTableModel, String> id_pedidoColumn;
    @FXML
    private TableColumn<ReportePedidoTableModel, String> origenColumn;
    @FXML
    private TableColumn<ReportePedidoTableModel, String> destinoColumn;
    @FXML
    private TableColumn<ReportePedidoTableModel, String> fechaColumn;
    @FXML
    private TableColumn<ReportePedidoTableModel, String> montoColumn;
	
	/*
     * Data de los pedidos para la tabla
     */
	private ObservableList<ReportePedidoTableModel> pedidosData;
	
	@FXML
	private Label cantidadPedidos;
	
	@FXML
	private Label totalIngresos;
	
	@FXML
	private Label totalViajes;
	
	@FXML
	private DatePicker fechaDesde;
	
	@FXML
	private DatePicker fechaHasta; 
	
	@FXML
	private ChoiceBox sucursal;
	
	@FXML
    private void initialize() {
		// Set las properties para que se actualice la tabla pedidos
		this.id_pedidoColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.origenColumn.setCellValueFactory(cellData -> cellData.getValue().origenProperty());
        this.destinoColumn.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
        this.fechaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        this.montoColumn.setCellValueFactory(cellData -> cellData.getValue().montoProperty());
        
        //Inicializar observablearraylist
        this.pedidosData = FXCollections.observableArrayList();
        
        // Poner fechas desde hace 1 semana hoy
        this.fechaDesde.setValue(LocalDate.now().minusMonths(1));
        this.fechaHasta.setValue(LocalDate.now());
        
        // Poner sucursales
        HashMap<String, Sucursal> sucursales = new HashMap<String, Sucursal>(); 
        this.sucursal.getItems().add("Todas");
        this.sucursal.getSelectionModel().select(0);
		for (Map.Entry<Integer, Sucursal> entry : Sistema.GetInstance().GetSucursales().entrySet()) {
			Sucursal s = entry.getValue();
			this.sucursal.getItems().add(s.GetDireccion());
		}
        
        //Consulta inicial
        this.CargarPedidos(false);
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML
	private void handleConsultar() {
		
		//Validaciones de fechas
		if (fechaDesde.getValue() == null) {
			ViewHelper.ShowMessage("Seleccione una fecha inicial de periodo.", AlertType.ERROR);
			return;
		}
		
		if (fechaHasta.getValue() == null) {
			ViewHelper.ShowMessage("Seleccione una fecha de termino de periodo.", AlertType.ERROR);
			return;
		}
		
		if (fechaDesde.getValue().isAfter(fechaHasta.getValue())) {
			ViewHelper.ShowMessage("La fecha inicial no puede ser posterior a la final.", AlertType.ERROR);
			return;
		}
		
		if (sucursal.getSelectionModel().isEmpty()) {
			ViewHelper.ShowMessage("Seleccione una sucursal.", AlertType.ERROR);
			return;
		}
		
		this.CargarPedidos(true);
	}
	
	private void CargarPedidos(boolean mostrarMensaje) {
		//Cargar pedidos y totales
		int totalPedidos = 0;
		int totalMonto = 0;
		int totalViajes = 0;
		
		this.pedidosData.clear();
		Map<Integer, Pedido> pedidos = Sistema.GetInstance().GetPedidos();
		if (pedidos != null) { //Si hay pedidos
			for (Map.Entry<Integer, Pedido> entry : pedidos.entrySet()) {
				Pedido pedido = entry.getValue();
				//Mostrar solo si es del periodo correspondiente y de la sucursal
				if ( ( pedido.GetFecha().isAfter(fechaDesde.getValue()) || pedido.GetFecha().isEqual(fechaDesde.getValue()) ) && ( pedido.GetFecha().isBefore(fechaHasta.getValue()) || pedido.GetFecha().isEqual(fechaHasta.getValue()) ) ) {
					if (!this.sucursal.getSelectionModel().getSelectedItem().toString().equals("Todas")) {
						if (this.sucursal.getSelectionModel().getSelectedItem().toString().equals(pedido.GetOrigen().GetDireccion())) {
							this.pedidosData.add(new ReportePedidoTableModel(Integer.toString(pedido.GetId()), pedido.GetFecha().toString(), pedido.GetOrigen().GetDireccion(), pedido.GetDestino().GetDireccion(), "$" + Integer.toString(pedido.CalcularMonto())));
							totalPedidos++;
							totalMonto += pedido.CalcularMonto();
						}		
					} else {
						this.pedidosData.add(new ReportePedidoTableModel(Integer.toString(pedido.GetId()), pedido.GetFecha().toString(), pedido.GetOrigen().GetDireccion(), pedido.GetDestino().GetDireccion(), "$" + Integer.toString(pedido.CalcularMonto())));
						totalPedidos++;
						totalMonto += pedido.CalcularMonto();
					}
				}
			}
		}
		this.tabla_pedidos.setItems(this.pedidosData);
		
		//calcular viajes
		Map<Integer, Viaje> viajes = Sistema.GetInstance().GetViajes();
		if (viajes != null) { //Si hay viajes
			for (Map.Entry<Integer, Viaje> entry : viajes.entrySet()) {
				Viaje viaje = entry.getValue();
				//Mostrar solo si es del periodo correspondiente
				if ( ( viaje.GetFechaSalida().isAfter(fechaDesde.getValue()) || viaje.GetFechaSalida().isEqual(fechaDesde.getValue()) ) && ( viaje.GetFechaSalida().isBefore(fechaHasta.getValue()) || viaje.GetFechaSalida().isEqual(fechaHasta.getValue()) ) ) {
					if (!this.sucursal.getSelectionModel().getSelectedItem().toString().equals("Todas")) {
						if (this.sucursal.getSelectionModel().getSelectedItem().toString().equals(viaje.GetOrigen().GetDireccion())) {
							totalViajes++;
						}
					} else {
						totalViajes++;
					}
				}
			}
		}
		
		//setear total
		this.cantidadPedidos.setText(Integer.toString(totalPedidos));
		this.totalIngresos.setText("$" + Integer.toString(totalMonto));
		this.totalViajes.setText(Integer.toString(totalViajes));
		
		if (mostrarMensaje)
			ViewHelper.ShowMessage("Se ha cargado la informacion del reporte", AlertType.INFORMATION);
	}
	
	@FXML
    private void handleVolver() {
		this.mainApp.MostrarMenu();
	}

}
