package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;

public record UserVehicleUsageFilter(

        Long requestId,

        String vehicleDescription,
        String requesterName,

        VehicleUsageStatus status,

        LocalDateTime usageFrom,
        LocalDateTime usageTo) {

}
