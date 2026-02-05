package br.edu.ifto.gestorfrotaapi.vehicleRequest.exception;

public class VehicleRequestCreationException extends RuntimeException {

        String msg;

    public VehicleRequestCreationException(String msg) {

        super(msg);

    }
    
}
