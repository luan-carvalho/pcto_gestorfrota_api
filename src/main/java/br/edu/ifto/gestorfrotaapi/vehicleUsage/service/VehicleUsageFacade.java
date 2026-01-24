package br.edu.ifto.gestorfrotaapi.vehicleUsage.service;

import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.createdBetween;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasDriverId;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasPriority;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasProcessNumber;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasPurpose;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasRequesterId;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasStatus;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleRequestSpecification.hasVehicleId;
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
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestApprovalDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestCreateDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleRequestFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleUsageFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.RequestConflictException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.VehicleRequestRepository;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.VehicleUsageRepository;
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
                                .orElseThrow(() -> new IllegalArgumentException("Request not found with the given id"));

        }

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
                                .where(hasRequesterId(filter.requesterId()))
                                .and(hasDriverId(filter.driverId()))
                                .and(hasVehicleId(filter.vehicleId()))
                                .and(hasStatus(filter.status()))
                                .and(hasPriority(filter.priority()))
                                .and(hasPurpose(filter.purpose()))
                                .and(hasProcessNumber(filter.processNumber()))
                                .and(createdBetween(filter.createdFrom(), filter.createdTo()))
                                .and(usageBetween(filter.usageFrom(), filter.usageTo()));

                return requestRepo.findAll(spec, pageable);

        }

        @Transactional
        @PreAuthorize("hasAnyRole('FLEET_MANAGER')")
        public void approveRequest(VehicleRequestApprovalDto dto, User approver) {

                VehicleRequest request = findById(dto.requestId());

                User driver = userRepo.findById(dto.driverId()).orElseThrow(
                                () -> new UserNotFoundException(dto.driverId()));

                request.approve(approver, driver, dto.notes());
                requestRepo.save(request);

                VehicleUsage newUsage = new VehicleUsage(request, driver);
                usageRepo.save(newUsage);

        }

        public Page<VehicleUsage> searchForVehicleUsage(VehicleUsageFilter filter, Pageable pageable) {

                Specification<VehicleUsage> spec = Specification
                                .where(VehicleUsageSpecification.hasDriverId(filter.driverId()))
                                .and(hasVehicleRequestId(filter.requestId()))
                                .and(VehicleUsageSpecification.hasVehicleId(filter.vehicleId()))
                                .and(checkInBetween(filter.checkInFrom(), filter.checkInTo()))
                                .and(checkOutBetween(filter.checkOutFrom(), filter.checkOutTo()))
                                .and(VehicleUsageSpecification.hasStatus(filter.status()));

                return usageRepo.findAll(spec, pageable);

        }

        @Transactional
        @PreAuthorize("hasAnyRole('DRIVER')")
        public void checkIn(CheckInDto dto, User driver) {

                VehicleUsage usage = usageRepo.findById(dto.vehicleUsageId())
                                .orElseThrow(() -> new IllegalArgumentException("Vehicle Usage not found"));

                if (!usage.getDriver().equals(driver)) {

                        throw new AccessDeniedException("Only assigned driver can check in");

                }

                usage.checkIn(dto.currentMileage());
                usageRepo.save(usage);

        }

        @Transactional
        @PreAuthorize("hasAnyRole('DRIVER')")
        public void checkOut(CheckOutDto dto, User driver) {

                VehicleUsage usage = usageRepo.findById(dto.vehicleUsageId())
                                .orElseThrow(() -> new IllegalArgumentException("Vehicle Usage not found"));

                if (!usage.getDriver().equals(driver)) {

                        throw new AccessDeniedException("Only assigned driver can check out");

                }

                usage.checkOut(dto.endMileage(), dto.notes());
                usageRepo.save(usage);

        }
}
