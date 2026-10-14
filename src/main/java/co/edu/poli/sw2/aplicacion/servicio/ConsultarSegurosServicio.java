package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.ConsultarSegurosUseCase;
import co.edu.poli.sw2.aplicacion.puerto.salida.BuscarSegurosPort;
import co.edu.poli.sw2.dominio.modelo.Seguro;

import java.util.List;

/**
 * Implementación del caso de uso {@link ConsultarSegurosUseCase}.
 *
 * @author BryanL22
 */
public class ConsultarSegurosServicio implements ConsultarSegurosUseCase {

    private final BuscarSegurosPort buscarSegurosPort;

    /** @param buscarSegurosPort puerto para obtener todos los seguros */
    public ConsultarSegurosServicio(BuscarSegurosPort buscarSegurosPort) {
        this.buscarSegurosPort = buscarSegurosPort;
    }

    /** {@inheritDoc} */
    @Override
    public List<Seguro> consultarTodos() {
        return buscarSegurosPort.buscarTodos();
    }
}
