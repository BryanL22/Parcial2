package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.CrearSeguroUseCase;
import co.edu.poli.sw2.aplicacion.puerto.salida.BuscarSeguroPort;
import co.edu.poli.sw2.aplicacion.puerto.salida.GuardarSeguroPort;
import co.edu.poli.sw2.dominio.modelo.Seguro;

/**
 * Implementación del caso de uso {@link CrearSeguroUseCase}.
 * <p>
 * Valida el seguro con las reglas del dominio, verifica que el número no esté
 * repetido y lo almacena a través de los puertos de salida.
 *
 * @author BryanL22
 */
public class CrearSeguroServicio implements CrearSeguroUseCase {

    private final GuardarSeguroPort guardarSeguroPort;
    private final BuscarSeguroPort buscarSeguroPort;

    /**
     * @param guardarSeguroPort puerto para almacenar el seguro
     * @param buscarSeguroPort  puerto para verificar que el número no exista
     */
    public CrearSeguroServicio(GuardarSeguroPort guardarSeguroPort, BuscarSeguroPort buscarSeguroPort) {
        this.guardarSeguroPort = guardarSeguroPort;
        this.buscarSeguroPort = buscarSeguroPort;
    }

    /** {@inheritDoc} */
    @Override
    public boolean crear(Seguro seguro) {
        if (seguro == null) {
            throw new IllegalArgumentException("El seguro es obligatorio.");
        }
        seguro.validar();
        if (buscarSeguroPort.buscarPorNumero(seguro.getNumero()) != null) {
            throw new IllegalArgumentException("Ya existe un seguro con el número " + seguro.getNumero() + ".");
        }
        return guardarSeguroPort.guardar(seguro);
    }
}
