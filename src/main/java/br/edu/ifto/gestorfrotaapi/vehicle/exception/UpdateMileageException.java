package br.edu.ifto.gestorfrotaapi.vehicle.exception;

public class UpdateMileageException extends RuntimeException {

    String msg;

    public UpdateMileageException(String msg) {

        super(msg);

    }

}
