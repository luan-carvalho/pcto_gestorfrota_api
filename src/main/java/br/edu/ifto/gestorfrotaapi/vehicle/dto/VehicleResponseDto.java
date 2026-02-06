package br.edu.ifto.gestorfrotaapi.vehicle.dto;

public record VehicleResponseDto(
                Long id,
                String make,
                String model,
                String licensePlate,
                String status,
                Integer currentMileage) {

}
