package br.edu.ifto.gestorfrotaapi.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
