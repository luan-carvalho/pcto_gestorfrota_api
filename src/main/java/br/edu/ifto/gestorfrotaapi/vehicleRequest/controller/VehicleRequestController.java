package br.edu.ifto.gestorfrotaapi.vehicleRequest.controller;

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

import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestApprovalDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestCreateRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.filters.UserVehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.filters.VehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.service.VehicleRequestService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("requests")
public class VehicleRequestController {

    private final VehicleRequestService service;

    public VehicleRequestController(VehicleRequestService service) {
        this.service = service;
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

        VehicleRequestResponseDto created = service.create(dto.toOpenRequestCommand());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(location).body(created);

    }

    @PatchMapping("/{requestId}/approve")
    public ResponseEntity<Void> approveRequest(@PathVariable Long requestId,
            @RequestBody @Valid VehicleRequestApprovalDto dto) {

        service.approve(dto.toCommand(requestId));

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/{requestId}/reject")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long requestId,
            @RequestBody @Valid VehicleRequestRejectDto dto) {

        service.reject(requestId, dto.notes());

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<Void> cancelRequest(@PathVariable Long requestId,
            @RequestBody @Valid VehicleRequestCancelDto dto) {

        service.cancel(requestId, dto.notes());

        return ResponseEntity.ok().build();

    }

}
