package co.edu.poli.sw2.aplicacion.puerto.salida;

/**
 * Error técnico al acceder al almacenamiento.
 * <p>
 * Los adaptadores de salida envuelven sus excepciones propias (por ejemplo
 * {@link java.sql.SQLException}) en esta excepción, de modo que la aplicación y
 * la interfaz no dependan de detalles de JDBC. Es <i>unchecked</i> para no
 * contaminar las firmas de los puertos.
 *
 * @author BryanL22
 */
public class PersistenciaException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje legible y la causa original.
     *
     * @param msg   descripción del error, apta para mostrar al usuario
     * @param causa excepción técnica que lo provocó
     */
    public PersistenciaException(String msg, Throwable causa) {
        super(msg, causa);
    }
}
