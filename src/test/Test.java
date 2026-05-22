package test;

import main.main.java.model.User;
import main.main.java.DAO.UserDAOimpl;
import main.main.java.util.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class Test {
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

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }
}
