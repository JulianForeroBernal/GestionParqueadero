import main.main.java.DAO.RecordDAOimpl;
import main.main.java.model.Record;
import main.main.java.util.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TestRecord {

    public static void main(String[] args) {

        try (Connection connection = ConnectionDB.getConnection()) {

            RecordDAOimpl recordDAO = new RecordDAOimpl(connection);

            System.out.println("=== TEST CREATE RECORD ===");

            Record record = new Record(
                    0, // id (autoincrement en BD)
                    LocalDateTime.now(),   // entrada
                    null,                  // salida (puede ser null)
                    1,                    // vehiculo_id
                    1                     // parqueadero_id
            );

            recordDAO.create(record);

            System.out.println("Registro creado correctamente");

            System.out.println("\n=== TEST READ RECORD ===");
            Record result = recordDAO.read(1);
            System.out.println(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}