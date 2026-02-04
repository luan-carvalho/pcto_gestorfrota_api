package br.edu.ifto.gestorfrotaapi.vehicleUsage.exception;

public class CheckInException extends RuntimeException {

    String msg;

    public CheckInException(String msg) {

        super(msg);

    }

}
