package br.edu.ifto.gestorfrotaapi.vehicle.dto;

import br.edu.ifto.gestorfrotaapi.vehicle.command.CreateVehicleCommand;
import br.edu.ifto.gestorfrotaapi.vehicle.model.valueObjects.LicensePlate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleCreationRequestDto(
                @NotBlank String make,
                @NotBlank String model,
                @NotBlank String licensePlate,
                @NotNull Integer currentMileage) {

        public CreateVehicleCommand toCommand() {

                return new CreateVehicleCommand(
                                make,
                                model,
                                new LicensePlate(licensePlate),
                                currentMileage);

        }

}
