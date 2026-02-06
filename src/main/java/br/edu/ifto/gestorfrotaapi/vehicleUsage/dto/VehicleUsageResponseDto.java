package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;

public record VehicleUsageResponseDto(
                Long id,
                Long requestId,
                LocalDateTime usageStart,
                LocalDateTime usageEnd,
                LocalDateTime checkInAt,
                LocalDateTime checkOutAt,
                VehicleUsageStatus status,
                String driverName,
                VehicleResponseDto vehicle

) {

}
