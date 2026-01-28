package br.edu.ifto.gestorfrotaapi.vehicle.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifto.gestorfrotaapi.vehicle.command.CreateVehicleCommand;
import br.edu.ifto.gestorfrotaapi.vehicle.command.UpdateVehicleCommand;
import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleCreationRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleUpdateRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicle.mapper.VehicleMapper;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.service.VehicleService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("admin/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper mapper;

    public VehicleController(VehicleService vehicleService, VehicleMapper mapper) {
        this.vehicleService = vehicleService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<VehicleResponseDto> getAll() {
        return mapper.toResponseDto(vehicleService.findAll());
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@Valid @RequestBody VehicleCreationRequestDto request) {

        Vehicle saved = vehicleService.create(new CreateVehicleCommand(
                request.model(),
                request.make(),
                request.licensePlate(),
                request.type(),
                request.capacity(),
                request.currentMileage()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toResponseDto(saved));

    }

    @GetMapping("/{id}")
    public VehicleResponseDto getOne(@PathVariable Long id) {
        return mapper.toResponseDto(vehicleService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateVehicle(@PathVariable Long id,
            @RequestBody @Valid VehicleUpdateRequestDto request) {

        vehicleService.update(
                id,
                new UpdateVehicleCommand(
                        request.make(),
                        request.model(),
                        request.licensePlate(),
                        request.capacity(),
                        request.type()));

        return ResponseEntity.noContent().build();

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
