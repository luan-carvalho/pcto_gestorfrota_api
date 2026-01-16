package br.edu.ifto.gestorfrotaapi.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateDto(
        @NotBlank String registration,
        @NotBlank String name,
        @NotNull Long roleId

) {

}
