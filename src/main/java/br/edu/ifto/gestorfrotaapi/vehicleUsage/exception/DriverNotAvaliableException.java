package br.edu.ifto.gestorfrotaapi.vehicleUsage.exception;

import br.edu.ifto.gestorfrotaapi.user.model.User;

public class DriverNotAvaliableException extends RuntimeException {

    private User driver;

    public DriverNotAvaliableException(User driver) {

        super(driver.getName() + " is not avaliable in the requested period.");

    }

    public User getDriver() {
        return driver;
    }

}
