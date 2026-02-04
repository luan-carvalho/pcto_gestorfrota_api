package br.edu.ifto.gestorfrotaapi.vehicleUsage.exception;

public class VehicleRequestApprovalException extends RuntimeException {

    String msg;

    public VehicleRequestApprovalException(String msg) {

        super(msg);

    }

}
