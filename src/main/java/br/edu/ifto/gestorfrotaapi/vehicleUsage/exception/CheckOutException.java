package br.edu.ifto.gestorfrotaapi.vehicleUsage.exception;

public class CheckOutException extends RuntimeException {

    String msg;

    public CheckOutException(String msg) {

        super(msg);

    }

}
