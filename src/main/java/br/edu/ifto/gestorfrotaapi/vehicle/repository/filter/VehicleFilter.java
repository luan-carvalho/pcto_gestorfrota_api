package br.edu.ifto.gestorfrotaapi.vehicle.repository.filter;

import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.VehicleStatus;

public record VehicleFilter(
        Long id,
        String description,
        VehicleStatus status) {

}
