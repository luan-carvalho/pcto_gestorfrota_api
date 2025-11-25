package br.edu.ifto.gestorfrotaapi.vehicle.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleCreationRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleReponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.mapper.VehicleMapper;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.service.VehicleService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("vehicle")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper mapper;

    public VehicleController(VehicleService vehicleService, VehicleMapper mapper) {
        this.vehicleService = vehicleService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<VehicleReponseDto> getAll() {
        return mapper.toResponseDto(vehicleService.listAllVehicles());
    }

    @PostMapping
    public ResponseEntity<VehicleReponseDto> create(@Valid @RequestBody VehicleCreationRequestDto request) {

        Vehicle saved = vehicleService.createNewVehicle(mapper.toEntity(request));
        VehicleReponseDto response = mapper.toResponseDto(saved);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);

    }

    @GetMapping("/{id}")
    public VehicleReponseDto getOne(@PathVariable Long id) {
        return mapper.toResponseDto(vehicleService.getVehicleDetails(id));
    }

}
