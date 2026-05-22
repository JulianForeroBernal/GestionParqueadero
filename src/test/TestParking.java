import main.main.java.model.Parking;
import main.main.java.enums.TypeVehicle;
import main.main.java.DAO.ParkingDAOimpl;
import main.main.java.util.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class TestParking {

    public static void main(String[] args) {

        try (Connection connection = ConnectionDB.getConnection()) {

            ParkingDAOimpl parkingDAO = new ParkingDAOimpl(connection);

            System.out.println("=== TEST CREATE PARKING ===");

            Parking parking = new Parking(
                    50,              // ability
                    1,               // id
                    10,              // occupied
                    TypeVehicle.CARRO
            );

            parkingDAO.create(parking);

            System.out.println("\n=== TEST READ ALL PARKINGS ===");
            System.out.println(parkingDAO.list());

            System.out.println("\n=== TEST READ BY ID ===");
            System.out.println(parkingDAO.read(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}