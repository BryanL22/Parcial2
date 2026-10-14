package co.edu.poli.sw2.aplicacion.puerto.entrada;

/**
 * Puerto de entrada: caso de uso para eliminar un seguro.
 * <p>
 * Lo implementa {@link co.edu.poli.sw2.aplicacion.servicio.EliminarSeguroServicio}.
 *
 * @author BryanL22
 */
public interface EliminarSeguroUseCase {

    /**
     * Elimina el seguro con el número indicado.
     *
     * @param numero número de la póliza
     * @return {@code true} si se eliminó; {@code false} si no existía
     * @throws IllegalArgumentException si el número está vacío
     * @throws co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException si
     *                                  falla el almacenamiento
     */
    boolean eliminar(String numero);
}
