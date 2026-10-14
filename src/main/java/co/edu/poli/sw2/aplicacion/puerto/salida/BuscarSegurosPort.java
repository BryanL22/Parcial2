package co.edu.poli.sw2.aplicacion.puerto.salida;

import co.edu.poli.sw2.dominio.modelo.Seguro;

import java.util.List;

/**
 * Puerto de salida: obtener todos los seguros almacenados.
 *
 * @author BryanL22
 */
public interface BuscarSegurosPort {

    /**
     * Obtiene todos los seguros almacenados.
     *
     * @return lista de seguros; vacía si no hay ninguno
     * @throws PersistenciaException si falla el almacenamiento
     */
    List<Seguro> buscarTodos();
}
