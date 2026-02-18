package br.edu.ifto.gestorfrotaapi.shared.dto;

import java.time.LocalDateTime;

public record ApiErrorResponseDto(
        int status,
        String error,
        String message,
        LocalDateTime instant) {

}
