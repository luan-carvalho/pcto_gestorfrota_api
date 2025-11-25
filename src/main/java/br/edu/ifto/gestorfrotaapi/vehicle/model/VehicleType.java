package br.edu.ifto.gestorfrotaapi.vehicle.model;

public enum VehicleType {

    PICKUP("Caminhonete"),
    SUV("SUV"),
    SEDAN("Sedan"),
    HATCH("Hatch"),
    MOTORCYCLE("Moto");

    private String description;

    private VehicleType(String description) {

        this.description = description;

    }

    public String getDescription() {

        return this.description;

    }

}
