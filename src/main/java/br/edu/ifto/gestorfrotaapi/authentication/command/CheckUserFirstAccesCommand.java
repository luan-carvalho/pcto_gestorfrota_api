package br.edu.ifto.gestorfrotaapi.authentication.command;

import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;

public record CheckUserFirstAccesCommand(
        Cpf cpf,
        String token) {

}
