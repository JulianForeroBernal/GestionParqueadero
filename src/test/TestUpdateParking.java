package test;

import main.main.java.DAO.ParkingDAOimpl;
import main.main.java.enums.TypeVehicle;
import main.main.java.model.Parking;
import main.main.java.util.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class TestUpdateParking {

    public static void main(String[] args) {

        try (Connection connection = ConnectionDB.getConnection()) {

            ParkingDAOimpl parkingDAO = new ParkingDAOimpl(connection);

            System.out.println("=== TEST UPDATE PARKING ===");

            Parking parking = new Parking(
                    80,             // ability
                    1,              // id
                    20,             // occupied
                    TypeVehicle.CARRO
            );

            parkingDAO.update(parking);

            System.out.println("Parking actualizado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}