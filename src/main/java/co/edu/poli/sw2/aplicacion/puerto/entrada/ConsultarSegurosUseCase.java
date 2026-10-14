package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.dominio.modelo.Seguro;

import java.util.List;

/**
 * Puerto de entrada: caso de uso para listar todos los seguros registrados.
 * <p>
 * Lo implementa {@link co.edu.poli.sw2.aplicacion.servicio.ConsultarSegurosServicio}.
 *
 * @author BryanL22
 */
public interface ConsultarSegurosUseCase {

    /**
     * Obtiene todos los seguros registrados.
     *
     * @return lista de seguros; vacía si no hay ninguno
     * @throws co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException si
     *                                  falla el almacenamiento
     */
    List<Seguro> consultarTodos();
}
