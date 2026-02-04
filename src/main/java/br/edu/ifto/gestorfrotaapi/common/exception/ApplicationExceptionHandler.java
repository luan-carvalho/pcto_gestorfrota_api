package br.edu.ifto.gestorfrotaapi.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.edu.ifto.gestorfrotaapi.common.dto.ApiErrorResponseDto;
import br.edu.ifto.gestorfrotaapi.vehicle.exception.VehicleNotFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponseDto> handleGenericException(RuntimeException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiErrorResponseDto error = new ApiErrorResponseDto(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(error, status);

    }

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

    // @ExceptionHandler(UserNotFoundException.class)
    // public ResponseEntity<ApiErrorResponseDto>
    // handleUserNotFoundException(UserNotFoundException e) {

    // HttpStatus status = HttpStatus.NOT_FOUND;
    // ApiErrorResponseDto error = new ApiErrorResponseDto(
    // status.value(),
    // status.getReasonPhrase(),
    // e.getMessage(),
    // LocalDateTime.now());

    // return new ResponseEntity<>(error, status);

    // }

    // @ExceptionHandler(UserCreationException.class)
    // public ResponseEntity<ApiErrorResponseDto>
    // handleUserCreationException(UserCreationException e) {

    // HttpStatus status = HttpStatus.BAD_REQUEST;
    // ApiErrorResponseDto error = new ApiErrorResponseDto(
    // status.value(),
    // status.getReasonPhrase(),
    // e.getMessage(),
    // LocalDateTime.now());

    // return new ResponseEntity<>(error, status);

    // }

    // @ExceptionHandler(DisabledException.class)
    // public ResponseEntity<ApiErrorResponseDto>
    // handleDisabledException(DisabledException e) {

    // HttpStatus status = HttpStatus.BAD_REQUEST;
    // ApiErrorResponseDto error = new ApiErrorResponseDto(
    // status.value(),
    // status.getReasonPhrase(),
    // e.getMessage(),
    // LocalDateTime.now());

    // return new ResponseEntity<>(error, status);

    // }

    // @ExceptionHandler(WrongPasswordException.class)
    // public ResponseEntity<ApiErrorResponseDto>
    // handleWrongPasswordException(WrongPasswordException e) {

    // HttpStatus status = HttpStatus.BAD_REQUEST;
    // ApiErrorResponseDto error = new ApiErrorResponseDto(
    // status.value(),
    // status.getReasonPhrase(),
    // e.getMessage(),
    // LocalDateTime.now());

    // return new ResponseEntity<>(error, status);

    // }

    // @ExceptionHandler(InvalidTokenException.class)
    // public ResponseEntity<ApiErrorResponseDto>
    // handleInvalidTokenException(InvalidTokenException e) {

    // HttpStatus status = HttpStatus.BAD_REQUEST;
    // ApiErrorResponseDto error = new ApiErrorResponseDto(
    // status.value(),
    // status.getReasonPhrase(),
    // e.getMessage(),
    // LocalDateTime.now());

    // return new ResponseEntity<>(error, status);

    // }

    // @ExceptionHandler(StatusUpdateException.class)
    // public ResponseEntity<ApiErrorResponseDto>
    // handleUserStatusUpdateException(StatusUpdateException e) {

    // HttpStatus status = HttpStatus.BAD_REQUEST;
    // ApiErrorResponseDto error = new ApiErrorResponseDto(
    // status.value(),
    // status.getReasonPhrase(),
    // e.getMessage(),
    // LocalDateTime.now());

    // return new ResponseEntity<>(error, status);

    // }

    // @ExceptionHandler(VehicleCreationException.class)
    // public ResponseEntity<ApiErrorResponseDto>
    // handleVehicleCreationException(VehicleCreationException e) {

    // HttpStatus status = HttpStatus.BAD_REQUEST;
    // ApiErrorResponseDto error = new ApiErrorResponseDto(
    // status.value(),
    // status.getReasonPhrase(),
    // e.getMessage(),
    // LocalDateTime.now());

    // return new ResponseEntity<>(error, status);

    // }

    // @ExceptionHandler(VehicleRequestCreationException.class)
    // public ResponseEntity<ApiErrorResponseDto>
    // handleVehicleRequestCreationException(
    // VehicleRequestCreationException e) {

    // HttpStatus status = HttpStatus.BAD_REQUEST;
    // ApiErrorResponseDto error = new ApiErrorResponseDto(
    // status.value(),
    // status.getReasonPhrase(),
    // e.getMessage(),
    // LocalDateTime.now());

    // return new ResponseEntity<>(error, status);

    // }

    // @ExceptionHandler(VehicleRequestApprovalException.class)
    // public ResponseEntity<ApiErrorResponseDto>
    // handleVehicleRequestApprovalException(
    // VehicleRequestApprovalException e) {

    // HttpStatus status = HttpStatus.BAD_REQUEST;
    // ApiErrorResponseDto error = new ApiErrorResponseDto(
    // status.value(),
    // status.getReasonPhrase(),
    // e.getMessage(),
    // LocalDateTime.now());

    // return new ResponseEntity<>(error, status);

    // }

}
