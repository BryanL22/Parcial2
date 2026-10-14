package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.EliminarSeguroUseCase;
import co.edu.poli.sw2.aplicacion.puerto.salida.EliminarSeguroPort;

/**
 * Implementación del caso de uso {@link EliminarSeguroUseCase}.
 *
 * @author BryanL22
 */
public class EliminarSeguroServicio implements EliminarSeguroUseCase {

    private final EliminarSeguroPort eliminarSeguroPort;

    /** @param eliminarSeguroPort puerto para eliminar el seguro almacenado */
    public EliminarSeguroServicio(EliminarSeguroPort eliminarSeguroPort) {
        this.eliminarSeguroPort = eliminarSeguroPort;
    }

    /** {@inheritDoc} */
    @Override
    public boolean eliminar(String numero) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("Ingrese el número del seguro a eliminar.");
        }
        return eliminarSeguroPort.eliminar(numero.trim());
    }
}
