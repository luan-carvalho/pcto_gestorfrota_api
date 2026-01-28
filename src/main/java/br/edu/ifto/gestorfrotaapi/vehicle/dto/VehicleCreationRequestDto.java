package br.edu.ifto.gestorfrotaapi.vehicle.dto;

import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VehicleCreationRequestDto(
        @NotBlank String make,
        @NotBlank String model,
        @NotBlank @Size(min = 7, max = 8) @Pattern(regexp = "^[A-Z]{3}-?(\\d{4}|\\d[A-Z0-9]\\d{2})$") String licensePlate,
        VehicleType type,
        Integer capacity,
        Integer currentMileage) {

}
