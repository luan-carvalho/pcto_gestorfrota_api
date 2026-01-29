package br.edu.ifto.gestorfrotaapi.vehicleUsage.command;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleRequestPurpose;

public record OpenVehicleRequestCommand(

        Vehicle vehicle,
        RequestPriority priority,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        VehicleRequestPurpose purpose,
        String processNumber) {

}
