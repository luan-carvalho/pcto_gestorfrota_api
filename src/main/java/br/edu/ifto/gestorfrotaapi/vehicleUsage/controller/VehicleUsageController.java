package br.edu.ifto.gestorfrotaapi.vehicleUsage.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.CheckInDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.CheckOutDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.UserVehicleUsageFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleUsageFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleUsageResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.mapper.VehicleUsageMapper;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.service.VehicleUsageFacade;
import jakarta.validation.Valid;

@RestController
@RequestMapping("usage")
public class VehicleUsageController {

    private final VehicleUsageFacade facade;
    private final VehicleUsageMapper mapper;

    public VehicleUsageController(VehicleUsageFacade facade, VehicleUsageMapper mapper) {
        this.facade = facade;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    @GetMapping
    public Page<VehicleUsageResponseDto> getUsages(VehicleUsageFilter filter, Pageable pageable) {

        return facade.searchForVehicleUsage(filter, pageable).map(mapper::toUsageResponseDto);

    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("my-usages")
    public Page<VehicleUsageResponseDto> getDriverUsages(UserVehicleUsageFilter filter, Pageable pageable,
            @AuthenticationPrincipal User user) {

        return facade.getDriverUsages(filter, user, pageable).map(mapper::toUsageResponseDto);

    }

    @PatchMapping("/{usageId}/check-in")
    public ResponseEntity<Void> checkIn(@PathVariable Long usageId, @RequestBody @Valid CheckInDto dto,
            @AuthenticationPrincipal User user) {

        System.out.println(dto.currentMileage());

        facade.checkIn(usageId, dto, user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{usageId}/check-out")
    public ResponseEntity<Void> checkOut(@PathVariable Long usageId, @RequestBody @Valid CheckOutDto dto,
            @AuthenticationPrincipal User user) {

        facade.checkOut(usageId, dto, user);
        return ResponseEntity.ok().build();

    }

}
