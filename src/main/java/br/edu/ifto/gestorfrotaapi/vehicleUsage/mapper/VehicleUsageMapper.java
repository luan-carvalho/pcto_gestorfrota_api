package br.edu.ifto.gestorfrotaapi.vehicleUsage.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.model.valueObjects.LicensePlate;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleUsageResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;

@Mapper(componentModel = "spring")
public interface VehicleUsageMapper {

    @Mapping(source = "driver.name", target = "driverName")
    @Mapping(source = "request.id", target = "requestId")
    @Mapping(source = "request.period.startDateTime", target = "usageStart")
    @Mapping(source = "request.period.endDateTime", target = "usageEnd")
    VehicleUsageResponseDto toUsageResponseDto(VehicleUsage request);

    @Mapping(source = "driver.name", target = "driverName")
    @Mapping(source = "request.id", target = "requestId")
    List<VehicleUsageResponseDto> toUsageResponseDto(List<VehicleUsage> request);

    VehicleResponseDto mapToVehicleResponseDto(Vehicle vehicle);

    default String mapLicensePlate(LicensePlate licensePlate) {

        return licensePlate.toString();

    }

}
