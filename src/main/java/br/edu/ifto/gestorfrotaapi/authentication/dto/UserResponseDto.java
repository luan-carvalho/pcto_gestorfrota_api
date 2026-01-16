package br.edu.ifto.gestorfrotaapi.authentication.dto;

import br.edu.ifto.gestorfrotaapi.authentication.model.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;

public record UserResponseDto(
        Long id,
        String registration,
        String nome,
        UserStatus status,
        Role role) {

}
