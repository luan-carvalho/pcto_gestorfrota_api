package br.edu.ifto.gestorfrotaapi.authentication.command;

import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;

public record LoginCommand(
        Cpf cpf,
        String password) {

}
