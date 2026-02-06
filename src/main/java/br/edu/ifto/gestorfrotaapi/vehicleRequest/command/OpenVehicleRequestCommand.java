package br.edu.ifto.gestorfrotaapi.vehicleRequest.command;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.VehicleRequestPurpose;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.valueObjects.Location;

public record OpenVehicleRequestCommand(

                String description,
                RequestPriority priority,
                LocalDateTime startDateTime,
                LocalDateTime endDateTime,
                VehicleRequestPurpose purpose,
                String processNumber,
                Location destination) {

}
