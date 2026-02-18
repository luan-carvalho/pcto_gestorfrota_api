package br.edu.ifto.gestorfrotaapi.user.application.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.user.application.commands.UserCreateCommand;
import br.edu.ifto.gestorfrotaapi.user.domain.enums.Role;
import br.edu.ifto.gestorfrotaapi.user.domain.valueObjects.Cpf;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequestDto(
        @NotBlank String cpf,
        @NotBlank String name,
        @NotNull List<Role> roles

) {

    public UserCreateCommand toCommand() {

        return new UserCreateCommand(
                new Cpf(cpf),
                name,
                roles);

    }

}
