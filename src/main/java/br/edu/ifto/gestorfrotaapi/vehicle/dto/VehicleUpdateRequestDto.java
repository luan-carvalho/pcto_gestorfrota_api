package br.edu.ifto.gestorfrotaapi.vehicle.dto;

import br.edu.ifto.gestorfrotaapi.vehicle.command.UpdateVehicleCommand;
import br.edu.ifto.gestorfrotaapi.vehicle.model.valueObjects.LicensePlate;

public record VehicleUpdateRequestDto(
                String make,
                String model,
                String licensePlate

) {

        public UpdateVehicleCommand toCommand(Long id) {

                return new UpdateVehicleCommand(
                                id,
                                make,
                                model,
                                licensePlate == null || licensePlate.isBlank() ? null : new LicensePlate(licensePlate));

        }

}
