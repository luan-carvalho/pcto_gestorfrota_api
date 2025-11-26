package br.edu.ifto.gestorfrotaapi.vehicle.dto;

public record VehicleResponseDto(
        String make,
        String model,
        String licensePlate,
        String type,
        String status,
        Integer capacity,
        Integer mileage) {

}
