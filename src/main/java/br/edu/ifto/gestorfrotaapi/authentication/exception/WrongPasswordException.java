package br.edu.ifto.gestorfrotaapi.authentication.exception;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {

        super("Wrong password");

    }

}
