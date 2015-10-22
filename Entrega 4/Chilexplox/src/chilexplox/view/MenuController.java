package chilexplox.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


//Fachada del backend
import backend.Sistema;
import backend.Sucursal;
import backend.Empleado;
import chilexplox.MainApp;

public class MenuController {
	
	@FXML
	private Label sucursal;
	
	@FXML
	private Label nombre;
	
	private MainApp mainApp;
	
	@FXML
    private void initialize() {
		Sucursal sucursal = Sistema.GetInstance().GetSucursalLoged();		
		Empleado usuario = Sistema.GetInstance().GetUsuarioLoged();
		//this.Actualizar(this.sucursal, sucursal.GetDireccion());
		//this.Actualizar(this.nombre, usuario.GetNombre());
		this.sucursal.setText(sucursal.GetDireccion());
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public MenuController() {
		
	}
	
	public void Actualizar(Label lab, String text) {
	    Thread t = new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	lab.setText(text);
	        }     
	    });
	    t.start();
	}
	
	@FXML
	public void handlePedidos () {
		//this.mainApp.mostrarPedidos();
	}
	
	@FXML
	public void handleClientes () {
		//this.mainApp.mostrarClientes();
	}
	
	@FXML
	public void handleSalir () {
		this.mainApp.mostrarLogin();
	}
}
