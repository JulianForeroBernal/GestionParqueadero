package main.main.java.DAO;

//imports
import main.main.java.enums.TypeVehicle;
import main.main.java.model.Vehicle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOimpl implements CRUDL<Vehicle> { // la case implementa la interfaz CRUDL con el tipo de dato Vehicle (es lo que permite la etiqueda <T> de la interfaz, el generico)
    private final Connection connection; // el atributo de nombre connection es el que nos permitirá establecer conexion y comunicacion con el abase de datos es de tipo Connection (una clase propia de fava de la libreira java.sql) que conteiene los metodos que nos permiten trabajar con la base de datos, es privado porque solo se va a usar dentro de esta clase, y es final porque no se va a modificar después de ser inicializado en el constructor

    public VehicleDAOimpl(Connection connection) {
        this.connection = connection; // se le asigna al atributo el valor del parámetro que se va a recibir en el constructor, el cual es un objeto de tipo Connection que se obtiene al llamar al metodo getConnection() de la clase ConnectionDB (que es la clase que se encarga de establecer la conexion con la base de datos)
    }

    @Override
    public void create(Vehicle vehicle) { //el metodo create recibe un objeto de tipo Vehicle (que es el tipo de dato que se va a usar en esta clase) y se encarga de insertar ese objeto en la base de datos, es decir, crear un nuevo registro en la tabla "vehiculos" con los datos del objeto Vehicle recibido como parámetro
        String sql = "INSERT INTO vehiculos (codigo_barras, tipo, placa, usuario_id) VALUES (?, ?, ?, ?)"; // sql es una variable qeu contiene unicamente un texto con una cofiguracion particular: values() -> (?) "placeholders"
        try (PreparedStatement statement = connection.prepareStatement(sql)){ //el texto de la variable sql lo recibe el objeto statement de la clase PreparedStatement (objeto creado por el metodo prepareStatement() de la clase Connection, gracias a nuestro objeto connection, creado en ConnectionBD y que tiene la comunicacion con la base de datos))
        //este try es un try-with-resources es decir lo que hay dentro del paréntesis (la declaración del recurso statement (el objeto que está usando recursos del PC para comunicarse con la base de datso y hacer una consulta a la BDs mysql)) debe ser cerrado, finalizado para no consumir más recurso en su comunicacion, al estar dentro del parentesis del try se cierra automaticamente al finalizar el bloque del try
            statement.setInt(1, vehicle.getBarCode()); //el objeto statement ahora tiene el texto de la variable sql, que ahora es una consulta en preparacion, gracias a esto se pueden cambiar los valores de los placeholders (?) por valores obtenidos, en este caso el objeto vehicel
            statement.setString(2, vehicle.getType().name()); //el metod.name() del enum TypeVehicle devuelve el nombre del valor como String
            statement.setString(3, vehicle.getPlate());// satament.setTIPo() es el metodo qeu permite asociar los place holder, recibe en su primer parametro el lugar del placeholder y en su segundo el valor qeu va a poner
            statement.setInt(4, vehicle.getIdUser()); //como vehicle tiene en uno de sus atributos a un user de la clase User, puede accerder los atributos de dicho usuario
            int filas = statement.executeUpdate();// statement.executeUpdate() ejecuta la consulta ya preparada en mysql, el cual además devuelve como entero la cantidad de filas qeu fueron modificadas
            if(filas > 0){ //validamos que si hubo al menos un registrado nuevo
                System.out.println(
                        "Vehículo registrado exitosamente"
                );
            }else {
                System.out.println("No se pudo registrar el vehículo");
            }

        }catch (SQLException e){ // de ocurrir un error, este llegará como objeto tipo SQLException, y será asignado a la variablel e
            System.out.println("¡ERROR AL CREAR EL VEHICULO!\n");
            //manejo del error
            System.out.println("\nDetalles del error: " + e.getMessage());  //e.getMessage() devuelve un mensaje descriptivo del error que ocurrió, el cual se muestra al usuario para ayudar a entender qué salió mal
        }
    }

    @Override
    public Vehicle read(int barcode) {
        String sql = "SELECT * FROM vehiculos WHERE codigo_barras = ?"; // consulta sql para leer un registro de la tabla vehiculos, usando el codigo de barras como identificador
        try (PreparedStatement statement = connection.prepareStatement(sql)) { //preparacion para consulta sql
            statement.setInt(1, barcode); //asociamos el placeholder de la consulta sql con el valor del codigo de barras recibido como parametro
            ResultSet resultSet = statement.executeQuery(); // resultSet se convierte en un objeto del tipo ResultSet (una interfaz(solo índica el tipo no es una instancia)) este objeto es creado por el metodo executeQuery() el cual retorna un objeto de la clase ResultSet que contiene el resultado de la consulta (algo similar a una matriz con filas y columnas) donde están los datos del registro que concide con el codigo de barras
            if(resultSet.next()) { // resulSet.next() es un metodo que mueve el cursor al siguiente registro del resultado (incialment está en la posicion antes del primer registro) y devuelve true si hay un registro disponible
                return new Vehicle( //creación del nuevo objeto vehículo apartir de los datos obtenidos de la consulta y guardados en resulSet
                        resultSet.getInt("id"), // resultSet.getTIPO("nombre_columna") duvuelve el dato de una columna específica del registro almacenado en resultSet
                        resultSet.getInt("codigo_barras"),
                        TypeVehicle.valueOf(resultSet.getString("tipo")), // como el constructo espera un TypeVehicle un objeto de tipo enum, hay qeu hacer una converssion, TypeVehicle.valueOf() es un metodo estatico de la clase TypeVehicle que recibe un String y devuelve el valor del enum que coincide con ese String, en este caso el valor de la columna "tipo" del registro obtenido en resultSet
                        resultSet.getString("placa"),
                        resultSet.getInt("id_usuario")
                );
            }
        }catch (SQLException e){
            System.out.println("¡ERROR AL LEER EL VEHICULO!\n");
            //manejo del error
            System.out.println("\nDetalles del error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Vehicle vehicle) {
        String sql = "UPDATE vehiculos SET codigo_barras = ?, tipo = ?, plava = ?, id_usuario = ? WHERE placa =?"; // consulta sql para actualizar un registro de la tabla vehiculos, usando la placa como identificador
        try (PreparedStatement statement = connection.prepareStatement(sql)) { //preparacion para consulta
            statement.setInt(1, vehicle.getBarCode()); //asociamos el placeholder de la consulta sql con el valor del codigo de barras del objeto vehículo recibido como parametro
            statement.setString(2, vehicle.getType().name()); //asociamos el placeholder
            statement.setString(3, vehicle.getPlate());
            statement.setInt(4, vehicle.getIdUser());
            statement.setString(5, vehicle.getPlate()); //asociamos el placeholder del WHERE
            int filas = statement.executeUpdate(); //ejecucion de la consulta sql, devuelve la cantidad de filas modificadas
            if(filas > 0){ //validamos que si hubo al menos un registro modificado
                System.out.println("Vehículo actualizado exitosamente");
            }else {
                System.out.println("No se pudo actualizar el vehículo");

            }
        }catch (SQLException e){
            System.out.println("¡ERROR AL ACTUALIZAR EL VEHICULO!\n");
            //manejo del error
            System.out.println("\nDetalles del error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM vehiculos WHERE id = ?"; // consulta sql para eliminar un registro de la tabla vehiculos, usando id como identificador
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id); //asociamos el placeholder de la consulta sql con el valor id recibido como parametro
            statement.executeUpdate(); //ejecucion de la consulta sql
        }catch (SQLException e){
            System.out.println("¡ERROR AL ELIMINAR EL VEHICULO!\n");
            //manejo del error
            System.out.println("\nDetalles del error: " + e.getMessage());
        }
    }

    @Override
    public List<Vehicle> list() {
        List<Vehicle> vehicles = new ArrayList<>(); //creacion de una lista vacia de vehículos, esta lista se va a llenar con los objetos vehículo obtenidos de la consulta sql
        String sql = "SELECT * FROM vehiculos"; // consulta sql para obtener todos los registros
        try (Statement statement = connection.createStatement()) { // ...
            ResultSet resultSet = statement.executeQuery(sql); // guarda los datos obtenidos de la consulta
            while (resultSet.next()) { // resultSet devuelve true cuando hay un registro en la fila donde el puntero esta pocisionado -> mientras haya registros creará un objeto vehículo nuevo con los datos de cada columna de la "matriz" guardada en resultSet
                vehicles.add(new Vehicle( //vehículos
                        resultSet.getInt("id"),
                        resultSet.getInt("codigo_barras"),
                        TypeVehicle.valueOf(resultSet.getString("tipo")),
                        resultSet.getString("placa"),
                        resultSet.getInt("id_usuario")
                ));
            }
        }catch (SQLException e){
            System.out.println("¡ERROR AL LISTAR LOS VEHICULOS!\n");
            //manejo del error
            System.out.println("\nDetalles del error: " + e.getMessage());

        }
        return vehicles; //devolvemos la lista de vehículos obtenida de la consulta sql
    }
}
