package main.main;

import main.main.java.DAO.ParkingDAOimpl;
import main.main.java.DAO.RecordDAOimpl;
import main.main.java.DAO.UserDAOimpl;
import main.main.java.DAO.VehicleDAOimpl;
import main.main.java.model.Parking;
import main.main.java.model.User;
import main.main.java.model.Vehicle;
import main.main.java.enums.TypeVehicle;
import main.main.java.util.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Scanner para leer datos por consola
        Scanner sc = new Scanner(System.in);

        try (Connection connection = ConnectionDB.getConnection()) {

            UserDAOimpl userDAO = new UserDAOimpl(connection);
            VehicleDAOimpl vehicleDAO = new VehicleDAOimpl(connection);
            ParkingDAOimpl parkingDAO = new ParkingDAOimpl(connection);
            RecordDAOimpl rocordDAO = new RecordDAOimpl(connection);

            User currentUser = null; // usuario logueado

            while (true) {

                // MENU PRINCIPAL
                if (currentUser == null) {

                    System.out.println("\n SISTEMA PARQUEADERO ");
                    System.out.println("1. Crear usuario");
                    System.out.println("2. Iniciar sesion");
                    System.out.println("3. Leer codigo de barras");
                    System.out.println("0. Salir");

                    int option = sc.nextInt();
                    sc.nextLine();

                    switch (option) {

                        // CREAR USUARIO
                        case 1 -> {

                            System.out.println("\n=== CREAR USUARIO ===");

                            System.out.print("Nombre: ");
                            String name = sc.nextLine();

                            System.out.print("Documento: ");
                            int document = sc.nextInt();

                            System.out.print("Correo: ");
                            String email = sc.nextLine();

                            User user = new User(0, name, document, email);
                            userDAO.create(user);
                        }

                        // INICIAR SESION (simple por documento)
                        case 2 -> {
                            System.out.println("\n=== INICIAR SESION ===");
                            System.out.print("Documento: ");
                            int document = sc.nextInt();
                            currentUser = userDAO.read(document);
                            System.out.println("Sesion iniciada");
                        }
                        // LEER CODIGO
                        case 3 -> {
                            System.out.print("Codigo: ");
                            int code = sc.nextInt();
                            sc.nextLine();
                            Vehicle v = vehicleDAO.read(code);
                            if (v != null) {
                                //encontrar usuario dueño del vehículo
                                User u = userDAO.read(v.getIdUser());
                                //encontrar espacios vacios en el parqeuadero
                                TypeVehicle type =  v.getType();
                                Parking p = parkingDAO.readByType(type);
                                //bienvenida
                                System.out.println("Bienvenido " + u.getName());
                                System.out.println("Hay " + (p.getAbility() - p.getOccupied()) + " espacios vacios para tu " + v.getType() + " en el parquedero");
                                System.out.println("Placa: " + v.getPlate());
                            } else {
                                System.out.println("No encontrado");
                            }
                        }
                        case 4 -> {
                            System.out.println("Saliendo");
                            return;
                        }

                        default -> System.out.println("Opcion invalida");
                    }
                } else {
                    // MENU LOGUEADO
                    System.out.println("\n=== MENU USUARIO ===");
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
                            TypeVehicle type = TypeVehicle.valueOf(sc.nextLine().toUpperCase());

                            System.out.println("Placa: ");
                            String plate = sc.nextLine();

                            //barras (falta)
                            int code = (int) (Math.random() * 100000);

                            //
                            Vehicle vehicle = new Vehicle(code, type, plate);
                            vehicle.setIdUser(currentUser.getId());

                            vehicleDAO.create(vehicle);

                            System.out.println("Vehiculo registrado");
                        }

                        // BUSCAR POR PLACA
                        case 2 -> {

                            System.out.print("Placa: ");
                            String plate = sc.nextLine();

                            Vehicle v = vehicleDAO.findByPlate(plate);

                            if (v != null) {
                                System.out.println("Encontrado: " + v);
                            } else {
                                System.out.println("No encontrado");
                            }
                        }

                        // LEER CODIGO
                        case 3 -> {
                            System.out.print("Codigo: ");
                            int code = sc.nextInt();
                            sc.nextLine();
                            Vehicle v = vehicleDAO.read(code);
                            if (v != null) {
                                //encontrar usuario dueño del vehículo
                                User u = userDAO.read(v.getIdUser());
                                //encontrar espacios vacios en el parqeuadero
                                TypeVehicle type =  v.getType();
                                Parking p = parkingDAO.readByType(type);
                                //bienvenida
                                System.out.println("Bienvenido " + u.getName());
                                System.out.println("Hay " + (p.getAbility() - p.getOccupied()) + " espacios vacios para tu " + v.getType() + " en el parquedero");
                                System.out.println("Placa: " + v.getPlate());
                            } else {
                                System.out.println("No encontrado");
                            }
                        }

                        // LOGOUT
                        case 4 -> {
                            currentUser = null;
                            System.out.println("Sesion cerrada");
                        }
                        default -> System.out.println("Opcion invalida");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error BD: " + e.getMessage());
        }
    }
}
//