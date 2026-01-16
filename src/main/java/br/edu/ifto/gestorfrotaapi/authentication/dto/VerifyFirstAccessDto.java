package br.edu.ifto.gestorfrotaapi.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyFirstAccessDto(
                @NotBlank String registration,
                @NotBlank String token) {

}
