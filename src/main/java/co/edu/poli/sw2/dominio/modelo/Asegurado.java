package co.edu.poli.sw2.dominio.modelo;

/**
 * Persona titular de uno o varios seguros.
 * <p>
 * Los asegurados son datos predeterminados de la aplicación: se cargan en la
 * base de datos mediante el script SQL y solo se consultan (no tienen CRUD).
 * Un asegurado puede tener cero o muchos {@link Seguro}s asociados.
 *
 * @author BryanL22
 * @see Seguro
 */
public class Asegurado {

    /** Documento de identificación; identifica de forma única al asegurado. */
    private String identificacion;

    /** Nombre completo del asegurado. */
    private String nombre;

    /** Correo electrónico de contacto. */
    private String correoElectronico;

    /**
     * Crea un asegurado con todos sus datos.
     *
     * @param identificacion    documento de identificación único
     * @param nombre            nombre completo
     * @param correoElectronico correo electrónico de contacto
     */
    public Asegurado(String identificacion, String nombre, String correoElectronico) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
    }

    /** @return documento de identificación del asegurado */
    public String getIdentificacion() {
        return identificacion;
    }

    /** @param identificacion nuevo documento de identificación */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /** @return nombre completo del asegurado */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre nuevo nombre completo */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return correo electrónico del asegurado */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /** @param correoElectronico nuevo correo electrónico */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Representación pensada para mostrarse en la interfaz (p. ej. en un ComboBox).
     *
     * @return identificación y nombre del asegurado
     */
    @Override
    public String toString() {
        return identificacion + " - " + nombre;
    }
}
