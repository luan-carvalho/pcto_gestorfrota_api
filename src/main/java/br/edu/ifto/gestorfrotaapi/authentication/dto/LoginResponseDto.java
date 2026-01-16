package br.edu.ifto.gestorfrotaapi.authentication.dto;

public record LoginResponseDto(
        String token,
        String type,
        String username,
        String roleDescription) {

}
