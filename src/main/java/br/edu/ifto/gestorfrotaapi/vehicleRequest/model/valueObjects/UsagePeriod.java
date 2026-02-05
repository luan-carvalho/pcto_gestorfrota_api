package br.edu.ifto.gestorfrotaapi.vehicleRequest.model.valueObjects;

import java.time.LocalDateTime;
import java.util.Objects;

import br.edu.ifto.gestorfrotaapi.vehicleRequest.exception.VehicleRequestCreationException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class UsagePeriod {

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    protected UsagePeriod() {

    }

    public UsagePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {

        Objects.requireNonNull(startDateTime);
        Objects.requireNonNull(endDateTime);

        if (startDateTime.isAfter(endDateTime)) {

            throw new VehicleRequestCreationException("Start date time cannot be after end date time");

        }

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;

    }

}
