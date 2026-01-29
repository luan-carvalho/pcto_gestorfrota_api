package br.edu.ifto.gestorfrotaapi.vehicleUsage.exception;

public class NotAssignedDriverException extends RuntimeException {

    public NotAssignedDriverException() {

        super("Only the assigned driver can check-in/check-out");

    }

}
