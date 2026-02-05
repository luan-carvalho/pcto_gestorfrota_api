package br.edu.ifto.gestorfrotaapi.vehicle.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.util.SecurityUtils;
import br.edu.ifto.gestorfrotaapi.vehicle.command.CreateVehicleCommand;
import br.edu.ifto.gestorfrotaapi.vehicle.command.UpdateVehicleCommand;
import br.edu.ifto.gestorfrotaapi.vehicle.exception.VehicleNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.model.valueObjects.LicensePlate;
import br.edu.ifto.gestorfrotaapi.vehicle.repository.VehicleRepository;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {

        return repository.findAll();

    }

    @Transactional(readOnly = true)
    public Vehicle findById(Long id) {

        return repository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));

    }

    public Vehicle create(CreateVehicleCommand cmd) {

        User createdBy = SecurityUtils.currentUser();

        return repository.save(
                Vehicle.builder()
                        .model(cmd.model())
                        .make(cmd.make())
                        .licensePlate(new LicensePlate(cmd.licensePlate()))
                        .currentMileage(cmd.currentMileage())
                        .createdBy(createdBy)
                        .build());

    }

    public Vehicle update(Long id, UpdateVehicleCommand cmd) {

        Vehicle toBeUpdated = findById(id);

        toBeUpdated.updateInfo(
                new LicensePlate(cmd.licensePlate()),
                cmd.make(),
                cmd.model());

        return toBeUpdated;

    }

    public void delete(Long id) {

        Vehicle v = findById(id);
        repository.delete(v);

    }

    public void deactivate(Long id) {

        Vehicle v = findById(id);
        v.deactivate();

    }

    public void activate(Long id) {

        Vehicle v = findById(id);
        v.activate();

    }

}
