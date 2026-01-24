package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

import jakarta.validation.constraints.NotNull;

public record CheckOutDto(@NotNull Integer endMileage, String notes) {

}
