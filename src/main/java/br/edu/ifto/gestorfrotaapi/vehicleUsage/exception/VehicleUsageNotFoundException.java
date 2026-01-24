package br.edu.ifto.gestorfrotaapi.vehicleUsage.exception;

public class VehicleUsageNotFoundException extends RuntimeException {

    private Long id;

    public VehicleUsageNotFoundException(Long id) {

        super("The vehicle usage with id " + id + " was not found");
        this.id = id;

    }

    public Long getId() {
        return id;
    }

}
