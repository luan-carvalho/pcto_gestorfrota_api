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

    protected Vehicle() {

    }

    private Vehicle(String model, String make, String licensePlate, VehicleType type, Integer capacity,
            Integer currentMileage,
            User createdBy) {

        Objects.requireNonNull(model);
        Objects.requireNonNull(make);
        Objects.requireNonNull(licensePlate);
        Objects.requireNonNull(createdBy);

        if (capacity <= 0) {

            throw new IllegalArgumentException("Capacity must be positive");

        }

        this.model = model;
        this.make = make;
        this.licensePlate = licensePlate;
        this.type = type;
        this.status = VehicleStatus.ACTIVE;
        this.capacity = capacity;
        this.mileageHistory = new ArrayList<>();

        updateMileage(
                currentMileage,
                MileageEntrySource.INITIAL_REGISTRATION,
                createdBy,
                "Initial registration");

    }

    public static Vehicle create(String model, String make, String licensePlate, VehicleType type, Integer capacity,
            Integer currentMileage,
            User createdBy) {

        return new Vehicle(model, make, licensePlate, type, capacity,
                currentMileage,
                createdBy);

    }

    public void activate() {

        this.status = VehicleStatus.ACTIVE;

    }

    public void deactivate() {

        this.status = VehicleStatus.INACTIVE;

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

    public boolean isActive() {

        return this.status == VehicleStatus.ACTIVE;

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

    @Override
    public String toString() {
        return this.make + " " + this.model + "[" + this.licensePlate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vehicle other = (Vehicle) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
