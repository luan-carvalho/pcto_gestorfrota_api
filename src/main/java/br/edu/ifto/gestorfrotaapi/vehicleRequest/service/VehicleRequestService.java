package br.edu.ifto.gestorfrotaapi.vehicleRequest.service;

import static br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.spec.VehicleRequestSpecification.createdBetween;
import static br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.spec.VehicleRequestSpecification.hasPriority;
import static br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.spec.VehicleRequestSpecification.hasProcessNumber;
import static br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.spec.VehicleRequestSpecification.hasPurpose;
import static br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.spec.VehicleRequestSpecification.hasRequestId;
import static br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.spec.VehicleRequestSpecification.hasRequesterName;
import static br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.spec.VehicleRequestSpecification.hasStatus;
import static br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.spec.VehicleRequestSpecification.hasVehicleDescription;
import static br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.spec.VehicleRequestSpecification.usageBetween;

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
import br.edu.ifto.gestorfrotaapi.vehicleRequest.command.ApproveRequestCommand;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.command.OpenVehicleRequestCommand;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.dto.VehicleRequestResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.exception.VehicleRequestApprovalException;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.exception.VehicleRequestNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.mapper.VehicleRequestMapper;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.valueObjects.UsagePeriod;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.VehicleRequestRepository;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.filters.UserVehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.filters.VehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.repository.spec.VehicleRequestSpecification;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.command.OpenVehicleUsageCommand;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.service.VehicleUsageService;

@Service
@Transactional
public class VehicleRequestService {

    private final VehicleRequestRepository requestRepo;
    private final VehicleUsageService usageService;
    private final VehicleService vehicleService;
    private final UserService userService;
    private final VehicleRequestMapper mapper;

    public VehicleRequestService(VehicleRequestRepository requestRepo, VehicleUsageService usageService,
            VehicleService vehicleService, UserService userService, VehicleRequestMapper mapper) {
        this.requestRepo = requestRepo;
        this.usageService = usageService;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public VehicleRequest getRequest(Long requestId) {

        return requestRepo.findById(requestId)
                .orElseThrow(() -> new VehicleRequestNotFoundException(requestId));

    }

    public VehicleRequestResponseDto findById(Long id) {

        return mapper.toRequestResponseDto(getRequest(id));

    }

    @PreAuthorize("hasRole('REQUESTER')")
    public VehicleRequestResponseDto create(OpenVehicleRequestCommand cmd) {

        User requester = SecurityUtils.currentUser();

        VehicleRequest created = requestRepo.save(
                VehicleRequest.builder()
                        .requester(requester)
                        .description(cmd.description())
                        .period(new UsagePeriod(cmd.startDateTime(), cmd.endDateTime()))
                        .priority(cmd.priority())
                        .purpose(cmd.purpose())
                        .processNumber(cmd.processNumber())
                        .destination(cmd.destination())
                        .build());

        return mapper.toRequestResponseDto(created);

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void approve(ApproveRequestCommand cmd) {

        User driver = userService.getUser(cmd.driverId());
        Vehicle vehicle = vehicleService.getVehicle(cmd.vehicleId());
        VehicleRequest request = getRequest(cmd.requestId());
        User approver = SecurityUtils.currentUser();

        if (!driver.hasRole(Role.DRIVER)) {

            throw new VehicleRequestApprovalException("The driver must have the role DRIVER");

        }

        usageService.checkVehicleConflicts(vehicle, request.getPeriod());
        usageService.checkDriverConflicts(driver, request.getPeriod());

        request.approve(approver, driver, cmd.notes());

        usageService.create(
                new OpenVehicleUsageCommand(
                        request,
                        driver,
                        vehicle));

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void reject(Long requestId, String notes) {

        User rejector = SecurityUtils.currentUser();
        VehicleRequest request = getRequest(requestId);
        request.reject(rejector, notes);

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void cancel(Long requestId, String notes) {

        User canceledBy = SecurityUtils.currentUser();
        VehicleRequest request = getRequest(requestId);
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

}
