package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.VehicleStatus;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.valueObjects.UsagePeriod;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;

public interface VehicleUsageRepository
                extends JpaRepository<VehicleUsage, Long>, JpaSpecificationExecutor<VehicleUsage> {

        @Query("""
                        SELECT COUNT(v) > 0
                        FROM VehicleUsage v
                        WHERE v.driver.id = :driverId
                        AND v.status IN :activeStatuses
                        AND v.request.period.endDateTime > :requestedStartDate
                        AND v.request.period.startDateTime < :requestedEndDate
                        """)
        boolean checkDriverConflict(
                        @Param("requestedStartDate") LocalDateTime requestedStartDate,
                        @Param("requestedEndDate") LocalDateTime requestedEndDate,
                        @Param("driverId") Long driverId,
                        @Param("activeStatuses") Collection<VehicleUsageStatus> activeStatuses);

        @Query("""
                        SELECT COUNT(v) > 0
                        FROM VehicleUsage v
                        WHERE v.vehicle.id = :vehicleId
                        AND v.status IN :activeStatuses
                        AND v.request.period.endDateTime > :requestedStartDate
                        AND v.request.period.startDateTime < :requestedEndDate
                        """)
        boolean checkVehicleConflict(
                        @Param("requestedStartDate") LocalDateTime requestedStartDate,
                        @Param("requestedEndDate") LocalDateTime requestedEndDate,
                        @Param("vehicleId") Long vehicleId,
                        @Param("activeStatuses") Collection<VehicleUsageStatus> activeStatuses);

        @Query("""
                        SELECT v
                        FROM Vehicle v
                        WHERE v.status = :vehicleStatus
                        AND NOT EXISTS (
                            SELECT 1
                            FROM VehicleUsage vu
                            WHERE vu.vehicle = v
                            AND vu.request.period.startDateTime < :end
                            AND vu.request.period.endDateTime > :start
                            AND vu.status IN :activeStatuses
                        )
                        """)
        List<Vehicle> findVehicles(
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end,
                        @Param("activeStatuses") Collection<VehicleUsageStatus> activeStatuses,
                        @Param("vehicleStatus") VehicleStatus status);

        @Query("""
                        SELECT u
                        FROM User u
                        WHERE :role MEMBER OF u.roles
                        AND u.status = :userStatus
                        AND NOT EXISTS (
                            SELECT 1
                            FROM VehicleUsage vu
                            WHERE vu.driver = u
                            AND vu.request.period.startDateTime < :end
                            AND vu.request.period.endDateTime > :start
                            AND vu.status IN :activeStatuses
                        )
                        """)
        List<User> findUsers(
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end,
                        @Param("activeStatuses") Collection<VehicleUsageStatus> activeStatuses,
                        @Param("role") Role role,
                        @Param("userStatus") UserStatus status);

        default List<User> findAvailableDrivers(UsagePeriod period) {

                return findUsers(
                                period.getStartDateTime(),
                                period.getEndDateTime(),
                                List.of(VehicleUsageStatus.STARTED, VehicleUsageStatus.NOT_STARTED),
                                Role.DRIVER,
                                UserStatus.ACTIVE);

        }

        default List<Vehicle> findAvailableVehicles(UsagePeriod period) {

                return findVehicles(
                                period.getStartDateTime(),
                                period.getEndDateTime(),
                                List.of(VehicleUsageStatus.STARTED, VehicleUsageStatus.NOT_STARTED),
                                VehicleStatus.ACTIVE);

        }

        default boolean existsVehicleConflict(Vehicle vehicle, UsagePeriod period) {

                return checkVehicleConflict(
                                period.getStartDateTime(),
                                period.getEndDateTime(),
                                vehicle.getId(),
                                List.of(VehicleUsageStatus.STARTED, VehicleUsageStatus.NOT_STARTED));

        }

        default boolean existsDriverConflict(User driver, UsagePeriod period) {

                return checkDriverConflict(
                                period.getStartDateTime(),
                                period.getEndDateTime(),
                                driver.getId(),
                                List.of(VehicleUsageStatus.STARTED, VehicleUsageStatus.NOT_STARTED));

        }

}
