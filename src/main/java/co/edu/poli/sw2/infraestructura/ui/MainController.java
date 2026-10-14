package co.edu.poli.sw2.infraestructura.ui;

import co.edu.poli.sw2.aplicacion.puerto.entrada.ActualizarSeguroUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.ConsultarAseguradosUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.ConsultarSeguroUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.ConsultarSegurosUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.CrearSeguroUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.EliminarSeguroUseCase;
import co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException;
import co.edu.poli.sw2.dominio.modelo.Asegurado;
import co.edu.poli.sw2.dominio.modelo.Seguro;
import co.edu.poli.sw2.dominio.modelo.SeguroVehiculo;
import co.edu.poli.sw2.dominio.modelo.SeguroVida;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 * Adaptador de entrada: controlador de la vista JavaFX {@code GestorSeguros.fxml}.
 * <p>
 * Traduce las acciones del usuario en llamadas a los puertos de entrada (casos
 * de uso) y muestra los resultados. No contiene lógica de negocio: las
 * validaciones las hace el dominio y los errores llegan como
 * {@link IllegalArgumentException} (datos inválidos) o
 * {@link PersistenciaException} (fallo técnico), que se muestran en un diálogo.
 * <p>
 * Solo depende de las interfaces de {@code aplicacion.puerto.entrada}; las
 * implementaciones las inyecta {@code vista.App} por constructor.
 *
 * @author BryanL22
 */
public class MainController {

    private static final List<String> ESTADOS = List.of("ACTIVO", "INACTIVO", "VENCIDO");

    private final CrearSeguroUseCase crearSeguro;
    private final ConsultarSeguroUseCase consultarSeguro;
    private final ConsultarSegurosUseCase consultarSeguros;
    private final ActualizarSeguroUseCase actualizarSeguro;
    private final EliminarSeguroUseCase eliminarSeguro;
    private final ConsultarAseguradosUseCase consultarAsegurados;

    @FXML private TextField txtNumero;
    @FXML private ComboBox<String> cbTipo;
    @FXML private DatePicker dpFecha;
    @FXML private TextField txtValor;
    @FXML private ComboBox<String> cbEstado;
    @FXML private ComboBox<Asegurado> cbAsegurado;
    @FXML private TextField txtBeneficiario;
    @FXML private TextField txtParentesco;
    @FXML private TextField txtMarca;

    @FXML private TableView<Seguro> tabla;
    @FXML private TableColumn<Seguro, String> colNumero;
    @FXML private TableColumn<Seguro, String> colTipo;
    @FXML private TableColumn<Seguro, String> colFecha;
    @FXML private TableColumn<Seguro, String> colValor;
    @FXML private TableColumn<Seguro, String> colEstado;
    @FXML private TableColumn<Seguro, String> colAsegurado;
    @FXML private TableColumn<Seguro, String> colDetalle;

    /**
     * Recibe los casos de uso que la vista necesita.
     *
     * @param crearSeguro         caso de uso para crear seguros
     * @param consultarSeguro     caso de uso para consultar un seguro por número
     * @param consultarSeguros    caso de uso para listar los seguros
     * @param actualizarSeguro    caso de uso para actualizar seguros
     * @param eliminarSeguro      caso de uso para eliminar seguros
     * @param consultarAsegurados caso de uso para listar los asegurados
     */
    public MainController(CrearSeguroUseCase crearSeguro, ConsultarSeguroUseCase consultarSeguro,
                          ConsultarSegurosUseCase consultarSeguros, ActualizarSeguroUseCase actualizarSeguro,
                          EliminarSeguroUseCase eliminarSeguro, ConsultarAseguradosUseCase consultarAsegurados) {
        this.crearSeguro = crearSeguro;
        this.consultarSeguro = consultarSeguro;
        this.consultarSeguros = consultarSeguros;
        this.actualizarSeguro = actualizarSeguro;
        this.eliminarSeguro = eliminarSeguro;
        this.consultarAsegurados = consultarAsegurados;
    }

