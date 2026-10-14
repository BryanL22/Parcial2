package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.dominio.modelo.Seguro;

/**
 * Puerto de entrada: caso de uso para modificar un seguro existente.
 * <p>
 * Lo implementa {@link co.edu.poli.sw2.aplicacion.servicio.ActualizarSeguroServicio}.
 *
 * @author BryanL22
 */
public interface ActualizarSeguroUseCase {

    /**
     * Valida y actualiza los datos de un seguro, identificado por su número.
     *
     * @param seguro seguro con los datos nuevos
     * @return {@code true} si se actualizó; {@code false} si no existe un seguro
     *         con ese número
     * @throws IllegalArgumentException si el seguro no cumple las reglas de negocio
     * @throws co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException si
     *                                  falla el almacenamiento
     */
    boolean actualizar(Seguro seguro);
}
