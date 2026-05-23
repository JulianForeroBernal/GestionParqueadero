package main.main.java.DAO;

import java.time.LocalDateTime;
import main.main.java.model.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDAOimpl implements CRUDL<Record> {

    // Conexion a la base de datos
    private final Connection connection;

    // Constructor
    public RecordDAOimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Record record) {

        // Consulta SQL para insertar un registro
        String sql = """
                INSERT INTO registros
                (hora_entrada, hora_salida,
                 vehiculo_id, parqueadero_id)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            // Insertar hora de entrada
            statement.setTimestamp(1,
                    record.getEntryTime() != null
                            ? Timestamp.valueOf(record.getEntryTime())
                            : null
            );

            // Insertar hora de salida
            statement.setTimestamp(2,
                    record.getDepartureTime() != null
                            ? Timestamp.valueOf(record.getDepartureTime())
                            : null
            );

            // Insertar id del vehiculo
            statement.setInt(3, record.getVehicleId());

            // Insertar id del parqueadero
            statement.setInt(4, record.getParkingId());

            // Ejecutar consulta
            int filas = statement.executeUpdate();

            // Verificar si se inserto correctamente
            if (filas > 0) {
                System.out.println("Registro creado con exito");
            } else {
                System.out.println("No se pudo crear el registro");
            }

        } catch (SQLException e) {

            // Error SQL
            System.out.println("Error al crear registro");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    @Override
    public Record read(int id) {

        // Consulta SQL para buscar un registro por id
        String sql = "SELECT * FROM registros WHERE id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            // Asignar id
            statement.setInt(1, id);

            // Ejecutar consulta
            ResultSet resultSet = statement.executeQuery();

            // Verificar si existe el registro
            if (resultSet.next()) {

                // Validar si la hora de salida es null
                LocalDateTime salida =
                        resultSet.getTimestamp("hora_salida") != null
                                ? resultSet.getTimestamp("hora_salida")
                                  .toLocalDateTime()
                                : null;

                // Retornar objeto Record
                return new Record(
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("hora_entrada")
                                .toLocalDateTime(),
                        salida,
                        resultSet.getInt("vehiculo_id"),
                        resultSet.getInt("parqueadero_id")
                );
            }

        } catch (SQLException e) {

            // Error SQL
            System.out.println("Error al leer registro");
            System.out.println("Detalles: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void update(Record record) {

        // Consulta SQL para actualizar un registro
        String sql = """
                UPDATE registros
                SET hora_entrada = ?,
                    hora_salida = ?,
                    vehiculo_id = ?,
                    parqueadero_id = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            // Actualizar hora de entrada
            statement.setTimestamp(1,
                    record.getEntryTime() != null
                            ? Timestamp.valueOf(record.getEntryTime())
                            : null
            );

            // Actualizar hora de salida
            statement.setTimestamp(2,
                    record.getDepartureTime() != null
                            ? Timestamp.valueOf(record.getDepartureTime())
                            : null
            );

            // Actualizar id del vehiculo
            statement.setInt(3, record.getVehicleId());

            // Actualizar id del parqueadero
            statement.setInt(4, record.getParkingId());

            // Actualizar id del registro
            statement.setInt(5, record.getId());

            // Ejecutar consulta
            int filas = statement.executeUpdate();

            // Verificar si se actualizo correctamente
            if (filas > 0) {
                System.out.println("Registro actualizado con exito");
            } else {
                System.out.println("No se pudo actualizar el registro");
            }

        } catch (SQLException e) {

            // Error SQL
            System.out.println("Error al actualizar registro");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {

        // Consulta SQL para eliminar un registro
        String sql = "DELETE FROM registros WHERE id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            // Asignar id
            statement.setInt(1, id);

            // Ejecutar consulta
            int filas = statement.executeUpdate();

            // Verificar si se elimino correctamente
            if (filas > 0) {
                System.out.println("Registro eliminado con exito");
            } else {
                System.out.println("No se pudo eliminar el registro");
            }

        } catch (SQLException e) {

            // Error SQL
            System.out.println("Error al eliminar registro");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    @Override
    public List<Record> list() {

        // Lista donde se guardaran los registros
        List<Record> records = new ArrayList<>();

        // Consulta SQL para listar todos los registros
        String sql = "SELECT * FROM registros";

        try (Statement statement =
                     connection.createStatement()) {

            // Ejecutar consulta
            ResultSet resultSet = statement.executeQuery(sql);

            // Recorrer resultados
            while (resultSet.next()) {

                // Validar si la hora de salida es null
                LocalDateTime salida =
                        resultSet.getTimestamp("hora_salida") != null
                                ? resultSet.getTimestamp("hora_salida")
                                  .toLocalDateTime()
                                : null;

                // Agregar registro a la lista
                records.add(
                        new Record(
                                resultSet.getInt("id"),
                                resultSet.getTimestamp("hora_entrada")
                                        .toLocalDateTime(),
                                salida,
                                resultSet.getInt("vehiculo_id"),
                                resultSet.getInt("parqueadero_id")
                        )
                );
            }

        } catch (SQLException e) {

            // Error SQL
            System.out.println("Error al listar registros");
            System.out.println("Detalles: " + e.getMessage());
        }

        return records;
    }

    // Verificar si un vehiculo esta dentro del parqueadero
    public boolean vehicleInside(int vehicleId) {

        // Buscar registros sin hora de salida
        String sql = """
                SELECT * FROM registros
                WHERE vehiculo_id = ?
                AND hora_salida IS NULL
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            // Asignar id del vehiculo
            statement.setInt(1, vehicleId);

            // Ejecutar consulta
            ResultSet resultSet = statement.executeQuery();

            // Si existe un registro abierto retorna true
            return resultSet.next();

        } catch (SQLException e) {

            // Error SQL
            System.out.println("Error verificando vehiculo");
            System.out.println("Detalles: " + e.getMessage());
        }

        return false;
    }

    // Registrar salida de un vehiculo
    public void registerExit(int vehicleId) {

        // Actualizar hora de salida
        String sql = """
                UPDATE registros
                SET hora_salida = ?
                WHERE vehiculo_id = ?
                AND hora_salida IS NULL
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            // Asignar hora actual
            statement.setTimestamp(
                    1,
                    Timestamp.valueOf(LocalDateTime.now())
            );

            // Asignar id del vehiculo
            statement.setInt(2, vehicleId);

            // Ejecutar consulta
            int filas = statement.executeUpdate();

            // Verificar si se actualizo correctamente
            if (filas > 0) {
                System.out.println("Salida registrada con exito");
            } else {
                System.out.println("El vehiculo no estaba dentro");
            }

        } catch (SQLException e) {

            // Error SQL
            System.out.println("Error registrando salida");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    // Contar cuantos vehiculos hay dentro del parqueadero
    public int vehiclesInside() {

        // Contar registros sin hora de salida
        String sql = """
                SELECT COUNT(*) AS total
                FROM registros
                WHERE hora_salida IS NULL
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            // Ejecutar consulta
            ResultSet resultSet = statement.executeQuery();

            // Retornar cantidad de vehiculos
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (SQLException e) {

            // Error SQL
            System.out.println("Error contando vehiculos");
            System.out.println("Detalles: " + e.getMessage());
        }

        return 0;
    }
}