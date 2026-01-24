package br.edu.ifto.gestorfrotaapi.authentication.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateDto(
        @NotBlank String registration,
        @NotBlank String name,
        @NotNull List<Role> roles

) {

}
