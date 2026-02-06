package br.edu.ifto.gestorfrotaapi.vehicleRequest.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.VehicleRequestPurpose;

public record VehicleRequestResponseDto(
        Long id,
        String requesterName,
        String description,
        String city,
        String state,
        Double latitude,
        Double longitude,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        RequestStatus status,
        RequestPriority priority,
        VehicleRequestPurpose purpose,
        List<VehicleRequestHistoryDto> history

) {

}
