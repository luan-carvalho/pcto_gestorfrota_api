package br.edu.ifto.gestorfrotaapi.authentication.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;

public record LoginResponseDto(
        String token,
        String name,
        List<Role> roles) {

    public static final String type = "Bearer";

}
