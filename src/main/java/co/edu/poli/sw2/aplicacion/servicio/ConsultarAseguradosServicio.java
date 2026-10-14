package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.ConsultarAseguradosUseCase;
import co.edu.poli.sw2.aplicacion.puerto.salida.BuscarAseguradosPort;
import co.edu.poli.sw2.dominio.modelo.Asegurado;

import java.util.List;

/**
 * Implementación del caso de uso {@link ConsultarAseguradosUseCase}.
 *
 * @author BryanL22
 */
public class ConsultarAseguradosServicio implements ConsultarAseguradosUseCase {

    private final BuscarAseguradosPort buscarAseguradosPort;

    /** @param buscarAseguradosPort puerto para obtener los asegurados */
    public ConsultarAseguradosServicio(BuscarAseguradosPort buscarAseguradosPort) {
        this.buscarAseguradosPort = buscarAseguradosPort;
    }

    /** {@inheritDoc} */
    @Override
    public List<Asegurado> consultarTodos() {
        return buscarAseguradosPort.buscarTodos();
    }
}
