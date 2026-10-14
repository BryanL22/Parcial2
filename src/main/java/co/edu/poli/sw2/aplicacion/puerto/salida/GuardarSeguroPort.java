package co.edu.poli.sw2.aplicacion.puerto.salida;

import co.edu.poli.sw2.dominio.modelo.Seguro;

/**
 * Puerto de salida: almacenar un seguro nuevo.
 * <p>
 * Lo define la aplicación y lo implementa un adaptador de persistencia
 * (p. ej. {@code MySqlSeguroRepository}).
 *
 * @author BryanL22
 */
public interface GuardarSeguroPort {

    /**
     * Almacena un seguro nuevo.
     *
     * @param seguro seguro ya validado
     * @return {@code true} si se almacenó
     * @throws PersistenciaException si falla el almacenamiento
     */
    boolean guardar(Seguro seguro);
}
