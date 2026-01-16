package br.edu.ifto.gestorfrotaapi.authentication.dto;

import br.edu.ifto.gestorfrotaapi.authentication.model.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;

public record UserCreateResponseDto(
                Long id,
                String registration,
                String name,
                UserStatus status,
                Role role,
                String firstAccessToken) {

}
