package br.edu.ifto.gestorfrotaapi.vehicleUsage.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.service.UserService;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.service.VehicleService;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.command.OpenVehicleRequestCommand;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.UserVehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestApprovalDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestCreateRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.mapper.VehicleUsageMapper;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.service.VehicleRequestService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("request")
public class VehicleRequestController {

    private final VehicleRequestService service;
    private final VehicleService vehicleService;
    private final UserService userService;
    private final VehicleUsageMapper mapper;

    public VehicleRequestController(VehicleRequestService service, VehicleService vehicleService,
            UserService userService, VehicleUsageMapper mapper) {
        this.service = service;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<VehicleRequestResponseDto> getRequests(VehicleRequestFilter filter, Pageable pageable) {

        return service.searchForVehicleRequest(filter, pageable).map(mapper::toRequestResponseDto);

    }

    @GetMapping("/my-requests")
    public Page<VehicleRequestResponseDto> getUserRequests(@AuthenticationPrincipal User user,
            UserVehicleRequestFilter filter, Pageable pageable) {

        return service.getUserRequests(filter, user, pageable).map(mapper::toRequestResponseDto);

    }

    @PostMapping
    public ResponseEntity<VehicleRequestResponseDto> openNewRequest(
            @RequestBody @Valid VehicleRequestCreateRequestDto dto) {

        Vehicle requestedVehicle = vehicleService.findById(dto.requestedVehicleId());

        VehicleRequest created = service.create(
                new OpenVehicleRequestCommand(
                        requestedVehicle,
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

        User assignedDriver = userService.findById(dto.driverId());

        service.approve(requestId, assignedDriver, dto.notes());

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
