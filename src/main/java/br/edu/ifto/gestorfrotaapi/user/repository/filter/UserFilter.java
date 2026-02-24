package br.edu.ifto.gestorfrotaapi.user.repository.filter;

import br.edu.ifto.gestorfrotaapi.user.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.user.model.enums.UserStatus;

public record UserFilter(

        Long id,
        String name,
        String cpf,
        UserStatus status,
        Role role

) {

}
