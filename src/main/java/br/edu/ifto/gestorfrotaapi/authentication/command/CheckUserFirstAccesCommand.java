package br.edu.ifto.gestorfrotaapi.authentication.command;

import br.edu.ifto.gestorfrotaapi.user.model.valueObjects.Cpf;

public record CheckUserFirstAccesCommand(
        Cpf cpf,
        String token) {

}
