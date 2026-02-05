package br.edu.ifto.gestorfrotaapi.vehicleRequest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.VehicleRequest;

@Mapper(componentModel = "spring")
public interface VehicleRequestMapper {

    @Mapping(source = "period.startDateTime", target = "startDateTime")
    @Mapping(source = "period.endDateTime", target = "endDateTime")
    @Mapping(source = "requester.name", target = "requesterName")
    VehicleRequestResponseDto toRequestResponseDto(VehicleRequest request);

    @Mapping(source = "requester.name", target = "requesterName")
    List<VehicleRequestResponseDto> toRequestResponseDto(List<VehicleRequest> request);

}
