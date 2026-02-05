package br.edu.ifto.gestorfrotaapi.vehicleRequest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.VehicleRequest;

public interface VehicleRequestRepository
                extends JpaRepository<VehicleRequest, Long>, JpaSpecificationExecutor<VehicleRequest> {

        @EntityGraph(attributePaths = { "requester" })
        Page<VehicleRequest> findAll(Specification<VehicleRequest> spec, Pageable page);

}
