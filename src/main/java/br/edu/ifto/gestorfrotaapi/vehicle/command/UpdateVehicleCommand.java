package br.edu.ifto.gestorfrotaapi.vehicle.command;

import br.edu.ifto.gestorfrotaapi.vehicle.model.valueObjects.LicensePlate;

public record UpdateVehicleCommand(
                Long id,
                String make,
                String model,
                LicensePlate licensePlate) {

}
