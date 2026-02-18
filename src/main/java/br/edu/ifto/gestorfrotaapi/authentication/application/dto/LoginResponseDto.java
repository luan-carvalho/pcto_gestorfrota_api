package br.edu.ifto.gestorfrotaapi.authentication.application.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.user.domain.enums.Role;

public record LoginResponseDto(
        String token,
        String name,
        List<Role> roles) {

    public static final String type = "Bearer";

}
