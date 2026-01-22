package br.edu.ifto.gestorfrotaapi.authentication.dto;

public record UserCreateResponseDto(
        Long id,
        String registration,
        String name,
        String role,
        String firstAccessToken) {

}
