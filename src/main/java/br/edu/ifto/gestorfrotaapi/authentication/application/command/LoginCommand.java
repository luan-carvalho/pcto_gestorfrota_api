package br.edu.ifto.gestorfrotaapi.authentication.application.command;

import br.edu.ifto.gestorfrotaapi.user.domain.valueObjects.Cpf;

public record LoginCommand(
        Cpf cpf,
        String password) {

}
