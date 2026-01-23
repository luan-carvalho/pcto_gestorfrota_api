package br.edu.ifto.gestorfrotaapi.vehicleUsage.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestAction;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleRequestPurpose;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;

@Entity
public class VehicleRequest {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private User driver;

    @OneToMany(mappedBy = "vehicleRequest", cascade = CascadeType.ALL)
    private List<VehicleRequestHistory> history = new ArrayList<>();

    @OneToOne(mappedBy = "vehicleRequest", cascade = CascadeType.ALL)
    private VehicleUsage vehicleUsage;

    @Enumerated(EnumType.STRING)
    private RequestPriority priority;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private String processNumber;
    private LocalDateTime createdAt;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private VehicleRequestPurpose purpose;

    public VehicleRequest() {

    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public VehicleRequest(User requester, Vehicle vehicle, RequestPriority priority, String processNumber,
            LocalDateTime startDateTime, LocalDateTime endDateTime, VehicleRequestPurpose purpose) {

        this.requester = requester;
        this.vehicle = vehicle;
        this.priority = priority;
        this.processNumber = processNumber;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.purpose = purpose;
        this.status = RequestStatus.CREATED;

        this.history.add(new VehicleRequestHistory(
                this,
                requester,
                RequestAction.CREATED,
                this.createdAt,
                "Request created"));

    }

    public void approve(User approvedBy, User driver, String notes) {

        if (this.status != RequestStatus.CREATED) {
            throw new IllegalStateException(
                    "Request cannot be approved from status " + this.status);
        }

        this.status = RequestStatus.APPROVED;
        this.driver = driver;
        this.history.add(new VehicleRequestHistory(
                this,
                approvedBy,
                RequestAction.APPROVED,
                LocalDateTime.now(),
                notes));

    }

    public void reject(User rejectedBy, String notes) {

        this.status = RequestStatus.REJECTED;
        this.history.add(new VehicleRequestHistory(
                this,
                rejectedBy,
                RequestAction.REJECTED,
                LocalDateTime.now(),
                notes));

    }

    public void cancel(User canceledBy, String notes) {

        this.status = RequestStatus.CANCELED;
        this.history.add(new VehicleRequestHistory(
                this,
                canceledBy,
                RequestAction.CANCELED,
                LocalDateTime.now(),
                notes));

    }

    public Long getId() {
        return id;
    }

    public User getRequester() {
        return requester;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public User getDriver() {
        return driver;
    }

    public List<VehicleRequestHistory> getHistory() {
        return history;
    }

    public VehicleUsage getVehicleUsage() {
        return vehicleUsage;
    }

    public RequestPriority getPriority() {
        return priority;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getProcessNumber() {
        return processNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public VehicleRequestPurpose getPurpose() {
        return purpose;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

}
