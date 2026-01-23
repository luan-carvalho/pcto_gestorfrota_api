package br.edu.ifto.gestorfrotaapi.vehicle.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
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

    public Vehicle() {
    }

    public Vehicle(String model, String make, String licensePlate, VehicleType type, Integer capacity,
            Integer mileage) {
        this.model = model;
        this.make = make;
        this.licensePlate = licensePlate;
        this.type = type;
        this.capacity = capacity;
        this.mileage = mileage;
        this.status = VehicleStatus.AVAILABLE;
    }

    public void updateStatus(VehicleStatus status) {

        this.status = status;

    }

    public void deactivate() {

        this.status = VehicleStatus.DECOMISSIONED;

    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getMake() {
        return make;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getMileage() {
        return mileage;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void updateInfo(String licensePlate, String make, String model, VehicleType type, Integer capacity) {

        if (model != null) {
            this.model = model;
        }
        if (make != null) {
            this.make = make;
        }
        if (licensePlate != null) {
            this.licensePlate = licensePlate;
        }
        if (type != null) {
            this.type = type;
        }
        if (capacity != null) {
            this.capacity = capacity;
        }

    }

}
