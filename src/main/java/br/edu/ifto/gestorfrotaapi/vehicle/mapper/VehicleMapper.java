package br.edu.ifto.gestorfrotaapi.vehicle.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.model.valueObjects.LicensePlate;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    VehicleResponseDto toResponseDto(Vehicle vehicle);

    @Mapping(source = "status", target = "status")
    List<VehicleResponseDto> toResponseDto(List<Vehicle> vehicle);

    default String mapLicensePlate(LicensePlate licensePlate) {

        return licensePlate.toString();

    }

}
