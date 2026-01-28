package br.edu.ifto.gestorfrotaapi.authentication.command;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;

public record UserCreateCommand(
        String registration,
        String name,
        List<Role> roles) {

}
