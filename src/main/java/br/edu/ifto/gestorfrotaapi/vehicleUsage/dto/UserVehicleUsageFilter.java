package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

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
