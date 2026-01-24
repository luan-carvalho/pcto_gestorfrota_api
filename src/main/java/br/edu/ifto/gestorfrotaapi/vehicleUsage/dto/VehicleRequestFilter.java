package br.edu.ifto.gestorfrotaapi.vehicleUsage.dto;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleRequestPurpose;

public record VehicleRequestFilter(

                String requesterName,
                String vehicleLicensePlate,
                String vehicleMake,
                String vehicleModel,
                
                RequestStatus status,
                RequestPriority priority,
                VehicleRequestPurpose purpose,

                String processNumber,

                LocalDateTime createdFrom,
                LocalDateTime createdTo,

                LocalDateTime usageFrom,
                LocalDateTime usageTo) {
}
