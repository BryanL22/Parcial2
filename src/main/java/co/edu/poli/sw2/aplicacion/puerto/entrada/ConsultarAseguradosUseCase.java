package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.dominio.modelo.Asegurado;

import java.util.List;

/**
 * Puerto de entrada: caso de uso para listar los asegurados predeterminados.
 * <p>
 * Es de solo lectura; la interfaz lo usa para que el usuario elija el titular
 * de un seguro. Lo implementa
 * {@link co.edu.poli.sw2.aplicacion.servicio.ConsultarAseguradosServicio}.
 *
 * @author BryanL22
 */
public interface ConsultarAseguradosUseCase {

    /**
     * Obtiene todos los asegurados disponibles.
     *
     * @return lista de asegurados; vacía si no hay ninguno
     * @throws co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException si
     *                                  falla el almacenamiento
     */
    List<Asegurado> consultarTodos();
}
