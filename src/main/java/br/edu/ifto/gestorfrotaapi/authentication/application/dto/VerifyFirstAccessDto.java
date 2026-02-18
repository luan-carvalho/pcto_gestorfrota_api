package br.edu.ifto.gestorfrotaapi.authentication.application.dto;

import br.edu.ifto.gestorfrotaapi.authentication.application.command.CheckUserFirstAccesCommand;
import br.edu.ifto.gestorfrotaapi.user.domain.valueObjects.Cpf;
import jakarta.validation.constraints.NotBlank;

public record VerifyFirstAccessDto(
        @NotBlank String cpf,
        @NotBlank String token) {

    public CheckUserFirstAccesCommand toCommand() {

        return new CheckUserFirstAccesCommand(
                new Cpf(cpf),
                token);

    }

}
