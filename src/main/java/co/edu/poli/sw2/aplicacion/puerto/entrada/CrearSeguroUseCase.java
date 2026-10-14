package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.dominio.modelo.Seguro;

/**
 * Puerto de entrada: caso de uso para registrar un seguro nuevo.
 * <p>
 * Lo invoca el adaptador de entrada (la interfaz JavaFX) y lo implementa
 * {@link co.edu.poli.sw2.aplicacion.servicio.CrearSeguroServicio}.
 *
 * @author BryanL22
 */
public interface CrearSeguroUseCase {

    /**
     * Valida y registra un seguro nuevo.
     *
     * @param seguro seguro a registrar (de vida o de vehículo)
     * @return {@code true} si el seguro quedó registrado
     * @throws IllegalArgumentException si el seguro no cumple las reglas de
     *                                  negocio o ya existe uno con ese número
     * @throws co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException si
     *                                  falla el almacenamiento
     */
    boolean crear(Seguro seguro);
}
