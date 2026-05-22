package test;

import main.main.java.model.User;
import main.main.java.DAO.UserDAOimpl;
import main.main.java.util.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestUsuario {
    public static void main(String[] args) {
        try (Connection connection = ConnectionDB.getConnection()) {
            UserDAOimpl userDAO = new UserDAOimpl(connection);

            System.out.println(
                    "=== TEST CREATE USER ==="
            );

            User user =
                    new User(
                            0,
                            "Julian",
                            "123456",
                            "julian@udec.edu"
                    );

            userDAO.create(user);
            System.out.println(
                    "Usuario creado\n"
            );

            System.out.println(
                    "=== TEST READ USER ==="
            );

            User usuarioLeido = userDAO.read(1);

            if (usuarioLeido != null) {

                System.out.println(
                        "Usuario encontrado:\n"
                );

                System.out.println(
                        "ID: " + usuarioLeido.getId()
                );

                System.out.println(
                        "Nombre: " + usuarioLeido.getName()
                );

                System.out.println(
                        "DNI: " + usuarioLeido.getDNI()
                );

                System.out.println(
                        "Correo: " + usuarioLeido.getEmail()
                );

            } else {
                System.out.println("Usuario no encontrado");
            }

            System.out.println(
                    "=== TEST LIST USER ==="
            );

            List<User> users = userDAO.list();

            if (users != null && !users.isEmpty()) {

                for (User u : users) {

                    System.out.println(
                            "\n-------------------"
                    );

                    System.out.println(
                            "ID: " + u.getId()
                    );

                    System.out.println(
                            "Nombre: " + u.getName()
                    );

                    System.out.println(
                            "DNI: " + u.getDNI()
                    );

                    System.out.println(
                            "Correo: " + u.getEmail()
                    );
                }

            } else {

                System.out.println(
                        "No hay usuarios registrados"
                );
            }

            System.out.println(
                    "\n=== TEST UPDATE USER ==="
            );

            User updatedUser = new User(
                    1,
                    "Julian Updated",
                    "999999",
                    "updated@udec.edu"
            );

            userDAO.update(updatedUser);

            System.out.println(
                    "Usuario actualizado\n"
            );

            User usuarioActualizado = userDAO.read(1);

            if (usuarioActualizado != null) {

                System.out.println(
                        "=== DATOS ACTUALIZADOS ==="
                );

                System.out.println(
                        "ID: " + usuarioActualizado.getId()
                );

                System.out.println(
                        "Nombre: " + usuarioActualizado.getName()
                );

                System.out.println(
                        "DNI: " + usuarioActualizado.getDNI()
                );

                System.out.println(
                        "Correo: " + usuarioActualizado.getEmail()
                );

            } else {

                System.out.println(
                        "No se encontró el usuario"
                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
