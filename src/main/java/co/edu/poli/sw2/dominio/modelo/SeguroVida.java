package co.edu.poli.sw2.dominio.modelo;

import java.time.LocalDate;

/**
 * Seguro de vida: además de los datos comunes de {@link Seguro} registra quién
 * es el beneficiario y su parentesco con el asegurado.
 *
 * @author BryanL22
 */
public class SeguroVida extends Seguro {

    /** Valor que retorna {@link #getTipo()} para este subtipo. */
    public static final String TIPO = "VIDA";

    /** Nombre de la persona que recibe la indemnización. */
    private String nombreBeneficiario;

    /** Parentesco del beneficiario con el asegurado (hijo, cónyuge, etc.). */
    private String parentesco;

    /**
     * Crea un seguro de vida.
     *
     * @param numero             número único de la póliza
     * @param fechaExpedicion    fecha de expedición
     * @param valor              valor asegurado
     * @param estado             estado de la póliza
     * @param asegurado          titular del seguro
     * @param nombreBeneficiario nombre del beneficiario
     * @param parentesco         parentesco del beneficiario con el asegurado
     */
    public SeguroVida(String numero, LocalDate fechaExpedicion, double valor, String estado,
                      Asegurado asegurado, String nombreBeneficiario, String parentesco) {
        super(numero, fechaExpedicion, valor, estado, asegurado);
        this.nombreBeneficiario = nombreBeneficiario;
        this.parentesco = parentesco;
    }

    /**
     * Aplica las reglas comunes y exige beneficiario y parentesco.
     *
     * @throws IllegalArgumentException si alguna regla no se cumple
     */
    @Override
    public void validar() {
        super.validar();
        if (nombreBeneficiario == null || nombreBeneficiario.isBlank()) {
            throw new IllegalArgumentException("El nombre del beneficiario es obligatorio.");
        }
        if (parentesco == null || parentesco.isBlank()) {
            throw new IllegalArgumentException("El parentesco es obligatorio.");
        }
    }

    /** @return {@value #TIPO} */
    @Override
    public String getTipo() {
        return TIPO;
    }

    /** @return nombre del beneficiario */
    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    /** @param nombreBeneficiario nuevo nombre del beneficiario */
    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    /** @return parentesco del beneficiario con el asegurado */
    public String getParentesco() {
        return parentesco;
    }

    /** @param parentesco nuevo parentesco */
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    /** @return resumen del seguro con beneficiario y parentesco */
    @Override
    public String toString() {
        return super.toString() + " | beneficiario: " + nombreBeneficiario + " (" + parentesco + ")";
    }
}
