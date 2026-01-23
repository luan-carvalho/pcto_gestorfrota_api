package br.edu.ifto.gestorfrotaapi.vehicleUsage.model;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestAction;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class VehicleRequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_request_id")
    private VehicleRequest request;

    @ManyToOne(optional = false)
    @JoinColumn(name = "performed_by")
    private User performedBy;

    private RequestAction action;

    private String notes;

    private LocalDateTime performedAt;

    public VehicleRequestHistory(VehicleRequest request, User performedBy, RequestAction action,
            LocalDateTime performedAt, String notes) {
        this.request = request;
        this.performedBy = performedBy;
        this.action = action;
        this.performedAt = performedAt;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public VehicleRequest getRequest() {
        return request;
    }

    public User getPerformedBy() {
        return performedBy;
    }

    public RequestAction getAction() {
        return action;
    }

    public LocalDateTime getPerformedAt() {
        return performedAt;
    }

    public String getNotes() {
        return notes;
    }

}