    /**
     * Configura los controles y carga asegurados y seguros. JavaFX lo invoca
     * automáticamente después de inyectar los campos {@code @FXML}.
     */
    @FXML
    private void initialize() {
        cbTipo.setItems(FXCollections.observableArrayList(SeguroVida.TIPO, SeguroVehiculo.TIPO));
        cbTipo.valueProperty().addListener((obs, anterior, tipo) -> habilitarCamposPorTipo(tipo));
        cbEstado.setItems(FXCollections.observableArrayList(ESTADOS));

        NumberFormat moneda = NumberFormat.getCurrencyInstance(Locale.of("es", "CO"));
        columna(colNumero, Seguro::getNumero);
        columna(colTipo, Seguro::getTipo);
        columna(colFecha, s -> s.getFechaExpedicion().toString());
        columna(colValor, s -> moneda.format(s.getValor()));
        columna(colEstado, Seguro::getEstado);
        columna(colAsegurado, s -> s.getAsegurado().getNombre());
        columna(colDetalle, s -> s instanceof SeguroVida v
                ? v.getNombreBeneficiario() + " (" + v.getParentesco() + ")"
                : ((SeguroVehiculo) s).getMarca());

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seguro) -> {
            if (seguro != null) {
                mostrarEnFormulario(seguro);
            }
        });

        onLimpiar();
        ejecutar(() -> {
            cbAsegurado.setItems(FXCollections.observableArrayList(consultarAsegurados.consultarTodos()));
            cargarTabla(consultarSeguros.consultarTodos());
        });
    }

    /** Crea un seguro con los datos del formulario. */
    @FXML
    private void onCrear() {
        ejecutar(() -> {
            if (crearSeguro.crear(leerFormulario())) {
                informar("Seguro creado correctamente.");
                onListar();
            }
        });
    }

    /** Consulta el seguro cuyo número está en el formulario y lo muestra. */
    @FXML
    private void onConsultar() {
        ejecutar(() -> {
            Seguro seguro = consultarSeguro.consultar(txtNumero.getText());
            if (seguro == null) {
                advertir("No existe un seguro con el número " + txtNumero.getText().trim() + ".");
                return;
            }
            cargarTabla(List.of(seguro));
            mostrarEnFormulario(seguro);
        });
    }

    /** Actualiza el seguro identificado por el número del formulario. */
    @FXML
    private void onActualizar() {
        ejecutar(() -> {
            if (actualizarSeguro.actualizar(leerFormulario())) {
                informar("Seguro actualizado correctamente.");
                onListar();
            } else {
                advertir("No existe un seguro con el número " + txtNumero.getText().trim() + ".");
            }
        });
    }

    /** Elimina el seguro del número indicado, previa confirmación del usuario. */
    @FXML
    private void onEliminar() {
        String numero = txtNumero.getText().trim();
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar el seguro " + numero + "?", ButtonType.YES, ButtonType.NO);
        confirmacion.setHeaderText(null);
        if (confirmacion.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
            return;
        }
        ejecutar(() -> {
            if (eliminarSeguro.eliminar(numero)) {
                informar("Seguro eliminado correctamente.");
                onLimpiar();
                onListar();
            } else {
                advertir("No existe un seguro con el número " + numero + ".");
            }
        });
    }

    /** Recarga la tabla con todos los seguros. */
    @FXML
    private void onListar() {
        ejecutar(() -> cargarTabla(consultarSeguros.consultarTodos()));
    }

    /** Deja el formulario vacío. */
    @FXML
    private void onLimpiar() {
        txtNumero.clear();
        cbTipo.setValue(SeguroVida.TIPO);
        dpFecha.setValue(null);
        txtValor.clear();
        cbEstado.setValue(ESTADOS.get(0));
        cbAsegurado.setValue(null);
        txtBeneficiario.clear();
        txtParentesco.clear();
        txtMarca.clear();
        tabla.getSelectionModel().clearSelection();
    }

    /**
     * Construye el seguro del subtipo elegido a partir de los controles.
     *
     * @return seguro sin validar (la validación la hace el caso de uso)
     * @throws IllegalArgumentException si el valor no es numérico o falta el tipo
     */
    private Seguro leerFormulario() {
        double valor;
        try {
            valor = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El valor debe ser un número.");
        }
        String numero = txtNumero.getText().trim();
        if (SeguroVida.TIPO.equals(cbTipo.getValue())) {
            return new SeguroVida(numero, dpFecha.getValue(), valor, cbEstado.getValue(), cbAsegurado.getValue(),
                    txtBeneficiario.getText().trim(), txtParentesco.getText().trim());
        }
        if (SeguroVehiculo.TIPO.equals(cbTipo.getValue())) {
            return new SeguroVehiculo(numero, dpFecha.getValue(), valor, cbEstado.getValue(), cbAsegurado.getValue(),
                    txtMarca.getText().trim());
        }
        throw new IllegalArgumentException("Seleccione el tipo de seguro.");
    }

    /**
     * Copia los datos de un seguro a los controles del formulario.
     *
     * @param seguro seguro a mostrar
     */
    private void mostrarEnFormulario(Seguro seguro) {
        txtNumero.setText(seguro.getNumero());
        cbTipo.setValue(seguro.getTipo());
        dpFecha.setValue(seguro.getFechaExpedicion());
        txtValor.setText(String.valueOf(seguro.getValor()));
        cbEstado.setValue(seguro.getEstado());
        cbAsegurado.setValue(cbAsegurado.getItems().stream()
                .filter(a -> a.getIdentificacion().equals(seguro.getAsegurado().getIdentificacion()))
                .findFirst().orElse(null));
        txtBeneficiario.clear();
        txtParentesco.clear();
        txtMarca.clear();
        if (seguro instanceof SeguroVida v) {
            txtBeneficiario.setText(v.getNombreBeneficiario());
            txtParentesco.setText(v.getParentesco());
        } else if (seguro instanceof SeguroVehiculo v) {
            txtMarca.setText(v.getMarca());
        }
    }

    /**
     * Habilita solo los campos propios del tipo de seguro elegido.
     *
     * @param tipo {@code "VIDA"} o {@code "VEHICULO"}
     */
    private void habilitarCamposPorTipo(String tipo) {
        boolean vida = SeguroVida.TIPO.equals(tipo);
        txtBeneficiario.setDisable(!vida);
        txtParentesco.setDisable(!vida);
        txtMarca.setDisable(vida);
    }

    /** @param seguros seguros a mostrar en la tabla */
    private void cargarTabla(List<Seguro> seguros) {
        tabla.setItems(FXCollections.observableArrayList(seguros));
    }

    /**
     * Asocia una columna de la tabla con el texto que se obtiene de cada seguro.
     *
     * @param columna columna de la tabla
     * @param valor   función que extrae el texto a mostrar
     */
    private void columna(TableColumn<Seguro, String> columna, Function<Seguro, String> valor) {
        columna.setCellValueFactory(celda -> new SimpleStringProperty(valor.apply(celda.getValue())));
    }

    /**
     * Ejecuta una acción de la vista y muestra en un diálogo los errores
     * esperados: validaciones de negocio y fallos de persistencia.
     *
     * @param accion acción a ejecutar
     */
    private void ejecutar(Runnable accion) {
        try {
            accion.run();
        } catch (IllegalArgumentException e) {
            advertir(e.getMessage());
        } catch (PersistenciaException e) {
            mostrar(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    private void informar(String mensaje) {
        mostrar(Alert.AlertType.INFORMATION, mensaje);
    }

    private void advertir(String mensaje) {
        mostrar(Alert.AlertType.WARNING, mensaje);
    }

    private void mostrar(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo, mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
