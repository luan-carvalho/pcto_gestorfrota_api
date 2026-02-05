package br.edu.ifto.gestorfrotaapi.authentication.command;

import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;

public record ActivateUserCommand(
        Cpf cpf,
        String token,
        String password) {

}
