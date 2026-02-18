package br.edu.ifto.gestorfrotaapi.authentication.application.command;

import br.edu.ifto.gestorfrotaapi.user.domain.valueObjects.Cpf;

public record ActivateUserCommand(
        Cpf cpf,
        String token,
        String password) {

}
