package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;

public interface VehicleUsageRepository
        extends JpaRepository<VehicleUsage, Long>, JpaSpecificationExecutor<VehicleUsage> {

}
