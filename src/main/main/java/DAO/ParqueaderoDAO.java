package org.example.dao;

import org.example.model.Parqueadero;
import org.example.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParqueaderoDAO {

    public void insertarParqueadero(Parqueadero parqueadero) {

        String sql = """
                INSERT INTO parqueaderos
                (zona, capacidad, ocupados)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conexion = ConexionDB.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, parqueadero.getZona());
            ps.setInt(2, parqueadero.getCapacidad());
            ps.setInt(3, parqueadero.getOcupados());

            ps.executeUpdate();

            System.out.println("Parqueadero insertado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}