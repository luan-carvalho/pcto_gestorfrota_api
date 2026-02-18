package br.edu.ifto.gestorfrotaapi.authentication.application.command;

public record UpdatePasswordCommand(
        String currentPassword,
        String newPassword) {

}
