package br.edu.ifto.gestorfrotaapi.vehicle.dto;

public record VehicleReponseDto(
        String make,
        String model,
        String licensePlate,
        String type,
        String status,
        Integer capacity,
        Integer kilometers) {

}
