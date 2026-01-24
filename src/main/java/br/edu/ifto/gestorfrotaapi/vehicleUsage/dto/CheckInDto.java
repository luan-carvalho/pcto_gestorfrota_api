package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

import jakarta.validation.constraints.NotNull;

public record CheckInDto(@NotNull Integer currentMileage) {

}
