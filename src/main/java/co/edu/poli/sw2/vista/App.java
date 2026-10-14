package co.edu.poli.sw2.vista;

import co.edu.poli.sw2.aplicacion.servicio.ActualizarSeguroServicio;
import co.edu.poli.sw2.aplicacion.servicio.ConsultarAseguradosServicio;
import co.edu.poli.sw2.aplicacion.servicio.ConsultarSeguroServicio;
import co.edu.poli.sw2.aplicacion.servicio.ConsultarSegurosServicio;
import co.edu.poli.sw2.aplicacion.servicio.CrearSeguroServicio;
import co.edu.poli.sw2.aplicacion.servicio.EliminarSeguroServicio;
import co.edu.poli.sw2.infraestructura.persistencia.ConexionBD;
import co.edu.poli.sw2.infraestructura.persistencia.MySqlAseguradoRepository;
import co.edu.poli.sw2.infraestructura.persistencia.MySqlSeguroRepository;
import co.edu.poli.sw2.infraestructura.ui.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punto de entrada y raíz de composición de la aplicación.
 * <p>
 * Es el único lugar que conoce todas las capas: crea los adaptadores de salida
 * (repositorios), los inyecta en los servicios y entrega los servicios —vistos
 * como puertos de entrada— al controlador de la interfaz.
 *
 * @author BryanL22
 */
public class App extends Application {

    /**
     * Arma el grafo de objetos y muestra la ventana principal.
     *
     * @param stage ventana principal provista por JavaFX
     * @throws IOException si no se puede cargar el archivo FXML
     */
    @Override
    public void start(Stage stage) throws IOException {
        MySqlSeguroRepository seguroRepository = new MySqlSeguroRepository();
        MySqlAseguradoRepository aseguradoRepository = new MySqlAseguradoRepository();

        MainController controller = new MainController(
                new CrearSeguroServicio(seguroRepository, seguroRepository),
                new ConsultarSeguroServicio(seguroRepository),
                new ConsultarSegurosServicio(seguroRepository),
                new ActualizarSeguroServicio(seguroRepository),
                new EliminarSeguroServicio(seguroRepository),
                new ConsultarAseguradosServicio(aseguradoRepository));

        FXMLLoader loader = new FXMLLoader(
                MainController.class.getResource("/co/edu/poli/sw2/infraestructura/ui/GestorSeguros.fxml"));
        loader.setController(controller);

        stage.setTitle("Gestión de Seguros");
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    /** Libera la conexión a la base de datos al cerrar la ventana. */
    @Override
    public void stop() {
        ConexionBD.getInstancia().cerrar();
    }

    /**
     * Inicia la aplicación JavaFX.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
