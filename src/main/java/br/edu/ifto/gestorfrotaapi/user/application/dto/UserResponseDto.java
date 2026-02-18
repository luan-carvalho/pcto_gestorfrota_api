package br.edu.ifto.gestorfrotaapi.user.application.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.user.domain.enums.Role;
import br.edu.ifto.gestorfrotaapi.user.domain.enums.UserStatus;

public record UserResponseDto(
                Long id,
                String cpf,
                String name,
                UserStatus status,
                List<Role> roles) {

}
