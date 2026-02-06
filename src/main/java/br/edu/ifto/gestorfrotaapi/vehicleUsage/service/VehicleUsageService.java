package br.edu.ifto.gestorfrotaapi.vehicleUsage.service;

import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.spec.VehicleUsageSpecification.checkInBetween;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.spec.VehicleUsageSpecification.checkOutBetween;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.spec.VehicleUsageSpecification.hasDriverId;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.spec.VehicleUsageSpecification.hasDriverName;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.spec.VehicleUsageSpecification.hasRequestId;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.spec.VehicleUsageSpecification.hasRequesterName;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.spec.VehicleUsageSpecification.hasStatus;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.spec.VehicleUsageSpecification.hasVehicleDescription;
import static br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.spec.VehicleUsageSpecification.usageBetween;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifto.gestorfrotaapi.authentication.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.mapper.UserMapper;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.util.SecurityUtils;
import br.edu.ifto.gestorfrotaapi.vehicle.dto.VehicleResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.mapper.VehicleMapper;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.valueObjects.UsagePeriod;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.command.OpenVehicleUsageCommand;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.dto.VehicleUsageResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.DriverNotAvaliableException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.NotAssignedDriverException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.VehicleNotAvailableException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.VehicleUsageNotFoundException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.mapper.VehicleUsageMapper;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.VehicleUsageRepository;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters.UserVehicleUsageFilter;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.filters.VehicleUsageFilter;

@Service
@Transactional
public class VehicleUsageService {

    private final VehicleUsageRepository usageRepo;
    private final VehicleUsageMapper mapper;
    private final UserMapper userMapper;
    private final VehicleMapper vehicleMapper;

    public VehicleUsageService(VehicleUsageRepository usageRepo, VehicleUsageMapper mapper, UserMapper userMapper,
            VehicleMapper vehicleMapper) {
        this.usageRepo = usageRepo;
        this.mapper = mapper;
        this.userMapper = userMapper;
        this.vehicleMapper = vehicleMapper;
    }

    @PreAuthorize("hasAnyRole('DRIVER', 'FLEET_MANAGER')")
    public VehicleUsageResponseDto findById(Long id) {

        return mapper.toUsageResponseDto(getById(id));

    }

    private VehicleUsage getById(Long id) {

        return usageRepo.findById(id)
                .orElseThrow(() -> new VehicleUsageNotFoundException(
                        id));

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void create(OpenVehicleUsageCommand cmd) {

        VehicleUsage usage = VehicleUsage.builder()
                .request(cmd.request())
                .driver(cmd.driver())
                .vehicle(cmd.vehicle())
                .build();

        usageRepo.save(usage);

    }

    @PreAuthorize("hasRole('DRIVER')")
    public void checkIn(Long usageId, Integer currentMileage) {

        User driver = SecurityUtils.currentUser();

        VehicleUsage usage = getById(usageId);

        if (!usage.getDriver().equals(driver)) {

            throw new NotAssignedDriverException();

        }

        usage.checkIn(currentMileage);

    }

    @PreAuthorize("hasRole('DRIVER')")
    public void checkOut(Long usageId, Integer endMileage, String notes) {

        User driver = SecurityUtils.currentUser();

        VehicleUsage usage = getById(usageId);

        if (!usage.getDriver().equals(driver)) {

            throw new NotAssignedDriverException();

        }

        usage.checkOut(endMileage, notes);

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public List<UserResponseDto> findAvailableDrivers(UsagePeriod period) {

        return userMapper.toResponseDto(usageRepo.findAvailableDrivers(period));

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public List<VehicleResponseDto> findAvailableVehicles(UsagePeriod period) {

        return vehicleMapper.toResponseDto(usageRepo.findAvailableVehicles(period));

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void checkDriverConflicts(User driver, UsagePeriod period) {

        boolean hasConflict = usageRepo.existsDriverConflict(driver, period);

        if (hasConflict) {

            throw new DriverNotAvaliableException(driver);

        }

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    public void checkVehicleConflicts(Vehicle vehicle, UsagePeriod period) {

        boolean hasConflict = usageRepo.existsVehicleConflict(vehicle, period);

        if (hasConflict) {

            throw new VehicleNotAvailableException();

        }

    }

    @PreAuthorize("hasRole('FLEET_MANAGER')")
    @Transactional(readOnly = true)
    public Page<VehicleUsageResponseDto> searchForVehicleUsage(VehicleUsageFilter filter, Pageable pageable) {

        Specification<VehicleUsage> spec = Specification
                .where(hasDriverName(filter.driverName()))
                .and(hasRequestId(filter.requestId()))
                .and(hasVehicleDescription(filter.vehicleDescription()))
                .and(checkInBetween(filter.checkInFrom(), filter.checkInTo()))
                .and(checkOutBetween(filter.checkOutFrom(), filter.checkOutTo()))
                .and(hasStatus(filter.status()));

        return usageRepo.findAll(spec, pageable).map(mapper::toUsageResponseDto);

    }

    @PreAuthorize("hasRole('DRIVER')")
    @Transactional(readOnly = true)
    public Page<VehicleUsageResponseDto> getDriverUsages(UserVehicleUsageFilter filter, Pageable pageable) {

        User loggedDriver = SecurityUtils.currentUser();

        Specification<VehicleUsage> spec = Specification
                .where(hasDriverId(loggedDriver.getId()))
                .and(hasRequesterName(filter.requesterName()))
                .and(hasRequestId(filter.requestId()))
                .and(hasVehicleDescription(filter.vehicleDescription()))
                .and(hasStatus(filter.status()))
                .and(usageBetween(filter.usageFrom(), filter.usageTo()));

        return usageRepo.findAll(spec, pageable).map(mapper::toUsageResponseDto);

    }

}
