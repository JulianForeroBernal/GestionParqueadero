package main.main.java.model;

import main.main.java.enums.TypeVehicle;

public class Record {
    private int id;
    private String entry_time;
    private String departure_time;
    private int vehicle_id;
    private int parking_id;
public Record(){

}
public Record(int id,String entry_time,String departure_time,int vehicle_id,int parking_id){
    this.id=id;
    this.entry_time=entry_time;
    this.departure_time=departure_time;
    this.vehicle_id=vehicle_id;
    this.parking_id=parking_id;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getParking_id() {
        return parking_id;
    }

    public void setParking_id(int parking_id) {
        this.parking_id = parking_id;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", entry_time='" + entry_time + '\'' +
                ", departure_time='" + departure_time + '\'' +
                ", vehicle_id=" + vehicle_id +
                ", parking_id=" + parking_id +
                '}';
    }
}
