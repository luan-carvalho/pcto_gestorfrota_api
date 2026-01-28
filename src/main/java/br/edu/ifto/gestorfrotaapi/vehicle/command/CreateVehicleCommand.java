package br.edu.ifto.gestorfrotaapi.vehicle.command;

import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.VehicleType;

public record CreateVehicleCommand(
                String model,
                String make,
                String licensePlate,
                VehicleType type,
                Integer capacity,
                Integer currentMileage) {

}
