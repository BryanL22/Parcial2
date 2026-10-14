package co.edu.poli.sw2.aplicacion.puerto.salida;

/**
 * Puerto de salida: eliminar un seguro almacenado.
 *
 * @author BryanL22
 */
public interface EliminarSeguroPort {

    /**
     * Elimina el seguro con el número indicado.
     *
     * @param numero número de la póliza
     * @return {@code true} si se eliminó; {@code false} si no existía
     * @throws PersistenciaException si falla el almacenamiento
     */
    boolean eliminar(String numero);
}
