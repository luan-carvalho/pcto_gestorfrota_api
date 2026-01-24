package br.edu.ifto.gestorfrotaapi.vehicleUsage.exception;

import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;

public class VehicleNotAvaliableException extends RuntimeException {

    private Vehicle vehicle;

    public VehicleNotAvaliableException(Vehicle v) {

        super("Vehicle " + v.toString() + "not avaliable. Current status: " + v.getStatus());

    }

    public Vehicle getVehicle() {
        return vehicle;
    }

}
