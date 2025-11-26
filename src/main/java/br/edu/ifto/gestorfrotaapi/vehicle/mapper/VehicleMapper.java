package br.edu.ifto.gestorfrotaapi.vehicle.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleCreationRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.model.VehicleStatus;
import br.edu.ifto.gestorfrotaapi.vehicle.model.VehicleType;

@Component
public class VehicleMapper {

    public Vehicle toEntity(VehicleCreationRequestDto dto) {

        return new Vehicle(
                dto.model(),
                dto.make(),
                dto.licensePlate(),
                VehicleType.valueOf(dto.type()),
                dto.capacity(),
                dto.mileage(),
                VehicleStatus.valueOf(dto.status()));

    }

    public VehicleResponseDto toResponseDto(Vehicle vehicle) {

        return new VehicleResponseDto(
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getLicensePlate(),
                vehicle.getType().getDescription(),
                vehicle.getStatus().getDescription(),
                vehicle.getCapacity(),
                vehicle.getMileage());

    }

    public List<VehicleResponseDto> toResponseDto(List<Vehicle> vehicles) {

        return vehicles.stream().map(this::toResponseDto).toList();

    }

}
