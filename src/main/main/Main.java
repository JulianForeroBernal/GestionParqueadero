package main.main;

import main.main.java.DAO.UserDAOimpl;
import main.main.java.model.User;
import main.main.java.model.Vehicle;
import main.main.java.util.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Scanner para leer datos por consola
        Scanner sc = new Scanner(System.in);

        // Conexion a la base de datos usando try-with-resources
        try (Connection connection = ConnectionDB.getConnection()) {

            // DAO del usuario (permite CRUD en la BD)
            UserDAOimpl userDAO = new UserDAOimpl(connection);

            // Menú infinito hasta que el usuario decida salir
            while (true) {

                System.out.println("\n SISTEMA PARQUEADERO ");
                System.out.print("\nSeleccione una opcion: ");
                System.out.println("\n1. Registrar usuario");
                System.out.println("2. Leer código de barras");
                System.out.println("3. Salir");


                // Leer opcion del usuario
                int option = sc.nextInt();
                sc.nextLine(); // limpiar buffer (evita errores con nextLine)

                // Estructura del menu
                switch (option) {

                    // OPCION 1: REGISTRAR USUARIO

                    case 1 -> {

                        System.out.println("\n=== REGISTRAR USUARIO ===");

                        // Pedir datos al usuario
                        System.out.print("Nombre: ");
                        String name = sc.nextLine();

                        System.out.print("Documento: ");
                        String document = sc.nextLine();

                        System.out.print("Correo: ");
                        String email = sc.nextLine();

                        if (email.endsWith("@ucundinamarca.edu.co")){ // por ahora se "valída" que sea mienbro de la universidad mediante el correo ingresado, para hacerlo con más rigor (e incluso con otros datos como el número de identificacion) se precisaria de bases de datos de la universidad
                            // Crear objeto User con los datos ingresados
                            User user = new User(0, name, document, email); //se pasa 0 como id en todas la ocaciones, pues la base de datos automaticamente cambiara este valor por el siguiente en el registro
                            // Guardar usuario en la base de datos
                            userDAO.create(user);
                            System.out.println("Usuario registrado correctamente");
                            // INMEDIANTAMENTE EL USUARIO DEBE REGISTRAR COMO MINIMO UN VECHICULO
                            int optionRecord; //variable de control para el registro de vehiculos
                            do {
                                System.out.println("REGISTRE SU VEHICULO");
                                System.out.println("Tipo del vehiculo: ");
                                String type = sc.nextLine();
                                System.out.println("Placa: ");
                                String plate = sc.nextLine();

                                //generacion codigo de barras



                                Vehicle vehicle = new Vehicle();


                                System.out.println("""
                                        1. registrar otro vehiculo
                                        0. salir
                                        """);
                                optionRecord = sc.nextInt();
                            }while (optionRecord == 1);

                        }else {
                            System.out.println("el correo que ingreso: " + email + "no pertenece a nincun mienbro de nuestra comunidad universitara por lo tanto el registro es invalido");
                        }
                    }

                    // OPCION 2: LEER CODIGO DE BARRAS

                    case 2 -> {
                        System.out.println("\n=== LEER CÓDIGO DE BARRAS ===");
                        // Pedir código de barras del vehículo
                        System.out.print("Ingrese código de barras: ");
                        int code = sc.nextInt();

                        // Mostrarlo por ahora
                        System.out.println("Código ingresado: " + code);

                    }


                    // OPCIÓN 3: SALIR DEL SISTEMA

                    case 3 -> {

                        System.out.println("Saliendo del sistema...");
                        return; // termina el programa
                    }

                    // OPCION INVALIDA

                    default -> System.out.println("Opción invalida");
                }
            }

        } catch (SQLException e) {
            // Error de conexion con la base de datos
            System.out.println("Error de conexión a la BD");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
}