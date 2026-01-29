package br.edu.ifto.gestorfrotaapi.vehicleUsage.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleUsageResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;

@Mapper(componentModel = "spring")
public interface VehicleUsageMapper {
    
    @Mapping(source = "requester.name", target = "requesterName")
    VehicleRequestResponseDto toRequestResponseDto(VehicleRequest request);

    @Mapping(source = "requester.name", target = "requesterName")
    List<VehicleRequestResponseDto> toRequestResponseDto(List<VehicleRequest> request);

    @Mapping(source = "driver.name", target = "driverName")
    @Mapping(source = "request.id", target = "requestId")
    VehicleUsageResponseDto toUsageResponseDto(VehicleUsage request);
    
    @Mapping(source = "driver.name", target = "driverName")
    @Mapping(source = "request.id", target = "requestId")
    List<VehicleUsageResponseDto> toUsageResponseDto(List<VehicleUsage> request);

    VehicleResponseDto map(Vehicle vehicle);

}
