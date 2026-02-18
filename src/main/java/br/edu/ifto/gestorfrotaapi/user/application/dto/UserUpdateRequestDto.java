package br.edu.ifto.gestorfrotaapi.user.application.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.user.application.commands.UserUpdateCommand;
import br.edu.ifto.gestorfrotaapi.user.domain.enums.Role;
import br.edu.ifto.gestorfrotaapi.user.domain.valueObjects.Cpf;

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
