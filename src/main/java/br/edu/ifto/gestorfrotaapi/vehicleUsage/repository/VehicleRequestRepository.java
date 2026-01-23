package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;

public interface VehicleRequestRepository extends JpaRepository<VehicleRequest, Long> {

    @Query("""
                SELECT COUNT(v) > 0
                FROM VehicleRequest v
                WHERE v.vehicle.id = :vehicleId
                  AND v.status IN :activeStatuses
                  AND v.endDate > :requestedStartDate
                  AND v.startDate < :requestedEndDate
            """)
    boolean existsConflict(
            @Param("requestedStartDate") LocalDateTime requestedStartDate,
            @Param("requestedEndDate") LocalDateTime requestedEndDate,
            @Param("vehicleId") Long vehicleId,
            @Param("activeStatuses") Collection<RequestStatus> activeStatuses);

}
