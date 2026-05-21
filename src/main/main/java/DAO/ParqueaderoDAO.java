package dao;

import modelo.Parqueadero;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParqueaderoDAO {

    // ──── CREATE ──────────────────────────────────────────────────────────

    public boolean insertar(Parqueadero p) {
        String sql = "INSERT INTO parqueadero (zona, capacidad, ocupados) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getZona());
            ps.setInt(2, p.getCapacidad());
            ps.setInt(3, p.getOcupados());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) p.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar parqueadero: " + e.getMessage());
        }
        return false;
    }

    // ──── READ por ID ─────────────────────────────────────────────────────

    public Parqueadero buscarPorId(int id) {
        String sql = "SELECT * FROM parqueadero WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar parqueadero por id: " + e.getMessage());
        }
        return null;
    }

    // ──── READ por zona ───────────────────────────────────────────────────

    public List<Parqueadero> buscarPorZona(String zona) {
        List<Parqueadero> lista = new ArrayList<>();
        String sql = "SELECT * FROM parqueadero WHERE zona = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, zona);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar parqueadero por zona: " + e.getMessage());
        }
        return lista;
    }

    // ──── READ con espacios disponibles ───────────────────────────────────

    public List<Parqueadero> listarConDisponibilidad() {
        List<Parqueadero> lista = new ArrayList<>();
        String sql = "SELECT * FROM parqueadero WHERE ocupados < capacidad";
        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar parqueaderos con disponibilidad: " + e.getMessage());
        }
        return lista;
    }

    // ──── READ todos ──────────────────────────────────────────────────────

    public List<Parqueadero> listarTodos() {
        List<Parqueadero> lista = new ArrayList<>();
        String sql = "SELECT * FROM parqueadero";
        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar parqueaderos: " + e.getMessage());
        }
        return lista;
    }

    // ──── UPDATE ──────────────────────────────────────────────────────────

    public boolean actualizar(Parqueadero p) {
        String sql = "UPDATE parqueadero SET zona = ?, capacidad = ?, ocupados = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getZona());
            ps.setInt(2, p.getCapacidad());
            ps.setInt(3, p.getOcupados());
            ps.setInt(4, p.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar parqueadero: " + e.getMessage());
        }
        return false;
    }

    // ──── UPDATE solo ocupados (entrada/salida rápida) ────────────────────

    public boolean actualizarOcupados(int id, int ocupados) {
        String sql = "UPDATE parqueadero SET ocupados = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ocupados);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar ocupados: " + e.getMessage());
        }
        return false;
    }

    // ──── DELETE ──────────────────────────────────────────────────────────

    public boolean eliminar(int id) {
        String sql = "DELETE FROM parqueadero WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar parqueadero: " + e.getMessage());
        }
        return false;
    }

    // ──── Mapeo ResultSet → Parqueadero ──────────────────────────────────

    private Parqueadero mapear(ResultSet rs) throws SQLException {
        return new Parqueadero(
                rs.getInt("id"),
                rs.getString("zona"),
                rs.getInt("capacidad"),
                rs.getInt("ocupados")
        );
    }
}