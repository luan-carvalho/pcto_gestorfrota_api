package br.edu.ifto.gestorfrotaapi.vehicle.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    
    VehicleResponseDto toResponseDto(Vehicle vehicle);
    List<VehicleResponseDto> toResponseDto(List<Vehicle> vehicle);

}
