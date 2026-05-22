package main.main.java.DAO;

import main.main.java.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOimpl implements CRUDL<User> {

    private final Connection connection; // conexion a la base de datos

    public UserDAOimpl(Connection connection) { // constructor
        this.connection = connection;
    }

    @Override
    public void create(User user) {

        // consulta SQL para insertar un usuario en la tabla
        String sql = "INSERT INTO usuarios (nombre, dni, correo) VALUES (?, ?, ?)";

        try (
                // PreparedStatement permite ejecutar consultas SQL seguras
                // Statement.RETURN_GENERATED_KEYS permite obtener el ID autogenerado por MySQL
                PreparedStatement statement =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            // asignacion de valores a los placeholders (?)
            statement.setString(1, user.getName());
            statement.setInt(2, user.getDNI());
            statement.setString(3, user.getEmail());

            // ejecucion de la consulta (INSERT)
            int filas = statement.executeUpdate();

            // ================================
            // OBTENER ID GENERADO POR MYSQL
            // ================================
            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                // guardamos el ID real generado en el objeto User
                user.setId(keys.getInt(1));
            }

            // cerramos el ResultSet (buena práctica)
            keys.close();

            // validacion de resultado
            if (filas > 0) {
                System.out.println("Usuario registrado con exito");
            } else {
                System.out.println("No se pudo registrar el usuario ");
            }

        } catch (SQLException e) {
            // manejo de error en base de datos
            System.err.println("Error al insertar usuario: " + e.getMessage());
        }
    }

    @Override
    public User read(int id) {

        // consulta para buscar usuario por ID
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            // asignamos el ID al placeholder
            statement.setInt(1, id);

            // ejecutamos la consulta SELECT
            ResultSet resultSet = statement.executeQuery();

            // movemos el cursor al primer resultado
            if (resultSet.next()) {

                // construimos el objeto User con datos de la BD
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("dni"),
                        resultSet.getString("correo")
                );
            }

        } catch (SQLException e) {
            System.out.println("Ocurrio un error al leer la base de datos");
            System.out.println("Detalles: " + e.getMessage());
        }

        return null; // si no encuentra el usuario
    }

    @Override
    public void update(User user) {

        // consulta para actualizar usuario por ID
        String sql = "UPDATE usuarios SET nombre = ?, dni = ?, correo = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            // asignacion de valores nuevos
            statement.setString(1, user.getName());
            statement.setInt(2, user.getDNI());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getId());

            // ejecucion del UPDATE
            if (statement.executeUpdate() > 0) {
                System.out.println("Usuario actualizado con exito");
            } else {
                System.out.println("No se pudo actualizar el usuario");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {

        // consulta para eliminar usuario por ID
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            // asignamos ID al placeholder
            statement.setInt(1, id);

            // ejecutamos DELETE
            int filas = statement.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario eliminado exitosamente"); // corregido mensaje
            } else {
                System.out.println("No se pudo eliminar el usuario");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    @Override
    public List<User> list() {

        // lista donde se almacenan los usuarios encontrados
        List<User> users = new ArrayList<>();

        // consulta para traer todos los usuarios
        String sql = "SELECT * FROM usuarios";

        try (Statement statement = connection.createStatement()) {

            // ejecuta consulta SELECT
            ResultSet resultSet = statement.executeQuery(sql);

            // recorrer todas las filas del resultado
            while (resultSet.next()) {

                // crear objeto User por cada fila encontrada
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("dni"),
                        resultSet.getString("correo")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios");
            System.out.println("Detalles: " + e.getMessage());
        }

        // devolver lista completa
        return users;
    }
}