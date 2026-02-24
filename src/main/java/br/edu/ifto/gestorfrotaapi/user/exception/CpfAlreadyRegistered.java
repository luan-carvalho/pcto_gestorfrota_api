package br.edu.ifto.gestorfrotaapi.user.exception;

import br.edu.ifto.gestorfrotaapi.user.model.valueObjects.Cpf;

public class CpfAlreadyRegistered extends RuntimeException {

    public CpfAlreadyRegistered(Cpf cpf) {

        super("The CPF " + cpf.getFormatted() + " is already registered");

    }

}
