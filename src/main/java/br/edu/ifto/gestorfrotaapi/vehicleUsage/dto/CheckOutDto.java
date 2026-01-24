package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

public record CheckOutDto(Long vehicleUsageId, String driverRegistration, Integer endMileage, String notes) {

}
