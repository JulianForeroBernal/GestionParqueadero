package main.main.java.model;

import main.main.java.enums.TypeVehicle;

import java.time.LocalDateTime;

public class Record {
    private int id;
    private LocalDateTime entry_time;
    private LocalDateTime departure_time;
    private int vehicle_id;
    private int parking_id;
public Record(){

}
public Record(int id,LocalDateTime entry_time,LocalDateTime departure_time,int vehicle_id,int parking_id){
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

    public LocalDateTime getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(LocalDateTime entry_time) {
        this.entry_time = entry_time;
    }

    public LocalDateTime getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(LocalDateTime departure_time) {
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
