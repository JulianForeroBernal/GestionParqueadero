package main.main.java.DAO;

//imports
import main.main.java.enums.TypeVehicle;
import main.main.java.model.Vehicle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOimpl implements CRUDL<Vehicle> {

    private final Connection connection;

    public VehicleDAOimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Vehicle vehicle) {

        String sql = "INSERT INTO vehiculos (codigo_barras, tipo, placa, usuario_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, vehicle.getBarCode());
            statement.setString(2, vehicle.getType().name());
            statement.setString(3, vehicle.getPlate());
            statement.setInt(4, vehicle.getIdUser());

            int filas = statement.executeUpdate();

            if (filas > 0) {
                System.out.println("Vehículo registrado exitosamente");
            } else {
                System.out.println("No se pudo registrar el vehículo");
            }

        } catch (SQLException e) {

            System.out.println("¡ERROR AL CREAR EL VEHICULO!\n");
            System.out.println("\nDetalles del error: " + e.getMessage());
        }
    }

    @Override
    public Vehicle read(int barcode) {

        String sql = "SELECT * FROM vehiculos WHERE codigo_barras = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, barcode);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Vehicle(
                        resultSet.getInt("id"),
                        resultSet.getInt("codigo_barras"),
                        TypeVehicle.valueOf(resultSet.getString("tipo")),
                        resultSet.getString("placa"),
                        resultSet.getInt("usuario_id")
                );
            }

        } catch (SQLException e) {

            System.out.println("¡ERROR AL LEER EL VEHICULO!\n");
            System.out.println("\nDetalles del error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void update(Vehicle vehicle) {

        String sql = "UPDATE vehiculos SET codigo_barras = ?, tipo = ?, placa = ?, usuario_id = ? WHERE id =?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, vehicle.getBarCode());
            statement.setString(2, vehicle.getType().name());
            statement.setString(3, vehicle.getPlate());
            statement.setInt(4, vehicle.getIdUser());
            statement.setInt(5, vehicle.getIdVehicle());

            int filas = statement.executeUpdate();

            if (filas > 0) {
                System.out.println("Vehículo actualizado exitosamente");
            } else {
                System.out.println("No se pudo actualizar el vehículo");
            }

        } catch (SQLException e) {

            System.out.println("¡ERROR AL ACTUALIZAR EL VEHICULO!\n");
            System.out.println("\nDetalles del error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM vehiculos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {

            System.out.println("¡ERROR AL ELIMINAR EL VEHICULO!\n");
            System.out.println("\nDetalles del error: " + e.getMessage());
        }
    }

    @Override
    public List<Vehicle> list() {

        List<Vehicle> vehicles = new ArrayList<>();

        String sql = "SELECT * FROM vehiculos";

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                vehicles.add(new Vehicle(
                        resultSet.getInt("id"),
                        resultSet.getInt("codigo_barras"),
                        TypeVehicle.valueOf(resultSet.getString("tipo")),
                        resultSet.getString("placa"),
                        resultSet.getInt("usuario_id")
                ));
            }

        } catch (SQLException e) {

            System.out.println("¡ERROR AL LISTAR LOS VEHICULOS!\n");
            System.out.println("\nDetalles del error: " + e.getMessage());
        }

        return vehicles;
    }


    // METODO AGREGADO: BUSCAR VEHICULO POR PLACA

    // SE AGREGO ESTE METODO PORQUE EL MAIN LO ESTA UTILIZANDO
    // PARA BUSCAR VEHICULOS POR PLACA Y MOSTRAR SU INFORMACION
    // SIN ESTE METODO EL PROGRAMA NO COMPILA NI FUNCIONA

    public Vehicle findByPlate(String plate) {

        String sql = "SELECT * FROM vehiculos WHERE placa = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, plate);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Vehicle(
                        resultSet.getInt("id"),
                        resultSet.getInt("codigo_barras"),
                        TypeVehicle.valueOf(resultSet.getString("tipo")),
                        resultSet.getString("placa"),
                        resultSet.getInt("usuario_id")
                );
            }

        } catch (SQLException e) {

            System.out.println("¡ERROR AL BUSCAR VEHICULO POR PLACA!\n");
            System.out.println("\nDetalles del error: " + e.getMessage());
        }

        return null;
    }
}