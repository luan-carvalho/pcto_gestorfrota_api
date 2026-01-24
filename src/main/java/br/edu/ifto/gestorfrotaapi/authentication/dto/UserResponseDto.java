package br.edu.ifto.gestorfrotaapi.authentication.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;

public record UserResponseDto(
                Long id,
                String registration,
                String name,
                UserStatus status,
                List<Role> roles) {

}
