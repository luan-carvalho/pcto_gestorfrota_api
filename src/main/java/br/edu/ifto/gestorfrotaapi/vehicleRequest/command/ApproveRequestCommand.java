package br.edu.ifto.gestorfrotaapi.vehicleRequest.command;

import jakarta.validation.constraints.NotNull;

public record ApproveRequestCommand(
        @NotNull Long requestId,
        @NotNull Long driverId,
        @NotNull Long vehicleId,
        String notes) {

}
