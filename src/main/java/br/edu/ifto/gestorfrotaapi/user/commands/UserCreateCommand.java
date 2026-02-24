package br.edu.ifto.gestorfrotaapi.user.commands;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.user.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.user.model.valueObjects.Cpf;

public record UserCreateCommand(
        Cpf cpf,
        String name,
        List<Role> roles) {

}
