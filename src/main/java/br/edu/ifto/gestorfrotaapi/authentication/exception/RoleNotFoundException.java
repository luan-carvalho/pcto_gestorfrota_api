package br.edu.ifto.gestorfrotaapi.authentication.exception;

public class RoleNotFoundException extends RuntimeException {

    private final Long id;
    private final String registration;

    public RoleNotFoundException(Long id) {

        super("Role with id " + id + " was not found");
        this.id = id;
        this.registration = null;
    }

    public Long getId() {
        return id;
    }

    public String getRegistration() {
        return registration;
    }

}
