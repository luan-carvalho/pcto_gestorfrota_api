package br.edu.ifto.gestorfrotaapi.vehicleUsage.service;

import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.createdBetween;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasPriority;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasProcessNumber;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasPurpose;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasRequestId;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasRequesterName;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasStatus;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasVehicleDescription;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasVehicleLicensePlate;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.usageBetween;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.checkInBetween;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.checkOutBetween;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.hasVehicleRequestId;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.edu.ifto.gestorfrotaapi.authentication.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.repository.UserRepository;
import br.edu.ifto.gestorfrotaapi.vehicle.exception.VehicleNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.repository.VehicleRepository;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.CheckInDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.CheckOutDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.UserVehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.UserVehicleUsageFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestApprovalDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestCreateDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleUsageFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.RequestConflictException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.VehicleRequestNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.VehicleUsageNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.VehicleRequestRepository;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.VehicleUsageRepository;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification;
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
                                .and(hasVehicleLicensePlate(filter.vehicleLicensePlate()))
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

                boolean hasDriverConflict; //implementar validação de motorista

                request.approve(approver, driver, dto.notes());
                requestRepo.save(request);

                VehicleUsage newUsage = new VehicleUsage(request, driver);
                usageRepo.save(newUsage);

        }

        public Page<VehicleUsage> searchForVehicleUsage(VehicleUsageFilter filter, Pageable pageable) {

                Specification<VehicleUsage> spec = Specification
                                .where(VehicleUsageSpecification.hasDriverName(filter.driverName()))
                                .and(hasVehicleRequestId(filter.requestId()))
                                .and(VehicleUsageSpecification.hasVehicleDescription(filter.vehicleDescription()))
                                .and(checkInBetween(filter.checkInFrom(), filter.checkInTo()))
                                .and(checkOutBetween(filter.checkOutFrom(), filter.checkOutTo()))
                                .and(VehicleUsageSpecification.hasStatus(filter.status()));

                return usageRepo.findAll(spec, pageable);

        }

        @Transactional
        @PreAuthorize("hasRole('DRIVER')")
        public void checkIn(Long usageId, CheckInDto dto, User driver) {

                VehicleUsage usage = usageRepo.findById(usageId)
                                .orElseThrow(() -> new VehicleUsageNotFoundException(
                                                usageId));

                if (!usage.getDriver().equals(driver)) {

                        throw new AccessDeniedException("Only assigned driver can check in");

                }

                usage.checkIn(dto.currentMileage());
                usageRepo.save(usage);

        }

        @Transactional
        @PreAuthorize("hasRole('DRIVER')")
        public void checkOut(Long usageId, CheckOutDto dto, User driver) {

                VehicleUsage usage = usageRepo.findById(usageId)
                                .orElseThrow(() -> new VehicleUsageNotFoundException(
                                                usageId));

                if (!usage.getDriver().equals(driver)) {

                        throw new AccessDeniedException("Only assigned driver can check out");

                }

                usage.checkOut(dto.endMileage(), dto.notes());
                usageRepo.save(usage);

        }

        public void rejectRequest(Long requestId, String notes, User rejector) {

                VehicleRequest request = findById(requestId);
                request.reject(rejector, notes);
                requestRepo.save(request);

        }

        public void cancelRequest(Long requestId, String notes, User canceledBy) {

                VehicleRequest request = findById(requestId);
                request.cancel(canceledBy, notes);
                requestRepo.save(request);

        }

        public Page<VehicleRequest> getUserRequests(UserVehicleRequestFilter filter, User user, Pageable pageable) {

                Specification<VehicleRequest> spec = Specification
                                .where(hasRequestId(filter.requestId()))
                                .and(VehicleRequestSpecification.hasRequesterId(user.getId()))
                                .and(hasVehicleDescription(filter.vehicleDescription()))
                                .and(hasStatus(filter.status()))
                                .and(hasPriority(filter.priority()))
                                .and(hasPurpose(filter.purpose()))
                                .and(hasProcessNumber(filter.processNumber()))
                                .and(usageBetween(filter.usageFrom(), filter.usageTo()));

                return requestRepo.findAll(spec, pageable);

        }

        public Page<VehicleUsage> getDriverUsages(UserVehicleUsageFilter filter, User user, Pageable pageable) {

                Specification<VehicleUsage> spec = Specification
                                .where(VehicleUsageSpecification.hasDriverId(user.getId()))
                                .and(VehicleUsageSpecification.hasRequesterName(filter.requesterName()))
                                .and(VehicleUsageSpecification.hasVehicleRequestId(filter.requestId()))
                                .and(VehicleUsageSpecification.hasVehicleDescription(filter.vehicleDescription()))
                                .and(VehicleUsageSpecification.hasStatus(filter.status()))
                                .and(VehicleUsageSpecification.usageBetween(filter.usageFrom(), filter.usageTo()));

                return usageRepo.findAll(spec, pageable);

        }
}
