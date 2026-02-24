package br.edu.ifto.gestorfrotaapi.user.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.user.commands.UserUpdateCommand;
import br.edu.ifto.gestorfrotaapi.user.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.user.model.valueObjects.Cpf;

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
