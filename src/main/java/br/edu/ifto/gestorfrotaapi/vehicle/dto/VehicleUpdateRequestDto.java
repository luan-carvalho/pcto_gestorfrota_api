package br.edu.ifto.gestorfrotaapi.vehicle.dto;

import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.VehicleType;

public record VehicleUpdateRequestDto(
        String make,
        String model,
        String licensePlate,
        VehicleType type,
        Integer capacity

) {

}
