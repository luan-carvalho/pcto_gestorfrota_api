package br.edu.ifto.gestorfrotaapi.authentication.dto;

public record UserUpdateDto(
                String name,
                String registration,
                Long roleId) {

}
