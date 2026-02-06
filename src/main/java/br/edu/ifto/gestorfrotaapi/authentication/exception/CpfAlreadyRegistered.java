package br.edu.ifto.gestorfrotaapi.authentication.exception;

import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;

public class CpfAlreadyRegistered extends RuntimeException {

    public CpfAlreadyRegistered(Cpf cpf) {

        super("The CPF " + cpf.getFormatted() + " is already registered");

    }

}
