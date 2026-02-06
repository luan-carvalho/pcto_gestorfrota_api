package br.edu.ifto.gestorfrotaapi.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    boolean existsByLicensePlateValue(String licensePlate);

}
