package main.main;

import main.main.java.DAO.ParkingDAOimpl;
import main.main.java.DAO.RecordDAOimpl;
import main.main.java.DAO.UserDAOimpl;
import main.main.java.DAO.VehicleDAOimpl;
import main.main.java.enums.TypeVehicle;
import main.main.java.model.Parking;
import main.main.java.model.Record;
import main.main.java.model.User;
import main.main.java.model.Vehicle;
import main.main.java.util.Barcode;
import main.main.java.util.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Scanner para leer datos por consola
        Scanner sc = new Scanner(System.in);

        try (Connection connection = ConnectionDB.getConnection()) {

            // Crear objetos DAO
            UserDAOimpl userDAO = new UserDAOimpl(connection);
            VehicleDAOimpl vehicleDAO = new VehicleDAOimpl(connection);
            ParkingDAOimpl parkingDAO = new ParkingDAOimpl(connection);
            RecordDAOimpl rocordDAO = new RecordDAOimpl(connection);

            // Usuario actualmente logueado
            User currentUser = null;

            // Ciclo principal del sistema
            while (true) {

                // MENU PRINCIPAL
                if (currentUser == null) {

                    System.out.println("/n SISTEMA PARQUEADERO ");
                    System.out.println("1. Crear usuario");
                    System.out.println("2. Iniciar sesion");
                    System.out.println("3. Leer codigo de barras");
                    System.out.println("0. Salir");

                    int option = sc.nextInt();
                    sc.nextLine();

                    switch (option) {

                        // CREAR USUARIO
                        case 1 -> {

                            System.out.println("/n=== CREAR USUARIO ===");

                            System.out.print("Nombre: ");
                            String name = sc.nextLine();

                            System.out.print("Documento: ");
                            int document = sc.nextInt();
                            sc.nextLine();

                            System.out.print("Correo: ");
                            String email = sc.nextLine();

                            // Crear objeto usuario
                            User user = new User(0, name, document, email);

                            // Guardar usuario
                            userDAO.create(user);
                        }

                        // INICIAR SESION
                        case 2 -> {

                            System.out.println("/n=== INICIAR SESION ===");

                            System.out.print("Documento: ");
                            int document = sc.nextInt();
                            sc.nextLine();

                            // Buscar usuario
                            currentUser = userDAO.findByDNI(document);

                            // Validar usuario
                            if (currentUser != null) {

                                System.out.println("Sesion iniciada");

                            } else {

                                System.out.println("Usuario no encontrado");
                            }
                        }

                        // LEER CODIGO DE BARRAS
                        case 3 -> {

                            System.out.print("Codigo: ");
                            int code = sc.nextInt();
                            sc.nextLine();

                            // Buscar vehiculo por codigo
                            Vehicle v = vehicleDAO.read(code);

                            // Validar si existe
                            if (v != null) {

                                // Buscar dueño del vehículo
                                User u = userDAO.read(v.getIdUser());

                                // Obtener tipo del vehículo
                                TypeVehicle type = v.getType();

                                // Buscar parqueadero correspondiente
                                Parking p = parkingDAO.readByType(type);

                                // Verificar si el vehiculo ya esta dentro
                                if (rocordDAO.vehicleInside(v.getIdVehicle())) {

                                    // Registrar salida
                                    rocordDAO.registerExit(v.getIdVehicle());

                                    System.out.println("Hasta luego " + u.getName());
                                    System.out.println("Salida registrada");

                                } else {

                                    // Crear registro de entrada
                                    Record record = new Record(
                                            0,
                                            LocalDateTime.now(),
                                            null,
                                            v.getIdVehicle(),
                                            p.getId()
                                    );

                                    // Guardar registro
                                    rocordDAO.create(record);

                                    // Mensaje de bienvenida
                                    System.out.println("Bienvenido " + u.getName());

                                    System.out.println(
                                            "Hay "
                                                    + (p.getAbility() - p.getOccupied())
                                                    + " espacios vacios para tu "
                                                    + v.getType()
                                                    + " en el parqueadero"
                                    );

                                    System.out.println("Placa: " + v.getPlate());

                                    System.out.println("Entrada registrada");
                                }

                                // Mostrar cantidad de vehiculos dentro
                                System.out.println(
                                        "Vehiculos dentro: "
                                                + rocordDAO.vehiclesInside()
                                );

                            } else {

                                System.out.println("No encontrado");
                            }
                        }

                        // SALIR DEL SISTEMA
                        case 0 -> {

                            System.out.println("Saliendo");
                            return;
                        }

                        // OPCION INVALIDA
                        default -> System.out.println("Opcion invalida");
                    }

                } else {

                    // MENU USUARIO LOGUEADO
                    System.out.println("/n=== MENU USUARIO ===");
                    System.out.println("1. Registrar vehiculo");
                    System.out.println("2. Buscar por placa");
                    System.out.println("3. Leer codigo de barras");
                    System.out.println("4. Cerrar sesion");

                    int option = sc.nextInt();
                    sc.nextLine();

                    switch (option) {

                        // REGISTRAR VEHICULO
                        case 1 -> {

                            System.out.println("Tipo (CARRO/MOTO/BICICLETA): ");

                            // Leer tipo de vehículo
                            TypeVehicle type =
                                    TypeVehicle.valueOf(
                                            sc.nextLine().toUpperCase()
                                    );

                            System.out.println("Placa: ");
                            String plate = sc.nextLine();

                            // Generar codigo de barras aleatorias
                            int code = Barcode.ramdomNum(8);
                            String codeString = String.valueOf(code);
                            Barcode.createBarcode(codeString,300,100,"C:/Users/USUARIO/Documents/SEMESTRE 3/programacion 2/proyecto/GestionParqueadero/Docs/Barcodes");
                            Barcode.ShowWindow("C:/Users/USUARIO/Documents/SEMESTRE 3/programacion 2/proyecto/GestionParqueadero/Docs/Barcodes", "codigo de barras para " + plate);
                            System.out.println("su codigo de barras es: " + code);
                            System.out.println("guardelo para facilitar su ingreso y salida");
                            //

                            // Crear vehículo
                            Vehicle vehicle =
                                    new Vehicle(code, type, plate);

                            // Asignar usuario dueño
                            vehicle.setIdUser(currentUser.getId());

                            // Guardar vehiculo
                            vehicleDAO.create(vehicle);

                            System.out.println("Vehiculo registrado");
                        }

                        // BUSCAR VEHICULO POR PLACA
                        case 2 -> {

                            System.out.print("Placa: ");
                            String plate = sc.nextLine();

                            // Buscar vehiculo
                            Vehicle v = vehicleDAO.findByPlate(plate);

                            // Validar resultado
                            if (v != null) {

                                System.out.println("Encontrado: " + v);

                            } else {

                                System.out.println("No encontrado");
                            }
                        }

                        // LEER CODIGO DE BARRAS
                        case 3 -> {

                            System.out.print("Codigo: ");
                            int code = sc.nextInt();
                            sc.nextLine();

                            // Buscar vehiculo
                            Vehicle v = vehicleDAO.read(code);

                            // Validar si existe
                            if (v != null) {

                                // Buscar dueño del vehículo
                                User u = userDAO.read(v.getIdUser());

                                // Obtener tipo del vehículo
                                TypeVehicle type = v.getType();

                                // Buscar parqueadero
                                Parking p = parkingDAO.readByType(type);

                                // Verificar si el vehiculo esta dentro
                                if (rocordDAO.vehicleInside(v.getIdVehicle())) {

                                    // Registrar salida
                                    rocordDAO.registerExit(v.getIdVehicle());

                                    System.out.println("Hasta luego " + u.getName());
                                    System.out.println("Salida registrada");

                                } else {

                                    // Crear nuevo registro
                                    Record record = new Record(
                                            0,
                                            LocalDateTime.now(),
                                            null,
                                            v.getIdVehicle(),
                                            p.getId()
                                    );

                                    // Guardar registro
                                    rocordDAO.create(record);

                                    // Mensaje de bienvenida
                                    System.out.println("Bienvenido " + u.getName());

                                    System.out.println(
                                            "Hay "
                                                    + (p.getAbility() - p.getOccupied())
                                                    + " espacios vacios para tu "
                                                    + v.getType()
                                                    + " en el parqueadero"
                                    );

                                    System.out.println("Placa: " + v.getPlate());

                                    System.out.println("Entrada registrada");
                                }

                                // Mostrar cantidad de vehiculos dentro
                                System.out.println(
                                        "Vehiculos dentro: "
                                                + rocordDAO.vehiclesInside()
                                );

                            } else {

                                System.out.println("No encontrado");
                            }
                        }

                        // CERRAR SESION
                        case 4 -> {

                            currentUser = null;

                            System.out.println("Sesion cerrada");
                        }

                        // OPCION INVALIDA
                        default -> System.out.println("Opcion invalida");
                    }
                }
            }

        } catch (SQLException e) {

            // Error de conexion con la base de datos
            System.out.println("Error BD: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}