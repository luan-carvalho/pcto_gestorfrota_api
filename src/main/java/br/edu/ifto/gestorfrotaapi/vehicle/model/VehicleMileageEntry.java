package br.edu.ifto.gestorfrotaapi.vehicle.model;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.MileageEntrySource;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class VehicleMileageEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Vehicle vehicle;

    private Integer mileage;

    @ManyToOne
    @JoinColumn(name = "recorded_by")
    private User recordedBy;

    private LocalDateTime recordedAt;

    private String obs;

    @Enumerated(EnumType.STRING)
    private MileageEntrySource source;

    protected VehicleMileageEntry() {
    }

    public VehicleMileageEntry(Vehicle vehicle, Integer mileage, User recordedBy, LocalDateTime recordedAt, String obs,
            MileageEntrySource source) {
        this.vehicle = vehicle;
        this.mileage = mileage;
        this.recordedBy = recordedBy;
        this.recordedAt = recordedAt;
        this.obs = obs;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Integer getMileage() {
        return mileage;
    }

    public User getRecordedBy() {
        return recordedBy;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public String getObs() {
        return obs;
    }

    public MileageEntrySource getSource() {
        return source;
    }

}
