package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequestCreateDto(
        @NotBlank String requesterRegistration,
        @NotNull Long requestedVehicleId,
        @NotBlank String priority,
        @NotNull LocalDateTime startDateTime,
        @NotNull LocalDateTime endDateTime,
        @NotBlank String purpose,
        @NotBlank String processNumber) {

}
