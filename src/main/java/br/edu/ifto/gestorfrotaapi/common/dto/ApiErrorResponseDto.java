package br.edu.ifto.gestorfrotaapi.common.dto;

import java.time.LocalDateTime;

public record ApiErrorResponseDto(
        int status,
        String error,
        String message,
        LocalDateTime instant) {

}
