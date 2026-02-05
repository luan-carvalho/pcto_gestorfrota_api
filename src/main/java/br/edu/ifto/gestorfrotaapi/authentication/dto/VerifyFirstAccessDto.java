package br.edu.ifto.gestorfrotaapi.authentication.dto;

import br.edu.ifto.gestorfrotaapi.authentication.command.CheckUserFirstAccesCommand;
import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;
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
