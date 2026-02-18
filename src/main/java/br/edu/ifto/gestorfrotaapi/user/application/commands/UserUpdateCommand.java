package br.edu.ifto.gestorfrotaapi.user.application.commands;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.user.domain.enums.Role;
import br.edu.ifto.gestorfrotaapi.user.domain.valueObjects.Cpf;

public record UserUpdateCommand(
        Long userId,
        String name,
        Cpf cpf,
        List<Role> roles) {

}
