package co.edu.poli.sw2.infraestructura.persistencia;

import co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Proveedor de la conexión JDBC a MySQL (patrón Singleton).
 * <p>
 * Lee la URL, el usuario y la contraseña del archivo {@code db.properties} del
 * classpath y mantiene una única conexión compartida por los repositorios. Si la
 * conexión se cierra o se pierde, se vuelve a abrir en la siguiente llamada a
 * {@link #getConexion()}.
 *
 * @author BryanL22
 */
public final class ConexionBD {

    /** Archivo de configuración en {@code src/main/resources}. */
    private static final String ARCHIVO_CONFIG = "/db.properties";

    /** Única instancia de la clase. */
    private static ConexionBD instancia;

    private final String url;
    private final String usuario;
    private final String contrasena;
    private Connection conexion;

    /**
     * Carga la configuración. Es privado para impedir instancias externas.
     *
     * @throws PersistenciaException si no se encuentra o no se puede leer
     *                               {@code db.properties}
     */
    private ConexionBD() {
        Properties props = new Properties();
        try (InputStream in = ConexionBD.class.getResourceAsStream(ARCHIVO_CONFIG)) {
            if (in == null) {
                throw new IOException("No se encontró " + ARCHIVO_CONFIG + " en el classpath");
            }
            props.load(in);
        } catch (IOException e) {
            throw new PersistenciaException("No se pudo leer la configuración de la base de datos.", e);
        }
        this.url = props.getProperty("db.url");
        this.usuario = props.getProperty("db.usuario");
        this.contrasena = props.getProperty("db.contrasena");
    }

    /**
     * Devuelve la única instancia, creándola la primera vez.
     *
     * @return instancia compartida
     */
    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Devuelve la conexión abierta, abriéndola si aún no existe o está cerrada.
     * <p>
     * Quien la usa no debe cerrarla; solo debe cerrar sus sentencias y
     * resultados.
     *
     * @return conexión JDBC lista para usar
     * @throws PersistenciaException si no es posible conectarse a MySQL
     */
    public synchronized Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed() || !conexion.isValid(2)) {
                conexion = DriverManager.getConnection(url, usuario, contrasena);
            }
            return conexion;
        } catch (SQLException e) {
            throw new PersistenciaException("No fue posible conectarse a la base de datos. "
                    + "Verifique que MySQL esté en ejecución y los datos de db.properties.", e);
        }
    }

    /** Cierra la conexión si está abierta. Se invoca al cerrar la aplicación. */
    public synchronized void cerrar() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException ignored) {
                // la aplicación ya está terminando; no hay nada más que hacer
            }
            conexion = null;
        }
    }
}
