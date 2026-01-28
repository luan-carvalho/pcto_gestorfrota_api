package br.edu.ifto.gestorfrotaapi.authentication.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {

        super("Invalid first access token or user already activated.");

    }

}
