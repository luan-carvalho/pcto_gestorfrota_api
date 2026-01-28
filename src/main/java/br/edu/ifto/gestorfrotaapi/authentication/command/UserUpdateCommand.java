package br.edu.ifto.gestorfrotaapi.authentication.command;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;

public record UserUpdateCommand(
        String name,
        String registration,
        List<Role> roles) {

}
