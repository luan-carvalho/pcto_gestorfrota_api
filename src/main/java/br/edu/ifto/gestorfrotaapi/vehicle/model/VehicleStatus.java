package br.edu.ifto.gestorfrotaapi.vehicle.model;

public enum VehicleStatus {

    AVAILABLE("Disponível"),
    RESERVED("Reservado"),
    IN_USE("Em uso"),
    MAINTANCE("Em manutenção"),
    DECOMISSIONED("Inativado");

    private String description;

    private VehicleStatus(String description) {

        this.description = description;

    }

    public String getDescription() {

        return this.description;

    }

}
