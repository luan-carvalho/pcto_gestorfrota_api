package br.edu.ifto.gestorfrotaapi.user.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.user.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.user.model.enums.UserStatus;

public record UserResponseDto(
                Long id,
                String cpf,
                String name,
                UserStatus status,
                List<Role> roles) {

}
