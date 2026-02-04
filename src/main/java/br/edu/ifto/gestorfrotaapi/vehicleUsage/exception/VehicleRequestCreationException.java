package br.edu.ifto.gestorfrotaapi.vehicleUsage.exception;

public class VehicleRequestCreationException extends RuntimeException {

        String msg;

    public VehicleRequestCreationException(String msg) {

        super(msg);

    }
    
}
