package br.edu.ifto.gestorfrotaapi.vehicleRequest.dto;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicleRequest.command.OpenVehicleRequestCommand;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.VehicleRequestPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequestCreateRequestDto(

                @NotNull String description,
                @NotNull RequestPriority priority,
                @NotNull LocalDateTime startDateTime,
                @NotNull LocalDateTime endDateTime,
                @NotNull VehicleRequestPurpose purpose,
                @NotBlank String processNumber,
                @NotBlank String address) {

        public OpenVehicleRequestCommand toOpenRequestCommand() {

                return new OpenVehicleRequestCommand(
                                description,
                                priority,
                                startDateTime,
                                endDateTime,
                                purpose,
                                processNumber,
                                address);

        }

}
