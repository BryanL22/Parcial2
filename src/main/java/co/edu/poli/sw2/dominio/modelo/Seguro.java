package co.edu.poli.sw2.dominio.modelo;

import java.time.LocalDate;

/**
 * Póliza de seguro: entidad raíz del dominio y única sobre la que se hace CRUD.
 * <p>
 * Es abstracta porque todo seguro es de un tipo concreto
 * ({@link SeguroVida} o {@link SeguroVehiculo}). Contiene los datos comunes y las
 * reglas de validación generales; cada subtipo agrega sus propios atributos y
 * reglas sobrescribiendo {@link #validar()}.
 *
 * @author BryanL22
 * @see SeguroVida
 * @see SeguroVehiculo
 * @see Asegurado
 */
public abstract class Seguro {

    /** Número de la póliza; identifica de forma única al seguro. */
    private String numero;

    /** Fecha en que se expidió la póliza. No puede ser futura. */
    private LocalDate fechaExpedicion;

    /** Valor asegurado. Debe ser mayor que cero. */
    private double valor;

    /** Estado de la póliza (por ejemplo ACTIVO, INACTIVO o VENCIDO). */
    private String estado;

    /** Titular del seguro (asociación 0..* seguros → 1 asegurado). */
    private Asegurado asegurado;

    /**
     * Constructor común para los subtipos.
     *
     * @param numero          número único de la póliza
     * @param fechaExpedicion fecha de expedición
     * @param valor           valor asegurado
     * @param estado          estado de la póliza
     * @param asegurado       titular del seguro
     */
    protected Seguro(String numero, LocalDate fechaExpedicion, double valor,
                     String estado, Asegurado asegurado) {
        this.numero = numero;
        this.fechaExpedicion = fechaExpedicion;
        this.valor = valor;
        this.estado = estado;
        this.asegurado = asegurado;
    }

    /**
     * Verifica las reglas de negocio comunes a todo seguro.
     * <p>
     * Las subclases deben invocar {@code super.validar()} antes de validar sus
     * propios atributos.
     *
     * @throws IllegalArgumentException si el número o el estado están vacíos, la
     *                                  fecha es nula o futura, el valor no es
     *                                  positivo o no hay asegurado
     */
    public void validar() {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("El número del seguro es obligatorio.");
        }
        if (fechaExpedicion == null) {
            throw new IllegalArgumentException("La fecha de expedición es obligatoria.");
        }
        if (fechaExpedicion.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de expedición no puede ser futura.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor del seguro debe ser mayor que cero.");
        }
        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("El estado del seguro es obligatorio.");
        }
        if (asegurado == null) {
            throw new IllegalArgumentException("El seguro debe tener un asegurado.");
        }
    }

    /**
     * Indica el tipo concreto del seguro. Se usa para mostrarlo en la interfaz y
     * para persistirlo.
     *
     * @return {@code "VIDA"} o {@code "VEHICULO"} según la subclase
     */
    public abstract String getTipo();

    /** @return número único de la póliza */
    public String getNumero() {
        return numero;
    }

    /** @param numero nuevo número de la póliza */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /** @return fecha de expedición */
    public LocalDate getFechaExpedicion() {
        return fechaExpedicion;
    }

    /** @param fechaExpedicion nueva fecha de expedición */
    public void setFechaExpedicion(LocalDate fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    /** @return valor asegurado */
    public double getValor() {
        return valor;
    }

    /** @param valor nuevo valor asegurado */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /** @return estado de la póliza */
    public String getEstado() {
        return estado;
    }

    /** @param estado nuevo estado de la póliza */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /** @return titular del seguro */
    public Asegurado getAsegurado() {
        return asegurado;
    }

    /** @param asegurado nuevo titular del seguro */
    public void setAsegurado(Asegurado asegurado) {
        this.asegurado = asegurado;
    }

    /** @return resumen con el tipo, número, valor y estado del seguro */
    @Override
    public String toString() {
        return getTipo() + " #" + numero + " | valor: " + valor + " | estado: " + estado;
    }
}
