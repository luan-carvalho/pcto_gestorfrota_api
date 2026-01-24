package br.edu.ifto.gestorfrotaapi.vehicleUsage.model;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.MileageEntrySource;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class VehicleUsage {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "vehicle_request_id", nullable = false, unique = true)
    private VehicleRequest vehicleRequest;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private User driver;

    private VehicleUsageStatus status;

    private LocalDateTime checkInAt;
    private LocalDateTime checkOutAt;
    private Integer mileageStart;
    private Integer mileageEnd;

    public VehicleUsage() {
    }

    public VehicleUsage(VehicleRequest vehicleRequest, User driver) {
        this.vehicleRequest = vehicleRequest;
        this.driver = driver;
        this.status = VehicleUsageStatus.NOT_STARTED;
    }

    public void checkIn(Integer mileageStart) {

        if (this.checkInAt != null) {
            throw new IllegalStateException("Vehicle already checked in");
        }

        if (vehicleRequest.getStatus() != RequestStatus.APPROVED) {
            throw new IllegalStateException("Request must be APPROVED to check in");
        }

        if (mileageStart == null || mileageStart < 0) {
            throw new IllegalArgumentException("Invalid mileage start");
        }

        this.mileageStart = mileageStart;
        this.checkInAt = LocalDateTime.now();
        this.status = VehicleUsageStatus.STARTED;

    }

    public void checkOut(Integer mileageEnd, String notes) {

        if (this.checkInAt == null) {
            throw new IllegalStateException("Cannot check out before check in");
        }

        if (this.checkOutAt != null) {
            throw new IllegalStateException("Vehicle already checked out");
        }

        if (mileageEnd < mileageStart) {
            throw new IllegalArgumentException(
                    "Mileage end cannot be lower than mileage start");
        }

        this.mileageEnd = mileageEnd;
        this.checkOutAt = LocalDateTime.now();
        this.vehicleRequest.getVehicle().updateMileage(
                mileageEnd,
                MileageEntrySource.USAGE_CHECKOUT,
                driver,
                notes != null ? notes : "Checkout from  Vehicle Request #" + vehicleRequest.getId());
        this.status = VehicleUsageStatus.FINISHED;

    }

    public void cancel() {

        // add more logic later

        this.status = VehicleUsageStatus.CANCELED;

    }

    public Long getId() {
        return id;
    }

    public VehicleRequest getVehicleRequest() {
        return vehicleRequest;
    }

    public User getDriver() {
        return driver;
    }

    public LocalDateTime getCheckInAt() {
        return checkInAt;
    }

    public LocalDateTime getCheckOutAt() {
        return checkOutAt;
    }

    public Integer getMileageStart() {
        return mileageStart;
    }

    public Integer getMileageEnd() {
        return mileageEnd;
    }

    public VehicleUsageStatus getStatus() {
        return status;
    }

}
