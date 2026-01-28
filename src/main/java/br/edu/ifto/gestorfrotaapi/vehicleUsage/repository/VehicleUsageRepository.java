package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;

public interface VehicleUsageRepository
                extends JpaRepository<VehicleUsage, Long>, JpaSpecificationExecutor<VehicleUsage> {

        @Query("""
                        SELECT COUNT(v) > 0
                        FROM VehicleUsage v
                        WHERE v.driver.id = :driverId
                        AND v.status IN :activeStatuses
                        AND v.vehicleRequest.endDateTime > :requestedStartDate
                        AND v.vehicleRequest.startDateTime < :requestedEndDate
                        """)
        boolean existsConflict(
                        @Param("requestedStartDate") LocalDateTime requestedStartDate,
                        @Param("requestedEndDate") LocalDateTime requestedEndDate,
                        @Param("driverId") Long driverId,
                        @Param("activeStatuses") Collection<VehicleUsageStatus> activeStatuses);

}
