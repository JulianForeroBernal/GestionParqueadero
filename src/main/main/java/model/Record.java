package main.main.java.model;

import main.main.java.enums.TypeVehicle;

import java.time.LocalDateTime;

public class Record {
    private int id;
    private LocalDateTime entryTime;
    private LocalDateTime departureTime;
    private int vehicleId;
    private int parkingId;
    public Record(){

    }
    public Record(int id, LocalDateTime entryTime, LocalDateTime departureTime, int vehicleId, int parkingId){
        this.id = id;
        this.entryTime = entryTime;
        this.departureTime = departureTime;
        this.vehicleId = vehicleId;
        this.parkingId = parkingId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getParkingId() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", entryTime='" + entryTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", vehicleId=" + vehicleId +
                ", parkingId=" + parkingId +
                '}';
    }
}
