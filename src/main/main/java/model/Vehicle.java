package main.main.java.model;

import main.main.java.enums.TypeVehicle;

public class Vehicle {
    private int idVehicle;          // atributos necesarios
    private int barCode;
    private TypeVehicle type;       // viene de enums/TypeVehicle que es la enumeración "lista" "tipos" de vehículos que se pueden usar, ayuda a trabajar mejor que con strings
    private String plate;
    private int idUser;             // atributo que se va a usar para relacionar el vehículo con el usuario que lo registró, es un entero porque es él id del usuario en la base de datos

    public Vehicle() {
    }

    public Vehicle(int barCode, TypeVehicle type, String plate) {    //constructor parcial, util para el registro de vehiculo por parte del usuario
        this.barCode = barCode;
        this.type = type;
        this.plate = plate;
    }

    public Vehicle(int idVehicle, int barCode, TypeVehicle type, String plate, int idUser) {  //constructor completo, util para el trabajo con la BDs
        this.idVehicle = idVehicle;
        this.barCode = barCode;
        this.type = type;
        this.plate = plate;
        this.idUser = idUser;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public int getIdUser() {
        return this.idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public TypeVehicle getType() {
        return type;
    }

    public void setType(TypeVehicle type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "barCode='" + barCode + '\'' +
                ", idVehicle=" + idVehicle +
                ", plate='" + plate + '\'' +
                ", type=" + type +
                '}';
    }


}
