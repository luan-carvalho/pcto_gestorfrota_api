package br.edu.ifto.gestorfrotaapi.vehicleUsage.service;

import org.springframework.stereotype.Service;

import br.edu.ifto.gestorfrotaapi.authentication.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.repository.UserRepository;
import br.edu.ifto.gestorfrotaapi.vehicle.exception.VehicleNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.repository.VehicleRepository;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestCreateDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleRequestPurpose;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.VehicleRequestRepository;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.VehicleUsageRepository;
import jakarta.transaction.Transactional;

@Service
public class VehicleUsageFacade {

    private final UserRepository userRepo;
    private final VehicleRepository vehicleRepo;
    private final VehicleRequestRepository requestRepo;
    private final VehicleUsageRepository usageRepo;

    public VehicleUsageFacade(UserRepository userRepo, VehicleRepository vehicleRepo,
            VehicleRequestRepository requestRepo, VehicleUsageRepository usageRepo) {
        this.userRepo = userRepo;
        this.vehicleRepo = vehicleRepo;
        this.requestRepo = requestRepo;
        this.usageRepo = usageRepo;
    }

    @Transactional
    public VehicleRequest openVehicleRequest(VehicleRequestCreateDto dto) {

        User requester = userRepo.findByRegistration(dto.requesterRegistration())
                .orElseThrow(() -> new UserNotFoundException(dto.requesterRegistration()));

        Vehicle vehicle = vehicleRepo.findById(dto.requestedVehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(dto.requestedVehicleId()));

        return requestRepo.save(new VehicleRequest(
                requester,
                vehicle,
                RequestPriority.valueOf(dto.priority()),
                dto.processNumber(),
                dto.startDateTime(),
                dto.endDateTime(),
                VehicleRequestPurpose.valueOf(dto.purpose())

        ));

    }

}
