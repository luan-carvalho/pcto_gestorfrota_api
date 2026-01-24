package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;

public record VehicleUsageResponseDto(
        Long id,
        Long requestId,
        VehicleUsageStatus status,
        String driverName

) {

}
