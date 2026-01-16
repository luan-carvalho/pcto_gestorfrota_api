package br.edu.ifto.gestorfrotaapi.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDto(
        @NotBlank String name,
        @NotBlank String registration,
        @NotNull Long roleId) {

}
