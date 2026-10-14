/**
 * Adaptadores de salida: implementan los puertos de
 * {@code aplicacion.puerto.salida} usando MySQL y JDBC.
 * <p>
 * Es el único paquete que conoce SQL. Cualquier {@link java.sql.SQLException}
 * se traduce a {@link co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException}.
 */
package co.edu.poli.sw2.infraestructura.persistencia;
