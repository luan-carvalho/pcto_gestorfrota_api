package br.edu.ifto.gestorfrotaapi.vehicleRequest.dto;

import br.edu.ifto.gestorfrotaapi.vehicleRequest.command.ApproveRequestCommand;
import jakarta.validation.constraints.NotNull;

public record VehicleRequestApprovalDto(
                @NotNull Long driverId,
                @NotNull Long vehicleId,
                String notes

) {

        public ApproveRequestCommand toCommand(Long requestId) {

                return new ApproveRequestCommand(
                                requestId,
                                driverId,
                                vehicleId,
                                notes);
        }

}
