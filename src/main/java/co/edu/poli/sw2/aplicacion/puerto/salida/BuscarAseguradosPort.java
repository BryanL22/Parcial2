package co.edu.poli.sw2.aplicacion.puerto.salida;

import co.edu.poli.sw2.dominio.modelo.Asegurado;

import java.util.List;

/**
 * Puerto de salida: obtener los asegurados predeterminados.
 *
 * @author BryanL22
 */
public interface BuscarAseguradosPort {

    /**
     * Obtiene todos los asegurados almacenados.
     *
     * @return lista de asegurados; vacía si no hay ninguno
     * @throws PersistenciaException si falla el almacenamiento
     */
    List<Asegurado> buscarTodos();
}
