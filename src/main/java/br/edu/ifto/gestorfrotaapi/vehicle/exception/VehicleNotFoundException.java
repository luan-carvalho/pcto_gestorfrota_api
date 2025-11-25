package br.edu.ifto.gestorfrotaapi.vehicle.exception;

public class VehicleNotFoundException extends RuntimeException {

    private final Long id;

    public VehicleNotFoundException(Long id) {

        super("Vehicle with id " + id + " was not found");
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
