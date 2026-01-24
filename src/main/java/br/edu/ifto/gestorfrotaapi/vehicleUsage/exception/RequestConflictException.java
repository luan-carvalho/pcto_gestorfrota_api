package br.edu.ifto.gestorfrotaapi.vehicleUsage.exception;

import java.time.LocalDateTime;

public class RequestConflictException extends RuntimeException {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public RequestConflictException(LocalDateTime startDateTime, LocalDateTime endDateTime) {

        super("The vehicle is already in use or there is an approved request for this vehicle");
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;

    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

}
