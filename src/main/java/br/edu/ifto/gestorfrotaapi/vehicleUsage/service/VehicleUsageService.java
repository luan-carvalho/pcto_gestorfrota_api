package br.edu.ifto.gestorfrotaapi.vehicleUsage.service;

import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.checkInBetween;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.checkOutBetween;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.hasDriverId;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.hasDriverName;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.hasRequesterName;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.hasStatus;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.hasVehicleDescription;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.hasVehicleRequestId;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications.VehicleUsageSpecification.usageBetween;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.util.SecurityUtils;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.command.OpenVehicleUsageCommand;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.DriverNotAvaliableException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.NotAssignedDriverException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.VehicleUsageNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.VehicleUsageRepository;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters.UserVehicleUsageFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters.VehicleUsageFilter;

@Service
@Transactional
public class VehicleUsageService {

    private final VehicleUsageRepository usageRepo;

    public VehicleUsageService(VehicleUsageRepository usageRepo) {
        this.usageRepo = usageRepo;
    }

    public VehicleUsage findById(Long id) {

        return usageRepo.findById(id)
                .orElseThrow(() -> new VehicleUsageNotFoundException(
                        id));

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void create(OpenVehicleUsageCommand cmd) {

        VehicleUsage usage = VehicleUsage.create(cmd.request(), cmd.driver());
        usageRepo.save(usage);

    }

    @PreAuthorize("hasRole('DRIVER')")
    public void checkIn(Long usageId, Integer currentMileage) {

        User driver = SecurityUtils.currentUser();

        VehicleUsage usage = findById(usageId);

        if (!usage.getDriver().equals(driver)) {

            throw new NotAssignedDriverException();

        }

        usage.checkIn(currentMileage);

    }

    @PreAuthorize("hasRole('DRIVER')")
    public void checkOut(Long usageId, Integer endMileage, String notes) {

        User driver = SecurityUtils.currentUser();

        VehicleUsage usage = findById(usageId);

        if (!usage.getDriver().equals(driver)) {

            throw new NotAssignedDriverException();

        }

        usage.checkOut(endMileage, notes);

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void checkDriverConflicts(User driver, LocalDateTime usageStart, LocalDateTime usageEnd) {

        boolean hasConflict = usageRepo
                .existsConflict(
                        usageStart,
                        usageEnd,
                        driver.getId(),
                        List.of(VehicleUsageStatus.NOT_STARTED, VehicleUsageStatus.STARTED));

        if (hasConflict) {

            throw new DriverNotAvaliableException(driver);

        }

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    @Transactional(readOnly = true)
    public Page<VehicleUsage> searchForVehicleUsage(VehicleUsageFilter filter, Pageable pageable) {

        Specification<VehicleUsage> spec = Specification
                .where(hasDriverName(filter.driverName()))
                .and(hasVehicleRequestId(filter.requestId()))
                .and(hasVehicleDescription(filter.vehicleDescription()))
                .and(checkInBetween(filter.checkInFrom(), filter.checkInTo()))
                .and(checkOutBetween(filter.checkOutFrom(), filter.checkOutTo()))
                .and(hasStatus(filter.status()));

        return usageRepo.findAll(spec, pageable);

    }

    @PreAuthorize("hasRole('DRIVER')")
    @Transactional(readOnly = true)
    public Page<VehicleUsage> getDriverUsages(UserVehicleUsageFilter filter, Pageable pageable) {

        User loggedDriver = SecurityUtils.currentUser();

        Specification<VehicleUsage> spec = Specification
                .where(hasDriverId(loggedDriver.getId()))
                .and(hasRequesterName(filter.requesterName()))
                .and(hasVehicleRequestId(filter.requestId()))
                .and(hasVehicleDescription(filter.vehicleDescription()))
                .and(hasStatus(filter.status()))
                .and(usageBetween(filter.usageFrom(), filter.usageTo()));

        return usageRepo.findAll(spec, pageable);

    }

}
