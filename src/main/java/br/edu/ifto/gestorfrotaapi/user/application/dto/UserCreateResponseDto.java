package br.edu.ifto.gestorfrotaapi.user.application.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.user.domain.enums.Role;

public record UserCreateResponseDto(
        Long id,
        String cpf,
        String name,
        List<Role> roles,
        String firstAccessToken) {

}
