package co.edu.poli.sw2.aplicacion.puerto.salida;

import co.edu.poli.sw2.dominio.modelo.Seguro;

/**
 * Puerto de salida: buscar un seguro almacenado por su número.
 *
 * @author BryanL22
 */
public interface BuscarSeguroPort {

    /**
     * Busca un seguro por su número de póliza.
     *
     * @param numero número de la póliza
     * @return el seguro (instancia de {@code SeguroVida} o {@code SeguroVehiculo}),
     *         o {@code null} si no existe
     * @throws PersistenciaException si falla el almacenamiento
     */
    Seguro buscarPorNumero(String numero);
}
