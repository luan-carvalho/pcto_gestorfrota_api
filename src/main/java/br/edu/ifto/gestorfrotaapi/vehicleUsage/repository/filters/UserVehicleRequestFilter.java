package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleRequestPurpose;

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
