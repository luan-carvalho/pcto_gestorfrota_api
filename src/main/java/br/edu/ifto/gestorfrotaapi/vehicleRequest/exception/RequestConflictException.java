package br.edu.ifto.gestorfrotaapi.vehicleRequest.exception;

import br.edu.ifto.gestorfrotaapi.vehicleRequest.model.valueObjects.UsagePeriod;

public class RequestConflictException extends RuntimeException {

    private UsagePeriod period;

    public RequestConflictException(UsagePeriod period) {

        super("The vehicle is reserved or in use.");
        this.period = period;
    }

    public UsagePeriod getPeriod() {
        return period;
    }

}
