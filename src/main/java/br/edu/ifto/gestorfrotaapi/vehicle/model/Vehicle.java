package br.edu.ifto.gestorfrotaapi.vehicle.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicle.exception.UpdateMileageException;
import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.MileageEntrySource;
import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.VehicleStatus;
import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.VehicleType;
import br.edu.ifto.gestorfrotaapi.vehicle.model.valueObjects.LicensePlate;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String make;

    @Embedded
    private LicensePlate licensePlate;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Column(nullable = false)
    private Integer currentMileage;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @OneToMany(mappedBy = "vehicle")
    private List<VehicleMileageEntry> mileageHistory;

    protected Vehicle() {

    }

    @Builder
    private Vehicle(String model, String make, LicensePlate licensePlate,
            Integer currentMileage,
            User createdBy) {

        Objects.requireNonNull(model);
        Objects.requireNonNull(make);
        Objects.requireNonNull(licensePlate);
        Objects.requireNonNull(createdBy);

        this.model = model;
        this.make = make;
        this.licensePlate = licensePlate;
        this.status = VehicleStatus.ACTIVE;
        this.mileageHistory = new ArrayList<>();

        updateMileage(
                currentMileage,
                MileageEntrySource.INITIAL_cpf,
                createdBy,
                "Initial cpf");

    }

    public void activate() {

        this.status = VehicleStatus.ACTIVE;

    }

    public void deactivate() {

        this.status = VehicleStatus.INACTIVE;

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
            throw new UpdateMileageException("Mileage cannot be negative");
        }

        if (notes == null || notes.isBlank()) {
            throw new UpdateMileageException(
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

    public void updateInfo(LicensePlate licensePlate, String make, String model) {

        if (model != null) {
            this.model = model;
        }
        if (make != null) {
            this.make = make;
        }
        if (licensePlate != null) {
            this.licensePlate = licensePlate;
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
