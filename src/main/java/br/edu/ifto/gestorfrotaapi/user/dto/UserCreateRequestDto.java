package br.edu.ifto.gestorfrotaapi.user.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.user.commands.UserCreateCommand;
import br.edu.ifto.gestorfrotaapi.user.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.user.model.valueObjects.Cpf;
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
