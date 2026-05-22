
package main.main.java.DAO;
import java.time.LocalDateTime;
import main.main.java.model.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDAOimpl implements CRUDL<Record> {

    private final Connection connection;

    public RecordDAOimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Record record) {

        String sql = """
                INSERT INTO registros
                (hora_entrada, hora_salida,
                 vehiculo_id, parqueadero_id)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setTimestamp(1,
                    record.getEntryTime() != null
                            ? Timestamp.valueOf(record.getEntryTime())
                            : null
            );

            statement.setTimestamp(2,
                    record.getDepartureTime() != null
                            ? Timestamp.valueOf(record.getDepartureTime())
                            : null
            );

            statement.setInt(3, record.getVehicleId());
            statement.setInt(4, record.getParkingId());

            int filas = statement.executeUpdate();

            if (filas > 0) {
                System.out.println("Registro creado con éxito");
            } else {
                System.out.println("No se pudo crear el registro");
            }

        } catch (SQLException e) {

            System.out.println("¡Error al crear registro!");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    @Override
    public Record read(int id) {

        String sql = "SELECT * FROM registros WHERE id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                LocalDateTime salida = resultSet.getTimestamp("hora_salida") != null
                        ? resultSet.getTimestamp("hora_salida").toLocalDateTime()
                        : null;

                return new Record(
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("hora_entrada").toLocalDateTime(),
                        salida,
                        resultSet.getInt("vehiculo_id"),
                        resultSet.getInt("parqueadero_id")
                );
            }

        } catch (SQLException e) {

            System.out.println("¡Error al leer registro!");
            System.out.println("Detalles: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void update(Record record) {

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

            statement.setTimestamp(1,
                    record.getEntryTime() != null
                            ? Timestamp.valueOf(record.getEntryTime())
                            : null
            );

            statement.setTimestamp(2,
                    record.getDepartureTime() != null
                            ? Timestamp.valueOf(record.getDepartureTime())
                            : null
            );

            statement.setInt(3, record.getVehicleId());
            statement.setInt(4, record.getParkingId());
            statement.setInt(5, record.getId());

            int filas = statement.executeUpdate();

            if (filas > 0) {
                System.out.println("Registro actualizado con éxito");
            } else {
                System.out.println("No se pudo actualizar el registro");
            }

        } catch (SQLException e) {

            System.out.println("¡Error al actualizar registro!");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM registros WHERE id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int filas = statement.executeUpdate();

            if (filas > 0) {
                System.out.println("Registro eliminado con éxito");
            } else {
                System.out.println("No se pudo eliminar el registro");
            }

        } catch (SQLException e) {

            System.out.println("¡Error al eliminar registro!");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    @Override
    public List<Record> list() {

        List<Record> records = new ArrayList<>();

        String sql = "SELECT * FROM registros";

        try (Statement statement =
                     connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                LocalDateTime salida = resultSet.getTimestamp("hora_salida") != null
                        ? resultSet.getTimestamp("hora_salida").toLocalDateTime()
                        : null;

                records.add(
                        new Record(
                                resultSet.getInt("id"),
                                resultSet.getTimestamp("hora_entrada").toLocalDateTime(),
                                salida,
                                resultSet.getInt("vehiculo_id"),
                                resultSet.getInt("parqueadero_id")
                        )
                );
            }

        } catch (SQLException e) {

            System.out.println("¡Error al listar registros!");
            System.out.println("Detalles: " + e.getMessage());
        }

        return records;
    }
}