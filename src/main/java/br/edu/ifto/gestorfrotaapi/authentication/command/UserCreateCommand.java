package br.edu.ifto.gestorfrotaapi.authentication.command;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;

public record UserCreateCommand(
        Cpf cpf,
        String name,
        List<Role> roles) {

}
