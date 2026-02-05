package br.edu.ifto.gestorfrotaapi.authentication.exception;

import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;

public class RoleNotFoundException extends RuntimeException {

    private final Long id;
    private final Cpf cpf;

    public RoleNotFoundException(Long id) {

        super("Role with id " + id + " was not found");
        this.id = id;
        this.cpf = null;
    }

    public Long getId() {
        return id;
    }

    public String getcpf() {
        return cpf.getFormatted();
    }

}
