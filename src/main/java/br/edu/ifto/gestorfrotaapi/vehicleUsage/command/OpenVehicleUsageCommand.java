package br.edu.ifto.gestorfrotaapi.vehicleUsage.command;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;

public record OpenVehicleUsageCommand(VehicleRequest request, User driver) {

}
