package br.edu.ifto.gestorfrotaapi.authentication.application.dto;

import br.edu.ifto.gestorfrotaapi.authentication.application.command.ActivateUserCommand;
import br.edu.ifto.gestorfrotaapi.user.domain.valueObjects.Cpf;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActivateUserDto(
                @NotBlank String cpf,
                @NotBlank String token,
                @NotBlank @Size(min = 8, message = "The password should be at least 8 characters long") String password) {

        public ActivateUserCommand toCommand() {

                return new ActivateUserCommand(
                                new Cpf(cpf),
                                token,
                                password);

        }

}
