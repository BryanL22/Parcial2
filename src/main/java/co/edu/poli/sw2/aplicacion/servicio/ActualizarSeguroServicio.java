package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.ActualizarSeguroUseCase;
import co.edu.poli.sw2.aplicacion.puerto.salida.ActualizarSeguroPort;
import co.edu.poli.sw2.dominio.modelo.Seguro;

/**
 * Implementación del caso de uso {@link ActualizarSeguroUseCase}.
 * <p>
 * Valida el seguro con las reglas del dominio antes de enviarlo al puerto de
 * salida.
 *
 * @author BryanL22
 */
public class ActualizarSeguroServicio implements ActualizarSeguroUseCase {

    private final ActualizarSeguroPort actualizarSeguroPort;

    /** @param actualizarSeguroPort puerto para actualizar el seguro almacenado */
    public ActualizarSeguroServicio(ActualizarSeguroPort actualizarSeguroPort) {
        this.actualizarSeguroPort = actualizarSeguroPort;
    }

    /** {@inheritDoc} */
    @Override
    public boolean actualizar(Seguro seguro) {
        if (seguro == null) {
            throw new IllegalArgumentException("El seguro es obligatorio.");
        }
        seguro.validar();
        return actualizarSeguroPort.actualizar(seguro);
    }
}
