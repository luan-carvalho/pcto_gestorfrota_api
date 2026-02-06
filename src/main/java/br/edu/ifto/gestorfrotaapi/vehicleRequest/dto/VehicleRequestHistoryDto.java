package br.edu.ifto.gestorfrotaapi.vehicleRequest.dto;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.RequestAction;

public record VehicleRequestHistoryDto(
        RequestAction action,
        String performedBy,
        LocalDateTime performedAt,
        String notes

) {

}
