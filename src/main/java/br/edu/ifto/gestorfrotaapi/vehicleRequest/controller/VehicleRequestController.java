package br.edu.ifto.gestorfrotaapi.vehicleRequest.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifto.gestorfrotaapi.authentication.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestApprovalDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestCancelDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestCreateRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestRejectDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.valueObjects.UsagePeriod;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.filters.UserVehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.filters.VehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.service.VehicleRequestService;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.service.VehicleUsageService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("requests")
public class VehicleRequestController {

    private final VehicleRequestService service;
    private final VehicleUsageService usageService;

    public VehicleRequestController(VehicleRequestService service, VehicleUsageService usageService) {
        this.service = service;
        this.usageService = usageService;
    }

    @GetMapping
    public Page<VehicleRequestResponseDto> getRequests(VehicleRequestFilter filter,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        return service.searchForVehicleRequest(filter, pageable);

    }

    @GetMapping("/{id}")
    public VehicleRequestResponseDto getRequestDetails(@PathVariable Long id) {

        return service.findById(id);

    }

    @GetMapping("/my-requests")
    public Page<VehicleRequestResponseDto> getUserRequests(UserVehicleRequestFilter filter,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

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

    @GetMapping("/available-drivers")
    public ResponseEntity<List<UserResponseDto>> getAvailableDrivers(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime usageStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime usageEnd) {

        var period = UsagePeriod.of(usageStart, usageEnd);

        return ResponseEntity.ok(usageService.findAvailableDrivers(period));
    }

    @GetMapping("/available-vehicles")
    public ResponseEntity<List<VehicleResponseDto>> getAvailableVehicles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime usageStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime usageEnd) {

        var period = UsagePeriod.of(usageStart, usageEnd);

        return ResponseEntity.ok(usageService.findAvailableVehicles(period));

    }

}
