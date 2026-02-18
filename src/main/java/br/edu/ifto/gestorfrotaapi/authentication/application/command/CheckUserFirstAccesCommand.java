package br.edu.ifto.gestorfrotaapi.authentication.application.command;

import br.edu.ifto.gestorfrotaapi.user.domain.valueObjects.Cpf;

public record CheckUserFirstAccesCommand(
        Cpf cpf,
        String token) {

}
