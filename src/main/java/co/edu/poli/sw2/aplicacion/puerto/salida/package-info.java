/**
 * Puertos de salida: lo que la aplicación necesita del exterior.
 * <p>
 * Son interfaces definidas por la aplicación e implementadas por los
 * adaptadores de {@code infraestructura.persistencia}. Así los servicios no
 * conocen MySQL ni JDBC. Incluye {@link
 * co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException}, el error
 * técnico común de todos los adaptadores de salida.
 */
package co.edu.poli.sw2.aplicacion.puerto.salida;
