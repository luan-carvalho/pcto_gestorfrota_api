package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;

public interface VehicleRequestRepository
                extends JpaRepository<VehicleRequest, Long>, JpaSpecificationExecutor<VehicleRequest> {

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

        Page<VehicleRequest> findAll(Specification<VehicleRequest> spec, Pageable page);

}
