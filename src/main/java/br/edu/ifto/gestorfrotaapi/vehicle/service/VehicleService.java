package br.edu.ifto.gestorfrotaapi.vehicle.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public Vehicle createNewVehicle(Vehicle vehicle) {

        return repository.save(vehicle);

    }

    public Vehicle updateVehicleInfo(Long id, VehicleUpdateRequestDto request) {

        Vehicle toBeUpdated = getVehicleById(id);
        toBeUpdated.updateVehicleInfo(
                request.model(),
                request.make(),
                request.licensePlate(),
                VehicleType.valueOf(request.type()),
                request.capacity());

        return repository.save(toBeUpdated);

    }

}
