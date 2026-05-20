package org.example.dao;

import org.example.model.Usuario;
import org.example.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public void insertarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuarios(nombre, dni, correo) VALUES (?, ?, ?)";

        try (
                Connection conexion = ConexionDB.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getDni());
            ps.setString(3, usuario.getCorreo());

            ps.executeUpdate();

            System.out.println("Usuario insertado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}