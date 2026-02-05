package br.edu.ifto.gestorfrotaapi.authentication.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;

public record UserCreateResponseDto(
        Long id,
        String cpf,
        String name,
        List<Role> roles,
        String firstAccessToken) {

}
