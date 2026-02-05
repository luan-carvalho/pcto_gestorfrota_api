package br.edu.ifto.gestorfrotaapi.vehicleRequest.exception;

public class VehicleRequestNotFoundException extends RuntimeException {

    private Long id;

    public VehicleRequestNotFoundException(Long id) {

        super("The vehicle request with id " + id + " was not found");
        this.id = id;

    }

    public Long getId() {
        return id;
    }

}
