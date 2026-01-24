package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

import jakarta.validation.constraints.NotNull;

public record VehicleRequestApprovalDto(
        @NotNull Long driverId,
        String notes

) {

}
