package dao;

import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // ──── CREATE ──────────────────────────────────────────────────────────

    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre, dni, correo) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getDni());
            ps.setString(3, u.getCorreo());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) u.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
        }
        return false;
    }

    // ──── READ por ID ─────────────────────────────────────────────────────

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por id: " + e.getMessage());
        }
        return null;
    }

    // ──── READ por DNI ────────────────────────────────────────────────────

    public Usuario buscarPorDni(String dni) {
        String sql = "SELECT * FROM usuarios WHERE dni = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por dni: " + e.getMessage());
        }
        return null;
    }

    // ──── READ todos ──────────────────────────────────────────────────────

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    // ──── UPDATE ──────────────────────────────────────────────────────────

    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nombre = ?, dni = ?, correo = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getDni());
            ps.setString(3, u.getCorreo());
            ps.setInt(4, u.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
        return false;
    }

    // ──── DELETE ──────────────────────────────────────────────────────────

    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
        return false;
    }

    // ──── Mapeo ResultSet → Usuario ───────────────────────────────────────

    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("dni"),
                rs.getString("correo")
        );
    }
}
