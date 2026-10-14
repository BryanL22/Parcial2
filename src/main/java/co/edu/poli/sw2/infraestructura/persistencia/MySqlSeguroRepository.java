package co.edu.poli.sw2.infraestructura.persistencia;

import co.edu.poli.sw2.aplicacion.puerto.salida.ActualizarSeguroPort;
import co.edu.poli.sw2.aplicacion.puerto.salida.BuscarSeguroPort;
import co.edu.poli.sw2.aplicacion.puerto.salida.BuscarSegurosPort;
import co.edu.poli.sw2.aplicacion.puerto.salida.EliminarSeguroPort;
import co.edu.poli.sw2.aplicacion.puerto.salida.GuardarSeguroPort;
import co.edu.poli.sw2.aplicacion.puerto.salida.PersistenciaException;
import co.edu.poli.sw2.dominio.modelo.Asegurado;
import co.edu.poli.sw2.dominio.modelo.Seguro;
import co.edu.poli.sw2.dominio.modelo.SeguroVehiculo;
import co.edu.poli.sw2.dominio.modelo.SeguroVida;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador de salida que persiste los seguros en MySQL mediante JDBC.
 * <p>
 * Implementa los cinco puertos de salida de seguros. La jerarquía
 * {@link Seguro} → {@link SeguroVida} / {@link SeguroVehiculo} se guarda en una
 * sola tabla {@code seguro}: la columna {@code tipo} indica la subclase y las
 * columnas propias de la otra subclase quedan en {@code NULL}.
 *
 * @author BryanL22
 */
public class MySqlSeguroRepository implements GuardarSeguroPort, BuscarSeguroPort, BuscarSegurosPort,
        ActualizarSeguroPort, EliminarSeguroPort {

    private static final String SQL_SELECT =
            "SELECT s.numero, s.fecha_expedicion, s.valor, s.estado, s.tipo, "
            + "s.nombre_beneficiario, s.parentesco, s.marca, "
            + "a.identificacion, a.nombre, a.correo_electronico "
            + "FROM seguro s JOIN asegurado a ON a.identificacion = s.id_asegurado";

    private static final String SQL_INSERT =
            "INSERT INTO seguro (fecha_expedicion, valor, estado, tipo, id_asegurado, "
            + "nombre_beneficiario, parentesco, marca, numero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE seguro SET fecha_expedicion = ?, valor = ?, estado = ?, tipo = ?, id_asegurado = ?, "
            + "nombre_beneficiario = ?, parentesco = ?, marca = ? WHERE numero = ?";

    private static final String SQL_DELETE = "DELETE FROM seguro WHERE numero = ?";

    /**
     * {@inheritDoc}
     *
     * @throws PersistenciaException si falla la sentencia SQL
     */
    @Override
    public boolean guardar(Seguro seguro) {
        return ejecutarEscritura(SQL_INSERT, seguro, "Error al guardar el seguro.");
    }

    /**
     * {@inheritDoc}
     *
     * @throws PersistenciaException si falla la consulta SQL
     */
    @Override
    public Seguro buscarPorNumero(String numero) {
        try (PreparedStatement ps = ConexionBD.getInstancia().getConexion()
                .prepareStatement(SQL_SELECT + " WHERE s.numero = ?")) {
            ps.setString(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar el seguro " + numero + ".", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws PersistenciaException si falla la consulta SQL
     */
    @Override
    public List<Seguro> buscarTodos() {
        List<Seguro> seguros = new ArrayList<>();
        try (PreparedStatement ps = ConexionBD.getInstancia().getConexion()
                .prepareStatement(SQL_SELECT + " ORDER BY s.numero");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                seguros.add(mapear(rs));
            }
            return seguros;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar los seguros.", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws PersistenciaException si falla la sentencia SQL
     */
    @Override
    public boolean actualizar(Seguro seguro) {
        return ejecutarEscritura(SQL_UPDATE, seguro, "Error al actualizar el seguro.");
    }

    /**
     * {@inheritDoc}
     *
     * @throws PersistenciaException si falla la sentencia SQL
     */
    @Override
    public boolean eliminar(String numero) {
        try (PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(SQL_DELETE)) {
            ps.setString(1, numero);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al eliminar el seguro " + numero + ".", e);
        }
    }

    /**
     * Ejecuta un INSERT o UPDATE con los datos del seguro. Ambas sentencias usan
     * el mismo orden de parámetros, con el número al final.
     *
     * @param sql          sentencia a ejecutar
     * @param seguro       seguro con los datos
     * @param mensajeError mensaje de la {@link PersistenciaException} si falla
     * @return {@code true} si se afectó al menos una fila
     */
    private boolean ejecutarEscritura(String sql, Seguro seguro, String mensajeError) {
        try (PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(seguro.getFechaExpedicion()));
            ps.setDouble(2, seguro.getValor());
            ps.setString(3, seguro.getEstado());
            ps.setString(4, seguro.getTipo());
            ps.setString(5, seguro.getAsegurado().getIdentificacion());
            ps.setString(6, seguro instanceof SeguroVida v ? v.getNombreBeneficiario() : null);
            ps.setString(7, seguro instanceof SeguroVida v ? v.getParentesco() : null);
            ps.setString(8, seguro instanceof SeguroVehiculo v ? v.getMarca() : null);
            ps.setString(9, seguro.getNumero());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException(mensajeError, e);
        }
    }

    /**
     * Construye la subclase de {@link Seguro} que corresponde a la fila actual,
     * según la columna {@code tipo}.
     *
     * @param rs resultado posicionado en una fila
     * @return {@link SeguroVida} o {@link SeguroVehiculo}
     * @throws SQLException si no se puede leer una columna
     */
    private Seguro mapear(ResultSet rs) throws SQLException {
        Asegurado asegurado = new Asegurado(
                rs.getString("identificacion"),
                rs.getString("nombre"),
                rs.getString("correo_electronico"));
        if (SeguroVida.TIPO.equals(rs.getString("tipo"))) {
            return new SeguroVida(
                    rs.getString("numero"),
                    rs.getDate("fecha_expedicion").toLocalDate(),
                    rs.getDouble("valor"),
                    rs.getString("estado"),
                    asegurado,
                    rs.getString("nombre_beneficiario"),
                    rs.getString("parentesco"));
        }
        return new SeguroVehiculo(
                rs.getString("numero"),
                rs.getDate("fecha_expedicion").toLocalDate(),
                rs.getDouble("valor"),
                rs.getString("estado"),
                asegurado,
                rs.getString("marca"));
    }
}
