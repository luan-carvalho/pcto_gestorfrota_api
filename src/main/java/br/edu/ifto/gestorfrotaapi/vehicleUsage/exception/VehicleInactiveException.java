package br.edu.ifto.gestorfrotaapi.vehicleUsage.exception;

import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;

public class VehicleInactiveException extends RuntimeException {

    private Vehicle vehicle;

    public VehicleInactiveException(Vehicle v) {

        super("Vehicle " + v.toString() + " is not active");

    }

    public Vehicle getVehicle() {
        return vehicle;
    }

}
