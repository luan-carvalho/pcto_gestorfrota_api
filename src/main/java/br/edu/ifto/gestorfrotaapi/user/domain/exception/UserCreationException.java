package br.edu.ifto.gestorfrotaapi.user.domain.exception;

public class UserCreationException extends RuntimeException {

    String msg;

    public UserCreationException(String msg) {

        super(msg);

    }

}
