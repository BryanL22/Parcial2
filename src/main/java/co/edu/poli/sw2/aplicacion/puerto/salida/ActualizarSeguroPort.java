package co.edu.poli.sw2.aplicacion.puerto.salida;

import co.edu.poli.sw2.dominio.modelo.Seguro;

/**
 * Puerto de salida: actualizar un seguro almacenado.
 *
 * @author BryanL22
 */
public interface ActualizarSeguroPort {

    /**
     * Reemplaza los datos del seguro que tiene el mismo número.
     *
     * @param seguro seguro ya validado con los datos nuevos
     * @return {@code true} si se actualizó; {@code false} si no existe
     * @throws PersistenciaException si falla el almacenamiento
     */
    boolean actualizar(Seguro seguro);
}
