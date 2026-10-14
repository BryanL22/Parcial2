package co.edu.poli.sw2.infraestructura.persistencia;

import co.edu.poli.sw2.aplicacion.puerto.salida.BuscarAseguradosPort;
import co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException;
import co.edu.poli.sw2.dominio.modelo.Asegurado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador de salida que lee los asegurados de la tabla {@code asegurado} en
 * MySQL. Implementa el puerto {@link BuscarAseguradosPort}.
 *
 * @author BryanL22
 */
public class MySqlAseguradoRepository implements BuscarAseguradosPort {

    private static final String SQL_TODOS =
            "SELECT identificacion, nombre, correo_electronico FROM asegurado ORDER BY nombre";

    /**
     * {@inheritDoc}
     *
     * @throws PersistenciaException si falla la consulta SQL
     */
    @Override
    public List<Asegurado> buscarTodos() {
        List<Asegurado> asegurados = new ArrayList<>();
        try (PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(SQL_TODOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                asegurados.add(new Asegurado(
                        rs.getString("identificacion"),
                        rs.getString("nombre"),
                        rs.getString("correo_electronico")));
            }
            return asegurados;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar los asegurados.", e);
        }
    }
}
