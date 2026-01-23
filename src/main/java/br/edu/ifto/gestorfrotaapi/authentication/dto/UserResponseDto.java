package br.edu.ifto.gestorfrotaapi.authentication.dto;

import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;

public record UserResponseDto(
        Long id,
        String registration,
        String name,
        UserStatus status,
        String role) {

}
