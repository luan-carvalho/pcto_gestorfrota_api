package br.edu.ifto.gestorfrotaapi.vehicleUsage.exception;

public class VehicleNotAvailableException extends RuntimeException {

    public VehicleNotAvailableException() {

        super("This vehicle is not available in the requested period.");

    }

}
