package br.edu.ifto.gestorfrotaapi.authentication.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.command.UserCreateCommand;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;
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
