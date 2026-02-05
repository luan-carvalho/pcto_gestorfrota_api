package br.edu.ifto.gestorfrotaapi.vehicleRequest.exception;

public class VehicleRequestApprovalException extends RuntimeException {

    String msg;

    public VehicleRequestApprovalException(String msg) {

        super(msg);

    }

}
