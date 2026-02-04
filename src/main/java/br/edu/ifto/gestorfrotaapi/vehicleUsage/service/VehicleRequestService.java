package br.edu.ifto.gestorfrotaapi.vehicleUsage.service;

import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.createdBetween;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasPriority;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasProcessNumber;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasPurpose;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasRequestId;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasRequesterName;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasStatus;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasVehicleDescription;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.usageBetween;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.authentication.service.UserService;
import br.edu.ifto.gestorfrotaapi.authentication.util.SecurityUtils;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.service.VehicleService;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.command.OpenVehicleRequestCommand;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.command.OpenVehicleUsageCommand;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.RequestConflictException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.VehicleInactiveException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.VehicleRequestApprovalException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.VehicleRequestNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.mapper.VehicleUsageMapper;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.VehicleRequestRepository;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters.UserVehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters.VehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification;

@Service
@Transactional
public class VehicleRequestService {

    private final VehicleRequestRepository requestRepo;
    private final VehicleUsageService usageService;
    private final VehicleService vehicleService;
    private final UserService userService;
    private final VehicleUsageMapper mapper;

    public VehicleRequestService(VehicleRequestRepository requestRepo, VehicleUsageService usageService,
            VehicleService vehicleService, UserService userService, VehicleUsageMapper mapper) {
        this.requestRepo = requestRepo;
        this.usageService = usageService;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public VehicleRequest findById(Long requestId) {

        return requestRepo.findById(requestId)
                .orElseThrow(() -> new VehicleRequestNotFoundException(requestId));

    }

    @PreAuthorize("hasRole('REQUESTER')")
    public VehicleRequest create(OpenVehicleRequestCommand cmd) {

        User requester = SecurityUtils.currentUser();

        Vehicle requestedVehicle = vehicleService.findById(cmd.vehicleId());

        checkVehicleAvailability(requestedVehicle, cmd.startDateTime(), cmd.endDateTime());

        VehicleRequest created = requestRepo.save(VehicleRequest.create(
                requester,
                requestedVehicle,
                cmd.priority(),
                cmd.processNumber(),
                cmd.startDateTime(),
                cmd.endDateTime(),
                cmd.purpose()

        ));

        return created;

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void approve(Long requestId, Long driverId, String notes) {

        User driver = userService.findById(driverId);

        if (!driver.hasRole(Role.DRIVER)) {

            throw new VehicleRequestApprovalException("The driver must have the role DRIVER");

        }

        User approver = SecurityUtils.currentUser();
        VehicleRequest request = findById(requestId);
        checkVehicleAvailability(request.getVehicle(), request.getStartDateTime(), request.getEndDateTime());
        usageService.checkDriverConflicts(driver, request.getStartDateTime(), request.getEndDateTime());
        usageService.create(
                new OpenVehicleUsageCommand(
                        request,
                        driver));

        request.approve(approver, driver, notes);

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void reject(Long requestId, String notes) {

        User rejector = SecurityUtils.currentUser();
        VehicleRequest request = findById(requestId);
        request.reject(rejector, notes);

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void cancel(Long requestId, String notes) {

        User canceledBy = SecurityUtils.currentUser();
        VehicleRequest request = findById(requestId);
        request.cancel(canceledBy, notes);

    }

    @Transactional(readOnly = true)
    public Page<VehicleRequestResponseDto> searchForVehicleRequest(VehicleRequestFilter filter, Pageable pageable) {

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

        return requestRepo.findAll(spec, pageable).map(mapper::toRequestResponseDto);

    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('REQUESTER')")
    public Page<VehicleRequestResponseDto> getUserRequests(UserVehicleRequestFilter filter, Pageable pageable) {

        User loggedUser = SecurityUtils.currentUser();

        Specification<VehicleRequest> spec = Specification
                .where(hasRequestId(filter.requestId()))
                .and(VehicleRequestSpecification.hasRequesterId(loggedUser.getId()))
                .and(hasVehicleDescription(filter.vehicleDescription()))
                .and(hasStatus(filter.status()))
                .and(hasPriority(filter.priority()))
                .and(hasPurpose(filter.purpose()))
                .and(hasProcessNumber(filter.processNumber()))
                .and(usageBetween(filter.usageFrom(), filter.usageTo()));

        return requestRepo.findAll(spec, pageable).map(mapper::toRequestResponseDto);

    }

    private void checkVehicleAvailability(Vehicle vehicle, LocalDateTime startTime, LocalDateTime endTime) {

        boolean hasConflict = requestRepo.existsConflict(
                startTime,
                endTime,
                vehicle.getId(),
                List.of(RequestStatus.APPROVED));

        if (hasConflict) {

            throw new RequestConflictException(startTime, endTime);

        }

        if (!vehicle.isActive()) {

            throw new VehicleInactiveException(vehicle);

        }

    }

}
