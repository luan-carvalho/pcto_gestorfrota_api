package br.edu.ifto.gestorfrotaapi.vehicle.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleCreationRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleUpdateRequestDto;
import br.edu.ifto.gestorfrotaapi.vehicle.exception.VehicleNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.model.VehicleType;
import br.edu.ifto.gestorfrotaapi.vehicle.repository.VehicleRepository;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public List<Vehicle> listAllVehicles() {

        return repository.findAll();

    }

    public Vehicle getVehicleById(Long id) {

        return repository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));

    }

    public Vehicle createNewVehicle(VehicleCreationRequestDto request) {

        VehicleType type = null;

        if (request.type() != null)
            type = VehicleType.valueOf(request.type());

        Vehicle newVehicle = new Vehicle(
                request.model(),
                request.make(),
                request.licensePlate(),
                type,
                request.capacity(),
                request.mileage());

        return repository.save(newVehicle);

    }

    public Vehicle updateVehicleInfo(Long id, VehicleUpdateRequestDto request) {

        VehicleType type = null;

        if (request.type() != null)
            type = VehicleType.valueOf(request.type());

        Vehicle toBeUpdated = getVehicleById(id);
        toBeUpdated.updateInfo(
                request.licensePlate(),
                request.make(),
                request.model(),
                type,
                request.capacity());
        return repository.save(toBeUpdated);

    }

    public void deleteById(Long id) {

        Vehicle toBeDeleted = getVehicleById(id);
        repository.delete(toBeDeleted);

    }

    public void deactivate(Long id) {

        Vehicle v = repository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        v.deactivate();
        repository.save(v);

    }

}
