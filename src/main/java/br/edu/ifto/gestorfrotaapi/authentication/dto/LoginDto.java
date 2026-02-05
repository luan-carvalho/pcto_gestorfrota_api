package br.edu.ifto.gestorfrotaapi.authentication.dto;

import br.edu.ifto.gestorfrotaapi.authentication.command.LoginCommand;
import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
                @NotBlank String cpf,
                @NotBlank String password) {

        public LoginCommand toCommand() {

                return new LoginCommand(
                                new Cpf(cpf),
                                password);

        }

}
