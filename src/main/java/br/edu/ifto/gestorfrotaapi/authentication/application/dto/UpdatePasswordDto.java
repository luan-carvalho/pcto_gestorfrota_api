package br.edu.ifto.gestorfrotaapi.authentication.application.dto;

import br.edu.ifto.gestorfrotaapi.authentication.application.command.UpdatePasswordCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDto(
                @NotBlank String currentPassword,
                @NotBlank @Size(min = 6) String newPassword) {

        public UpdatePasswordCommand toCommand() {

                return new UpdatePasswordCommand(
                                currentPassword,
                                newPassword);

        }

}
