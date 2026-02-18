package br.edu.ifto.gestorfrotaapi.user.domain.exception;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {

        super("Wrong password");

    }

}
