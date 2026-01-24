package br.edu.ifto.gestorfrotaapi.authentication.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;

public record UserCreateResponseDto(
                Long id,
                String registration,
                String name,
                List<Role> roles,
                String firstAccessToken) {

}
