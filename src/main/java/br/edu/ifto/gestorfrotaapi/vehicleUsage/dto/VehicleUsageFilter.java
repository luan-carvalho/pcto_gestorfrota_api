package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;

public record VehicleUsageFilter(

        Long requestId,
        Long vehicleId,
        Long driverId,

        LocalDateTime checkInFrom,
        LocalDateTime checkInTo,

        LocalDateTime checkOutFrom,
        LocalDateTime checkOutTo,

        Integer mileageStartMin,
        Integer mileageEndMax,

        VehicleUsageStatus status) {
}
