package br.edu.ifto.gestorfrotaapi.authentication.dto;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;

public record UserUpdateRequestDto(
        String name,
        String registration,
        List<Role> roles) {

}
