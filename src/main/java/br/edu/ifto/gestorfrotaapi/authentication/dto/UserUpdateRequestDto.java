package br.edu.ifto.gestorfrotaapi.authentication.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.command.UserUpdateCommand;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;

public record UserUpdateRequestDto(
        String name,
        String cpf,
        List<Role> roles) {
    public UserUpdateCommand toCommand(Long userId) {

        return new UserUpdateCommand(
                userId,
                name,
                cpf == null || cpf.isBlank() ? null : new Cpf(cpf),
                roles);

    }

}
