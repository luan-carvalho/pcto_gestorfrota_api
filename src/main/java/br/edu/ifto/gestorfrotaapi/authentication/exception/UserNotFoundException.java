package br.edu.ifto.gestorfrotaapi.authentication.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final Long id;
    private final String registration;

    public UserNotFoundException(Long id) {

        super("User with id " + id + " was not found");
        this.id = id;
        this.registration = null;
    }

    public UserNotFoundException(String registration) {

        super("User with registration " + registration + " was not found");
        this.registration = registration;
        this.id = null;
    }

}
