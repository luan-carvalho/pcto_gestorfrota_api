package br.edu.ifto.gestorfrotaapi.vehicle.command;

import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.VehicleType;

public record UpdateVehicleCommand(String make,
        String model,
        String licensePlate,
        Integer capacity,
        VehicleType type) {

}
