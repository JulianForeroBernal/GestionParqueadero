package main.main.java.model;

import main.main.java.enums.TypeVehicle;

public class Parking {
    private int id;
    private TypeVehicle zone;
    private int ability;
    private int occupied;

    public Parking(){

    }

    public Parking(int ability, int id, int occupied, TypeVehicle zone) {
        this.ability = ability;
        this.id = id;
        this.occupied = occupied;
        this.zone = zone;
    }

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public TypeVehicle getZone() {
        return zone;
    }

    public void setZone(TypeVehicle zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "Parking{" +
                "ability=" + ability +
                ", id=" + id +
                ", zone=" + zone +
                ", occupied=" + occupied +
                '}';
    }
}
