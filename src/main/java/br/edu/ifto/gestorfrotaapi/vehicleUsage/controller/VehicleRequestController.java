package br.edu.ifto.gestorfrotaapi.vehicleUsage.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.command.OpenVehicleRequestCommand;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestApprovalDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestCreateRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.mapper.VehicleUsageMapper;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters.UserVehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters.VehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.service.VehicleRequestService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("request")
public class VehicleRequestController {

    private final VehicleRequestService service;
    private final VehicleUsageMapper mapper;

    public VehicleRequestController(VehicleRequestService service, VehicleUsageMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<VehicleRequestResponseDto> getRequests(VehicleRequestFilter filter, Pageable pageable) {

        return service.searchForVehicleRequest(filter, pageable);

    }

    @GetMapping("/my-requests")
    public Page<VehicleRequestResponseDto> getUserRequests(UserVehicleRequestFilter filter, Pageable pageable) {

        return service.getUserRequests(filter, pageable);

    }

    @PostMapping
    public ResponseEntity<VehicleRequestResponseDto> openNewRequest(
            @RequestBody @Valid VehicleRequestCreateRequestDto dto) {

        VehicleRequest created = service.create(
                new OpenVehicleRequestCommand(
                        dto.requestedVehicleId(),
                        dto.priority(),
                        dto.startDateTime(),
                        dto.endDateTime(),
                        dto.purpose(),
                        dto.processNumber()));

        VehicleRequestResponseDto response = mapper.toRequestResponseDto(created);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);

    }

    @PatchMapping("/{requestId}/approve")
    public ResponseEntity<Void> approveRequest(@PathVariable Long requestId,
            @RequestBody @Valid VehicleRequestApprovalDto dto) {

        service.approve(requestId, dto.driverId(), dto.notes());

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/{requestId}/reject")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long requestId,
            @RequestBody String notes) {

        service.reject(requestId, notes);

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<Void> cancelRequest(@PathVariable Long requestId,
            @RequestBody String notes) {

        service.cancel(requestId, notes);

        return ResponseEntity.ok().build();

    }

}
