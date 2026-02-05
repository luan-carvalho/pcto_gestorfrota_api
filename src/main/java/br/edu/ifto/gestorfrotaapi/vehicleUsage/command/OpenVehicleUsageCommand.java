package br.edu.ifto.gestorfrotaapi.vehicleUsage.command;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.VehicleRequest;

public record OpenVehicleUsageCommand(VehicleRequest request, User driver, Vehicle vehicle) {

}
