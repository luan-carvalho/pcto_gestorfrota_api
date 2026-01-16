package br.edu.ifto.gestorfrotaapi.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActivateFirstAccessDto(
        @NotBlank String registration,
        @NotBlank String token,
        @NotBlank @Size(min = 8, message = "The password should be at least 8 characters long") String password) {

}
