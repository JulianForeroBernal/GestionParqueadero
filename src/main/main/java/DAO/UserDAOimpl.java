package main.main.java.DAO;

import main.main.java.enums.TypeVehicle;
import main.main.java.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOimpl implements CRUDL<User> {

    private final Connection connection; // atributo para connexion base de datos

    public UserDAOimpl(Connection connection) { //constructor
        this.connection = connection;
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO usuarios (nombre, dni, correo) VALUES (?, ?, ?)"; //consulta
        try (PreparedStatement statement = connection.prepareStatement(sql)) { //prepara consulta
            statement.setString(1, user.getName());
            statement.setString(2, user.getDNI());
            statement.setString(3, user.getEmail());
            int filas = statement.executeUpdate(); // devuelve cuantas filas se modificaron
            if (filas > 0) {
                System.out.println("Usuario registrado con exito");
            } else {
                System.out.println("No se pudo registrar el usuario ");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
        }
    }

    @Override
    public User read(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) { // preparacion para la consulta
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery(); // objeto que guarda los datos que recibe de la consulta
            if (resultSet.next()) { // pasa el cursor a la siguiente fila
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("dni"),
                        resultSet.getString("correo")
                );
            }
        } catch (SQLException e) {
            System.out.println("ocurrio un error al leer la base de datos");
            System.out.println("detalles: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE usuarios SET nombre = ?, dni = ?, correo = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) { //perara la consulta
            statement.setString(1, user.getName());
            statement.setString(2, user.getDNI());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getId());
            if (statement.executeUpdate() > 0) {
                System.out.println("usuario actualizado con exito");
            } else {
                System.out.println("no se pudo actualizar el usuario");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar");
            System.out.println("\n Detalles : " + e.getMessage());

        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELATE FROM usuarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int filas = statement.executeUpdate();
            if (filas > 0) {
                System.out.println("Vehículo registrado exitosamente");
            } else {
                System.out.println("No se pudo registrar el vehículo");
            }
        } catch (SQLException e) {
            System.out.println("Error al acatualizar");
            System.out.println("\n Detalles: " + e.getMessage());
        }
    }

    @Override
    public List<User> list() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Statement statement = connection.createStatement()) { // no hay qeu preparar la consulta, pues no hay placeholders qeu modificar, la consulta ya está bien y será ejecutada justo como esta en la variable sql
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) { //se desplaza entre las filas
                users.add(new User( //agrega la lista los usuarios encontrados en la tabla de la BDs y guardados en resulSet
                                resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("dni"),
                                resultSet.getString("correo")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al acatualizar");
            System.out.println("\n Detalles: " + e.getMessage());
        }
        return users;
    }
}
