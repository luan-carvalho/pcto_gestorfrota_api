package br.edu.ifto.gestorfrotaapi.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.edu.ifto.gestorfrotaapi.authentication.exception.StatusUpdateException;
import br.edu.ifto.gestorfrotaapi.authentication.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.common.dto.ApiErrorResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.exception.VehicleNotFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handleVehicleNotFoundException(VehicleNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorResponseDto error = new ApiErrorResponseDto(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(error, status);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handleUserNotFoundException(UserNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorResponseDto error = new ApiErrorResponseDto(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(error, status);

    }

    @ExceptionHandler(StatusUpdateException.class)
    public ResponseEntity<ApiErrorResponseDto> handleUserNotFoundException(StatusUpdateException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiErrorResponseDto error = new ApiErrorResponseDto(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(error, status);

    }

}
