package br.edu.ifto.gestorfrotaapi.user.exception;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {

        super("Wrong password");

    }

}
