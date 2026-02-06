package br.edu.ifto.gestorfrotaapi.vehicleRequest.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.exception.VehicleRequestApprovalException;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.RequestAction;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.VehicleRequestPurpose;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.valueObjects.Location;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.valueObjects.UsagePeriod;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class VehicleRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<VehicleRequestHistory> history = new ArrayList<>();

    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL)
    private VehicleUsage vehicleUsage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestPriority priority;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column(nullable = false)
    private String processNumber;
    private LocalDateTime createdAt;

    @Embedded
    private UsagePeriod period;

    @Embedded
    private Location destination;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleRequestPurpose purpose;

    protected VehicleRequest() {

    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder(buildMethodName = "build")
    private VehicleRequest(
            User requester,
            RequestPriority priority,
            String processNumber,
            UsagePeriod period,
            VehicleRequestPurpose purpose,
            String description,
            Location destination) {

        this.requester = Objects.requireNonNull(requester);
        this.priority = Objects.requireNonNull(priority);
        this.processNumber = Objects.requireNonNull(processNumber);
        this.purpose = Objects.requireNonNull(purpose);
        this.period = Objects.requireNonNull(period);
        this.description = Objects.requireNonNull(description);
        this.destination = Objects.requireNonNull(destination);

        this.status = RequestStatus.SENT_TO_MANAGER;
        this.history.add(
                new VehicleRequestHistory(
                        this,
                        requester,
                        RequestAction.CREATED,
                        "Request created"));

    }

    public void approve(User approvedBy, User driver, String notes) {

        if (this.status != RequestStatus.SENT_TO_MANAGER) {
            throw new VehicleRequestApprovalException(
                    "Request cannot be approved from status " + this.status);
        }

        this.status = RequestStatus.APPROVED;
        this.history.add(new VehicleRequestHistory(
                this,
                approvedBy,
                RequestAction.APPROVED,
                notes));

    }

    public void reject(User rejectedBy, String notes) {

        this.status = RequestStatus.REJECTED;
        this.history.add(new VehicleRequestHistory(
                this,
                rejectedBy,
                RequestAction.REJECTED,
                notes));

    }

    public void cancel(User canceledBy, String notes) {

        this.status = RequestStatus.CANCELED;
        this.history.add(new VehicleRequestHistory(
                this,
                canceledBy,
                RequestAction.CANCELED,
                notes));

    }

}
