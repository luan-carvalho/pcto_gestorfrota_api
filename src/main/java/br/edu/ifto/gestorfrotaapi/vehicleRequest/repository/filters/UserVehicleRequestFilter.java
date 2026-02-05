package br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.filters;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.VehicleRequestPurpose;

public record UserVehicleRequestFilter(

        Long requestId,

        String vehicleDescription,

        RequestStatus status,
        RequestPriority priority,
        VehicleRequestPurpose purpose,

        String processNumber,

        LocalDateTime usageFrom,
        LocalDateTime usageTo) {

}
