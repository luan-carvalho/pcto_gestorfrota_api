package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleRequestPurpose;

public record VehicleRequestResponseDto(
        Long id,
        String requesterName,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        RequestStatus status,
        Vehicle vehicle,
        RequestPriority priority,
        VehicleRequestPurpose purpose

) {

}
