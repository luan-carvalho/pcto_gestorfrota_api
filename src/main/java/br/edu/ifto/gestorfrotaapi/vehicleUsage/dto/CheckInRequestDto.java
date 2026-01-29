package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

import jakarta.validation.constraints.NotNull;

public record CheckInRequestDto(@NotNull Integer currentMileage) {

}
