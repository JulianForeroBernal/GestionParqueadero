package test;

import main.main.java.model.User;
import main.main.java.model.Vehicle;
import main.main.java.enums.TypeVehicle;
import main.main.java.DAO.VehicleDAOimpl;
import main.main.java.util.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestVehicle {

    public static void main(String[] args) {

        try (Connection connection = ConnectionDB.getConnection()) {

            VehicleDAOimpl vehicleDAO = new VehicleDAOimpl(connection);

            System.out.println("=== TEST CREATE VEHICLE ===");

            Vehicle vehicle = new Vehicle(
                    0,
                    123456, // barcode
                    TypeVehicle.CARRO,
                    "ABC123",
                    1
            );

            vehicleDAO.create(vehicle);
            System.out.println("Vehículo creado\n");

            System.out.println("=== TEST READ VEHICLE ===");

            Vehicle vRead = vehicleDAO.read(123456);

            if (vRead != null) {
                System.out.println("Vehículo encontrado:");
                System.out.println("ID: " + vRead.getIdVehicle());
                System.out.println("Barcode: " + vRead.getBarCode());
                System.out.println("Tipo: " + vRead.getType());
                System.out.println("Placa: " + vRead.getPlate());
                System.out.println("Usuario ID: " + vRead.getIdUser());
            } else {
                System.out.println("No encontrado");
            }

            System.out.println("\n=== TEST LIST VEHICLES ===");

            List<Vehicle> list = vehicleDAO.list();

            for (Vehicle v : list) {
                System.out.println("------------------");
                System.out.println("ID: " + v.getIdVehicle());
                System.out.println("Barcode: " + v.getBarCode());
                System.out.println("Tipo: " + v.getType());
                System.out.println("Placa: " + v.getPlate());
                System.out.println("User: " + v.getIdUser());
            }

            System.out.println("\n=== TEST UPDATE VEHICLE ===");

            if (vRead != null){
            Vehicle updated = new Vehicle(
                    vRead.getIdVehicle(),
                    vRead.getBarCode(),
                    TypeVehicle.MOTO,
                    "XYZ999",
                    vRead.getIdUser()
            );

            vehicleDAO.update(updated);
            System.out.println("Vehículo actualizado");

            Vehicle vAfter = vehicleDAO.read(123456);

            System.out.println("=== DESPUÉS UPDATE ===");
            System.out.println("Placa: " + vAfter.getPlate());
            System.out.println("Tipo: " + vAfter.getType());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
