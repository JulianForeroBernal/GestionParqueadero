package main.main.java.DAO;

import main.main.java.enums.TypeVehicle;
import main.main.java.model.Parking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingDAOimpl implements CRUDL<Parking> {

    private final Connection connection;

    public ParkingDAOimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Parking parking) {
        String sql = "INSERT INTO parqueaderos (capacidad, ocupados, zona) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, parking.getAbility());
            statement.setInt(2, parking.getOccupied());
            statement.setString(3, parking.getZone().name());
            int filas = statement.executeUpdate();
            if (filas > 0) {
                System.out.println("Parqueadero registrado con éxito");
            } else {
                System.out.println("No se pudo registrar el parqueadero");
            }
        } catch (SQLException e) {
            System.out.println("¡Error al crear parqueadero!");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    @Override
    public Parking read(int id) {
        String sql = "SELECT * FROM parqueaderos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Parking(
                        resultSet.getInt("capacidad"),
                        resultSet.getInt("id"),
                        resultSet.getInt("ocupados"),
                        TypeVehicle.valueOf(resultSet.getString("zona"))
                );
            }
        } catch (SQLException e) {
            System.out.println("¡Error al leer parqueadero!");
            System.out.println("Detalles: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Parking parking) {
        String sql = "UPDATE parqueaderos SET capacidad = ?, ocupados = ?, zona = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, parking.getAbility());
            statement.setInt(2, parking.getOccupied());
            statement.setString(3, parking.getZone() != null ? parking.getZone().name() : null);
            statement.setInt(4, parking.getId());
            int filas = statement.executeUpdate();
            if (filas > 0) {
                System.out.println("Parqueadero actualizado con éxito");
            } else {
                System.out.println("No se pudo actualizar el parqueadero");
            }
        } catch (SQLException e) {
            System.out.println("¡Error al actualizar parqueadero!");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM parqueaderos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int filas = statement.executeUpdate();
            if (filas > 0) {
                System.out.println("Parqueadero eliminado con éxito");
            } else {
                System.out.println("No se pudo eliminar el parqueadero");
            }
        } catch (SQLException e) {
            System.out.println("¡Error al eliminar parqueadero!");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    @Override
    public List<Parking> list() {
        List<Parking> parkings = new ArrayList<>();
        String sql = "SELECT * FROM parqueaderos";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                parkings.add(new Parking(
                        resultSet.getInt("capacidad"),
                        resultSet.getInt("id"),
                        resultSet.getInt("ocupados"),
                        TypeVehicle.valueOf(resultSet.getString("zona"))
                ));
            }
        } catch (SQLException e) {
            System.out.println("¡Error al listar parqueaderos!");
            System.out.println("Detalles: " + e.getMessage());
        }
        return parkings;
    }

    public Parking readByType(TypeVehicle type) {
        String sql = "SELECT * FROM parqueaderos WHERE zona = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, type.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Parking(
                        resultSet.getInt("capacidad"),
                        resultSet.getInt("id"),
                        resultSet.getInt("ocupados"),
                        TypeVehicle.valueOf(resultSet.getString("zona"))
                );
            }
        } catch (SQLException e) {
            System.out.println("¡Error al leer parqueadero!");
            System.out.println("Detalles: " + e.getMessage());
        }
        return null;
    }

}

