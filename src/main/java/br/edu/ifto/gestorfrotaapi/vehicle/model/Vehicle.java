package br.edu.ifto.gestorfrotaapi.vehicle.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String make;
    private String licensePlate;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private Integer capacity;
    private Integer mileage;
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    public Vehicle(String model, String make, String licensePlate, VehicleType type, Integer capacity,
            Integer mileage,
            VehicleStatus status) {
        this.model = model;
        this.make = make;
        this.licensePlate = licensePlate;
        this.type = type;
        this.capacity = capacity;
        this.mileage = mileage;
        this.status = status;
    }

    public void updateVehicleInfo(String model, String make, String licensePlate, VehicleType type, Integer capacity) {

        this.model = model;
        this.make = make;
        this.licensePlate = licensePlate;
        this.type = type;
        this.capacity = capacity;

    }

    public void registerNewMileage(Integer newMileage) {

        this.mileage = newMileage;

    }

    public void updateStatus(VehicleStatus status) {

        this.status = status;

    }

}
