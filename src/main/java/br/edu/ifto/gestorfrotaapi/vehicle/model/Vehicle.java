package br.edu.ifto.gestorfrotaapi.vehicle.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.MileageEntrySource;
import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.VehicleStatus;
import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.VehicleType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

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
    private Integer currentMileage;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @OneToMany(mappedBy = "vehicle")
    private List<VehicleMileageEntry> mileageHistory;

    public Vehicle() {
    }

    public Vehicle(String model, String make, String licensePlate, VehicleType type, Integer capacity,
            Integer currentMileage,
            User createdBy) {

        this.make = make;
        this.licensePlate = licensePlate;
        this.type = type;
        this.capacity = capacity;
        this.mileageHistory = new ArrayList<>();

        updateMileage(
                currentMileage,
                MileageEntrySource.INITIAL_REGISTRATION,
                createdBy,
                "Initial registration");

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

    public Integer getCurrentMileage() {
        return currentMileage;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void updateMileage(
            Integer newMileage,
            MileageEntrySource source,
            User recordedBy,
            String notes) {

        Objects.requireNonNull(newMileage, "Mileage cannot be null");
        Objects.requireNonNull(source, "Mileage source is required");
        Objects.requireNonNull(recordedBy, "User is required");

        if (newMileage < 0) {
            throw new IllegalArgumentException("Mileage cannot be negative");
        }

        if (notes == null || notes.isBlank()) {
            throw new IllegalArgumentException(
                    "Admin correction requires justification");
        }

        VehicleMileageEntry entry = new VehicleMileageEntry(
                this,
                newMileage,
                recordedBy,
                LocalDateTime.now(),
                notes,
                source);

        this.mileageHistory.add(entry);
        this.currentMileage = newMileage;

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
