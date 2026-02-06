package br.edu.ifto.gestorfrotaapi.vehicleRequest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestHistoryDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.VehicleRequestHistory;

@Mapper(componentModel = "spring")
public interface VehicleRequestMapper {

    @Mapping(source = "period.startDateTime", target = "startDateTime")
    @Mapping(source = "period.endDateTime", target = "endDateTime")
    @Mapping(source = "requester.name", target = "requesterName")
    @Mapping(source = "destination.city", target = "city")
    @Mapping(source = "destination.state", target = "state")
    @Mapping(source = "destination.latitude", target = "latitude")
    @Mapping(source = "destination.longitude", target = "longitude")
    VehicleRequestResponseDto toRequestResponseDto(VehicleRequest request);

    @Mapping(source = "requester.name", target = "requesterName")
    List<VehicleRequestResponseDto> toRequestResponseDto(List<VehicleRequest> request);

    default VehicleRequestHistoryDto mapToRequestHistoryDto(VehicleRequestHistory history) {

        return new VehicleRequestHistoryDto(

                history.getAction(),
                history.getPerformedBy().getName(),
                history.getPerformedAt(),
                history.getNotes()

        );

    }

}
