package br.edu.ifto.gestorfrotaapi.vehicleUsage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.edu.ifto.gestorfrotaapi.authentication.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicle.exception.VehicleNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestApprovalDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestCreateDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.DriverNotAvaliableException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.RequestConflictException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.VehicleRequestNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.VehicleRequestRepository;
import jakarta.transaction.Transactional;

@Service
public class VehicleRequestService {

    private final VehicleRequestRepository requestRepo;

    public VehicleRequestService(VehicleRequestRepository requestRepo) {
        this.requestRepo = requestRepo;
    }

    public VehicleRequest findById(Long requestId) {

        return requestRepo.findById(requestId)
                .orElseThrow(() -> new VehicleRequestNotFoundException(requestId));

    }

    @PreAuthorize("hasRole('REQUESTER')")
    @Transactional
    public VehicleRequest openVehicleRequest(VehicleRequestCreateDto dto, User requester) {

        boolean hasConflict = requestRepo.existsConflict(
                dto.startDateTime(),
                dto.endDateTime(),
                dto.requestedVehicleId(),
                List.of(RequestStatus.APPROVED, RequestStatus.IN_USE));

        if (hasConflict) {

            throw new RequestConflictException(dto.startDateTime(), dto.endDateTime());

        }

        Vehicle vehicle = vehicleRepo
                .findById(dto.requestedVehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(dto.requestedVehicleId()));

        if (!vehicle.isActive()) {

        }

        VehicleRequest created = requestRepo.save(new VehicleRequest(
                requester,
                vehicle,
                dto.priority(),
                dto.processNumber(),
                dto.startDateTime(),
                dto.endDateTime(),
                dto.purpose()

        ));

        return created;

    }

    public Page<VehicleRequest> searchForVehicleRequest(VehicleRequestFilter filter, Pageable pageable) {

        Specification<VehicleRequest> spec = Specification
                .where(hasRequesterName(filter.requesterName()))
                .and(hasRequestId(filter.requestId()))
                .and(hasVehicleDescription(filter.vehicleDescription()))
                .and(hasStatus(filter.status()))
                .and(hasPriority(filter.priority()))
                .and(hasPurpose(filter.purpose()))
                .and(hasProcessNumber(filter.processNumber()))
                .and(createdBetween(filter.createdFrom(), filter.createdTo()))
                .and(usageBetween(filter.usageFrom(), filter.usageTo()));

        return requestRepo.findAll(spec, pageable);

    }

    @Transactional
    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void approveRequest(Long requestId, VehicleRequestApprovalDto dto, User approver) {

        VehicleRequest request = findById(requestId);

        User driver = userRepo.findById(dto.driverId()).orElseThrow(
                () -> new UserNotFoundException(dto.driverId()));

        boolean hasDriverConflict = usageRepo.existsConflict(
                request.getStartDateTime(),
                request.getEndDateTime(),
                dto.driverId(),
                List.of(VehicleUsageStatus.NOT_STARTED, VehicleUsageStatus.STARTED));

        if (hasDriverConflict) {

            throw new DriverNotAvaliableException(driver);

        }

        request.approve(approver, driver, dto.notes());
        requestRepo.save(request);

        VehicleUsage newUsage = new VehicleUsage(request, driver);
        usageRepo.save(newUsage);

    }

}
