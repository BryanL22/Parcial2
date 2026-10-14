package co.edu.poli.sw2.dominio.modelo;

import java.time.LocalDate;

/**
 * Seguro de vehículo: además de los datos comunes de {@link Seguro} registra la
 * marca del vehículo asegurado.
 *
 * @author BryanL22
 */
public class SeguroVehiculo extends Seguro {

    /** Valor que retorna {@link #getTipo()} para este subtipo. */
    public static final String TIPO = "VEHICULO";

    /** Marca del vehículo asegurado. */
    private String marca;

    /**
     * Crea un seguro de vehículo.
     *
     * @param numero          número único de la póliza
     * @param fechaExpedicion fecha de expedición
     * @param valor           valor asegurado
     * @param estado          estado de la póliza
     * @param asegurado       titular del seguro
     * @param marca           marca del vehículo
     */
    public SeguroVehiculo(String numero, LocalDate fechaExpedicion, double valor, String estado,
                          Asegurado asegurado, String marca) {
        super(numero, fechaExpedicion, valor, estado, asegurado);
        this.marca = marca;
    }

    /**
     * Aplica las reglas comunes y exige la marca.
     *
     * @throws IllegalArgumentException si alguna regla no se cumple
     */
    @Override
    public void validar() {
        super.validar();
        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException("La marca del vehículo es obligatoria.");
        }
    }

    /** @return {@value #TIPO} */
    @Override
    public String getTipo() {
        return TIPO;
    }

    /** @return marca del vehículo */
    public String getMarca() {
        return marca;
    }

    /** @param marca nueva marca del vehículo */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /** @return resumen del seguro con la marca */
    @Override
    public String toString() {
        return super.toString() + " | marca: " + marca;
    }
}
