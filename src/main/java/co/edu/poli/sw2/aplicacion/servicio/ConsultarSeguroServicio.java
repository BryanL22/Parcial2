package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.ConsultarSeguroUseCase;
import co.edu.poli.sw2.aplicacion.puerto.salida.BuscarSeguroPort;
import co.edu.poli.sw2.dominio.modelo.Seguro;

/**
 * Implementación del caso de uso {@link ConsultarSeguroUseCase}.
 *
 * @author BryanL22
 */
public class ConsultarSeguroServicio implements ConsultarSeguroUseCase {

    private final BuscarSeguroPort buscarSeguroPort;

    /** @param buscarSeguroPort puerto para buscar el seguro por número */
    public ConsultarSeguroServicio(BuscarSeguroPort buscarSeguroPort) {
        this.buscarSeguroPort = buscarSeguroPort;
    }

    /** {@inheritDoc} */
    @Override
    public Seguro consultar(String numero) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("Ingrese el número del seguro a consultar.");
        }
        return buscarSeguroPort.buscarPorNumero(numero.trim());
    }
}
