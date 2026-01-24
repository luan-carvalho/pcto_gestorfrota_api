package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

public record CheckInDto(Long vehicleUsageId, String driverRegistration, Integer currentMileage) {

}
