package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleRequestPurpose;

public record VehicleRequestResponseDto(
                Long id,
                String requesterName,
                LocalDateTime startDateTime,
                LocalDateTime endDateTime,
                RequestStatus status,
                VehicleResponseDto vehicle,
                RequestPriority priority,
                VehicleRequestPurpose purpose

) {

}
