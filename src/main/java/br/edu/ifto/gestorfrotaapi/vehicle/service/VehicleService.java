package br.edu.ifto.gestorfrotaapi.vehicle.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.util.SecurityUtils;
import br.edu.ifto.gestorfrotaapi.vehicle.command.CreateVehicleCommand;
import br.edu.ifto.gestorfrotaapi.vehicle.command.UpdateVehicleCommand;
import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.exception.LiscensePlateAlreadyRegistered;
import br.edu.ifto.gestorfrotaapi.vehicle.exception.VehicleNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicle.mapper.VehicleMapper;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.repository.VehicleRepository;
import br.edu.ifto.gestorfrotaapi.vehicle.repository.filter.VehicleFilter;
import br.edu.ifto.gestorfrotaapi.vehicle.repository.spec.VehicleSpecification;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    public VehicleService(VehicleRepository repository, VehicleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public Page<VehicleResponseDto> findAll(VehicleFilter filter, Pageable pageable) {

        Specification<Vehicle> spec = Specification
                .where(VehicleSpecification.hasId(filter.id()))
                .and(VehicleSpecification.hasDescription(filter.description()))
                .and(VehicleSpecification.hasStatus(filter.status()));

        return repository.findAll(spec, pageable).map(mapper::toResponseDto);

    }

    public Vehicle getVehicle(Long id) {

        return repository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public VehicleResponseDto findById(Long id) {

        return mapper.toResponseDto(getVehicle(id));

    }

    @PreAuthorize("hasRole('ADMIN')")
    public VehicleResponseDto create(CreateVehicleCommand cmd) {

        if (repository.existsByLicensePlateValue(cmd.licensePlate().getValue())) {

            throw new LiscensePlateAlreadyRegistered(cmd.licensePlate());

        }

        User createdBy = SecurityUtils.currentUser();

        return mapper
                .toResponseDto(
                        repository.save(
                                Vehicle.builder()
                                        .model(cmd.model())
                                        .make(cmd.make())
                                        .licensePlate(cmd.licensePlate())
                                        .currentMileage(cmd.currentMileage())
                                        .createdBy(createdBy)
                                        .build()));

    }

    @PreAuthorize("hasRole('ADMIN')")
    public VehicleResponseDto update(UpdateVehicleCommand cmd) {

        Vehicle toBeUpdated = getVehicle(cmd.id());

        if (cmd.licensePlate() != null && toBeUpdated.getLicensePlate().equals(cmd.licensePlate()) &&
                repository.existsByLicensePlateValue(cmd.licensePlate().getValue())) {
            throw new LiscensePlateAlreadyRegistered(cmd.licensePlate());
        }

        toBeUpdated.updateInfo(
                cmd.licensePlate(),
                cmd.make(),
                cmd.model());

        return mapper.toResponseDto(toBeUpdated);

    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deactivate(Long id) {

        Vehicle v = getVehicle(id);
        v.deactivate();

    }

    @PreAuthorize("hasRole('ADMIN')")
    public void activate(Long id) {

        Vehicle v = getVehicle(id);
        v.activate();

    }

}
