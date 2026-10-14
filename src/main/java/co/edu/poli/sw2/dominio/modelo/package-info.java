/**
 * Modelo de dominio: el núcleo de la arquitectura hexagonal.
 * <p>
 * Solo depende de {@code java.*}; no conoce la interfaz gráfica ni la base de
 * datos. Contiene las entidades {@link co.edu.poli.sw2.dominio.modelo.Seguro}
 * (abstracta), {@link co.edu.poli.sw2.dominio.modelo.SeguroVida},
 * {@link co.edu.poli.sw2.dominio.modelo.SeguroVehiculo} y
 * {@link co.edu.poli.sw2.dominio.modelo.Asegurado}.
 */
package co.edu.poli.sw2.dominio.modelo;
