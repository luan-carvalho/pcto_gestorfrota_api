package br.edu.ifto.gestorfrotaapi.vehicleUsage.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.CheckInRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.CheckOutRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleUsageResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.mapper.VehicleUsageMapper;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters.UserVehicleUsageFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters.VehicleUsageFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.service.VehicleUsageService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("usage")
public class VehicleUsageController {

    private final VehicleUsageService service;
    private final VehicleUsageMapper mapper;

    public VehicleUsageController(VehicleUsageService service, VehicleUsageMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<VehicleUsageResponseDto> getUsages(VehicleUsageFilter filter, Pageable pageable) {

        return service.searchForVehicleUsage(filter, pageable).map(mapper::toUsageResponseDto);

    }

    @GetMapping("my-usages")
    public Page<VehicleUsageResponseDto> getDriverUsages(UserVehicleUsageFilter filter, Pageable pageable) {

        return service.getDriverUsages(filter, pageable).map(mapper::toUsageResponseDto);

    }

    @PatchMapping("/{usageId}/check-in")
    public ResponseEntity<Void> checkIn(@PathVariable Long usageId, @RequestBody @Valid CheckInRequestDto dto) {

        System.out.println(dto.currentMileage());

        service.checkIn(usageId, dto.currentMileage());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{usageId}/check-out")
    public ResponseEntity<Void> checkOut(@PathVariable Long usageId, @RequestBody @Valid CheckOutRequestDto dto) {

        service.checkOut(usageId, dto.endMileage(), dto.notes());
        return ResponseEntity.ok().build();

    }

}
