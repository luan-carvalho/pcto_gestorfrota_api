package br.edu.ifto.gestorfrotaapi.vehicleUsage.model;

import java.time.LocalDateTime;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.MileageEntrySource;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.CheckInException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.exception.CheckOutException;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class VehicleUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "vehicle_request_id", nullable = false, unique = true)
    private VehicleRequest request;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private User driver;

    @Enumerated(EnumType.STRING)
    private VehicleUsageStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private LocalDateTime checkInAt;
    private LocalDateTime checkOutAt;
    private Integer mileageStart;
    private Integer mileageEnd;

    protected VehicleUsage() {
    }

    @Builder
    private VehicleUsage(VehicleRequest request, User driver, Vehicle vehicle) {
        this.request = request;
        this.driver = driver;
        this.status = VehicleUsageStatus.NOT_STARTED;
        this.vehicle = vehicle;
    }

    public void checkIn(Integer mileageStart) {

        if (this.checkInAt != null) {
            throw new CheckInException("Vehicle usaged already checked-in");
        }

        if (request.getStatus() != RequestStatus.APPROVED) {
            throw new CheckInException("Request must be APPROVED to check in");
        }

        if (mileageStart == null || mileageStart < 0) {
            throw new CheckInException("Invalid mileage start");
        }

        this.mileageStart = mileageStart;
        this.checkInAt = LocalDateTime.now();
        this.status = VehicleUsageStatus.STARTED;

    }

    public void checkOut(Integer mileageEnd, String notes) {

        if (this.checkInAt == null) {
            throw new CheckOutException("Cannot check out before check in");
        }

        if (this.checkOutAt != null) {
            throw new CheckOutException("Vehicle usaged already checked-out");
        }

        if (mileageEnd < mileageStart) {
            throw new CheckOutException(
                    "Mileage end cannot be lower than mileage start");
        }

        this.mileageEnd = mileageEnd;
        this.checkOutAt = LocalDateTime.now();
        this.vehicle.updateMileage(
                mileageEnd,
                MileageEntrySource.USAGE_CHECKOUT,
                driver,
                notes != null ? notes : "Checkout from  Vehicle Request #" + request.getId());
        this.status = VehicleUsageStatus.FINISHED;

    }

    public void cancel() {

        this.status = VehicleUsageStatus.CANCELED;

    }

}
