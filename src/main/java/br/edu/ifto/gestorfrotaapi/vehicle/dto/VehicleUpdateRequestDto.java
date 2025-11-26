package br.edu.ifto.gestorfrotaapi.vehicle.dto;

public record VehicleUpdateRequestDto(
        String make,
        String model,
        String licensePlate,
        String type,
        Integer capacity

) {

}
