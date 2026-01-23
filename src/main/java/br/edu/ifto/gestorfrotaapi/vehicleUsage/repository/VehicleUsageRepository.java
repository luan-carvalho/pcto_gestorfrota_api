package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;

public interface VehicleUsageRepository extends JpaRepository<VehicleUsage, Long> {

}
