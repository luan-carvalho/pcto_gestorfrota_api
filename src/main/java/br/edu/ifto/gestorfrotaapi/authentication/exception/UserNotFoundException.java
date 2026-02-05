package br.edu.ifto.gestorfrotaapi.authentication.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {

        super("User with given id/cpf was not found");
    }

}
