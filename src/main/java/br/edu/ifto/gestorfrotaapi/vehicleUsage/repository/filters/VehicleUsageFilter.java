package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;

public record VehicleUsageFilter(

                Long requestId,

                String vehicleDescription,
                String driverName,
                String requesterName,

                LocalDateTime checkInFrom,
                LocalDateTime checkInTo,

                LocalDateTime checkOutFrom,
                LocalDateTime checkOutTo,

                VehicleUsageStatus status) {
}
