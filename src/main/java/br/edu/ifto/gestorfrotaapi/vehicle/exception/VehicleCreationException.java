package br.edu.ifto.gestorfrotaapi.vehicle.exception;

public class VehicleCreationException extends RuntimeException {

    String msg;

    public VehicleCreationException(String msg) {

        super(msg);

    }

}
