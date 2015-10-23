package chilexplox;
import java.io.IOException;

import backend.Sistema;
import chilexplox.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainApp extends Application {
	
	private Stage primaryStage;
    private BorderPane ventanaPrincipal;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Chilexplox");

        // Iniciar la ventana principal que contendra todo
        IniciarVentanaPrincipal();

        // Iniciar la vista del login
        mostrarLogin();
	}
	
	/**
     * Inicializar ventana principal
     */
    public void IniciarVentanaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            ventanaPrincipal = (BorderPane) loader.load();

            // Cargar escena de la venta principal
            Scene scene = new Scene(ventanaPrincipal);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Mostrar el login
     */
    public void mostrarLogin() {
        try {
            // Load login
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
            AnchorPane login = (AnchorPane) loader.load();

            // Poner la vista del login en el centro de la ventana principal
            ventanaPrincipal.setCenter(login);
            
            // Dar acceso al controlador de login
            LoginController controllerView = loader.getController();
            controllerView.setMainApp(this);

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //se llama cuando se hizo login correcto
    public void IngresoCorrecto() {
    	this.MostrarMenu();
    }

    public void MostrarMenu() {
    	try {
            // Load menu
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Menu.fxml"));
            AnchorPane menu = (AnchorPane) loader.load();

            // Poner la vista del menu en el centro de la ventana principal
            ventanaPrincipal.setCenter(menu);
            
            // Dar acceso al controlador de menu
            MenuController controllerView = loader.getController();
            controllerView.setMainApp(this);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Mostrar el Carga y descarga de medios de trnasporte
     */
    public void mostrarCargayDescarga() {
        try {
            // Load login
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CargayDescarga.fxml"));
            AnchorPane cargaydescarga = (AnchorPane) loader.load();

            // Poner la vista en el centro de la ventana principal
            ventanaPrincipal.setCenter(cargaydescarga);
            
            // Dar acceso al controlador
            CargayDescargaController controllerView = loader.getController();
            controllerView.setMainApp(this);

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Mostrar el arrivo y salida de medios de trnasporte
     */
    public void mostrarArrivoSalida() {
        try {
            // Load login
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ArrivoySalida.fxml"));
            AnchorPane arrivosalida = (AnchorPane) loader.load();

            // Poner la vista en el centro de la ventana principal
            ventanaPrincipal.setCenter(arrivosalida);
            
            // Dar acceso al controlador
            ArrivoySalidaController controllerView = loader.getController();
            controllerView.setMainApp(this);

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void MostrarPedidos() {
    	try {
            // Load menu
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ListadoPedidos.fxml"));
            AnchorPane pedidos = (AnchorPane) loader.load();

            // Poner la vista en el centro de la ventana principal
            ventanaPrincipal.setCenter(pedidos);
            
            // Dar acceso al controlador
            ArrivoySalidaController controllerView = loader.getController();
            controllerView.setMainApp(this);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void mostrarMensajes() {
    	try {
            // Load menu
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Mensaje.fxml"));
            AnchorPane mensaje = (AnchorPane) loader.load();

            // Poner la vista en el centro de la ventana principal
            ventanaPrincipal.setCenter(mensaje);
            
            // Dar acceso al controlador
            MensajeController controllerView = loader.getController();
            controllerView.setMainApp(this);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }		
	}

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        Sistema.GetInstance().GuardarTodo();
    }
    
	public static void main(String[] args) {
		launch(args);
	}
}
