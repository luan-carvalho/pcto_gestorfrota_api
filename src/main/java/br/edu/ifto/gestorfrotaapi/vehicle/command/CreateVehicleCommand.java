package br.edu.ifto.gestorfrotaapi.vehicle.command;

import br.edu.ifto.gestorfrotaapi.vehicle.model.valueObjects.LicensePlate;

public record CreateVehicleCommand(
        String model,
        String make,
        LicensePlate licensePlate,
        Integer currentMileage) {

}
