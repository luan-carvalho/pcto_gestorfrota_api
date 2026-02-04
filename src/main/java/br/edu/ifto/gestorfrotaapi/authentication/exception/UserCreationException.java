package br.edu.ifto.gestorfrotaapi.authentication.exception;

public class UserCreationException extends RuntimeException {

    String msg;

    public UserCreationException(String msg) {

        super(msg);

    }

}
