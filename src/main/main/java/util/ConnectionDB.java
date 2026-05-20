package main.main.java.util;

import java.sql.*;

public class ConnectionDB {

    private static final String URL = "jdbc:mysql://localhost:3114/gestionParqueadero"; // atributo privado, constante y de uso propio de la clase (static), que guarda la dirección de la base de datos
    private static final String USER = "root"; // atributo privado, constante y de uso propio de la clase (static), que guarda el usuario de la base de datos
    private static final String PASSWORD = "1234"; // atributo privado, constante y de uso propio de la clase (static), que guarda la contraseña de la base de datos

    public static Connection getConnection() throws SQLException { // Que el metodo sea static permite usarlo sin crear una instancia (objeto), recurriendo directamente a la clase
        // Connection es una interfaz de la librería java.sql (nos está diciendo que lo que devuelve este metodo será un objeto de alguna clase que implementa esta interfaz), la cual nos ayuda a establecer conexión con la BD y trabajar/comunicarse con ella mediante los métodos de la interfaz.
        // Throws SQLException indica que en este metodo puede ocurrir un error y de existir ese error va a subir por los metodos previos (los que llamaron a este metodo de esta clase(call stack) ) en un objeto de tipo SQLException hasta llegar al metodo que contiene el catch el cual se encarga de recibir ese objeto error, luego lo almacena en una variable normalmente llamada ( e) y mediante esa variable que ahora es ese objeto manejar el error dentro del catch
        return DriverManager.getConnection(URL, USER, PASSWORD); // DriverManager es una clase que tiene un metodo estático llamado "getConnection", el cual busca el driver JDBC adecuado para nuestra base de datos MySQL (lo identifica porque la URL contiene "jdbc:mysql"); luego, usando las credenciales proporcionadas, establece la conexión y devuelve un objeto de tipo Connection que representa la conexión activa con la base de datos y permite trabajar con ella mediante los métodos de la interfaz Connection.
    }

}