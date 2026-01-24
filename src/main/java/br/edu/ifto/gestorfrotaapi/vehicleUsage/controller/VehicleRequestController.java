package br.edu.ifto.gestorfrotaapi.vehicleUsage.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.UserVehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestApprovalDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestCreateDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.mapper.VehicleUsageMapper;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.service.VehicleUsageFacade;
import jakarta.validation.Valid;

@RestController
@RequestMapping("request")
public class VehicleRequestController {

    private final VehicleUsageFacade facade;
    private final VehicleUsageMapper mapper;

    public VehicleRequestController(VehicleUsageFacade facade, VehicleUsageMapper mapper) {
        this.facade = facade;
        this.mapper = mapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public Page<VehicleRequestResponseDto> getRequests(VehicleRequestFilter filter, Pageable pageable) {

        return facade.searchForVehicleRequest(filter, pageable).map(mapper::toRequestResponseDto);

    }

    @GetMapping("/my-requests")
    @PreAuthorize("hasRole('REQUESTER')")
    public Page<VehicleRequestResponseDto> getUserRequests(@AuthenticationPrincipal User user,
            UserVehicleRequestFilter filter, Pageable pageable) {

        return facade.getUserRequests(filter, user, pageable).map(mapper::toRequestResponseDto);

    }

    @PostMapping
    public ResponseEntity<VehicleRequestResponseDto> openNewRequest(@RequestBody @Valid VehicleRequestCreateDto dto,
            @AuthenticationPrincipal User requester) {

        VehicleRequest created = facade.openVehicleRequest(dto, requester);
        VehicleRequestResponseDto response = mapper.toRequestResponseDto(created);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);

    }

    @PatchMapping("/{requestId}/approve")
    public ResponseEntity<Void> approveRequest(@PathVariable Long requestId,
            @RequestBody @Valid VehicleRequestApprovalDto dto,
            @AuthenticationPrincipal User approver) {

        facade.approveRequest(requestId, dto, approver);

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/{requestId}/reject")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long requestId,
            @RequestBody String notes,
            @AuthenticationPrincipal User approver) {

        facade.rejectRequest(requestId, notes, approver);

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<Void> cancelRequest(@PathVariable Long requestId,
            @RequestBody String notes,
            @AuthenticationPrincipal User approver) {

        facade.cancelRequest(requestId, notes, approver);

        return ResponseEntity.ok().build();

    }

}
