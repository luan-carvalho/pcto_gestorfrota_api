package br.edu.ifto.gestorfrotaapi.authentication.command;

public record UpdatePasswordCommand(
        String currentPassword,
        String newPassword) {

}
