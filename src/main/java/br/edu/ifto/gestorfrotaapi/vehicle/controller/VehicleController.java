package br.edu.ifto.gestorfrotaapi.vehicle.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleCreationRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleUpdateRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicle.repository.filter.VehicleFilter;
import br.edu.ifto.gestorfrotaapi.vehicle.service.VehicleService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;

    }

    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@Valid @RequestBody VehicleCreationRequestDto request) {

        VehicleResponseDto saved = vehicleService.create(request.toCommand());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.id())
                .toUri();

        return ResponseEntity.created(location).body(saved);

    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponseDto>> getAll(VehicleFilter filter,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(vehicleService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public VehicleResponseDto getOne(@PathVariable Long id) {
        return vehicleService.findById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> updateVehicle(@PathVariable Long id,
            @RequestBody @Valid VehicleUpdateRequestDto request) {

        VehicleResponseDto updated = vehicleService.update(request.toCommand(id));

        return ResponseEntity.ok(updated);

    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateVehicle(@PathVariable Long id) {
        vehicleService.activate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateVehicle(@PathVariable Long id) {
        vehicleService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

}
