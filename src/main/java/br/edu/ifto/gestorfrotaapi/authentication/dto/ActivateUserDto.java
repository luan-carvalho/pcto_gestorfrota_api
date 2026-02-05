package br.edu.ifto.gestorfrotaapi.authentication.dto;

import br.edu.ifto.gestorfrotaapi.authentication.command.ActivateUserCommand;
import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;
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
