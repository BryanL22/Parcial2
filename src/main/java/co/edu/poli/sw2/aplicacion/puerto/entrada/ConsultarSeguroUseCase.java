package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.dominio.modelo.Seguro;

/**
 * Puerto de entrada: caso de uso para consultar un seguro por su número.
 * <p>
 * Lo implementa {@link co.edu.poli.sw2.aplicacion.servicio.ConsultarSeguroServicio}.
 *
 * @author BryanL22
 */
public interface ConsultarSeguroUseCase {

    /**
     * Busca un seguro por su número de póliza.
     *
     * @param numero número de la póliza
     * @return el seguro encontrado, o {@code null} si no existe
     * @throws IllegalArgumentException si el número está vacío
     * @throws co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException si
     *                                  falla el almacenamiento
     */
    Seguro consultar(String numero);
}
