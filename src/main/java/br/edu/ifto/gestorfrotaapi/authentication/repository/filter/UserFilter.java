package br.edu.ifto.gestorfrotaapi.authentication.repository.filter;

import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;

public record UserFilter(

        Long id,
        String name,
        String cpf,
        UserStatus status,
        Role role

) {

}
