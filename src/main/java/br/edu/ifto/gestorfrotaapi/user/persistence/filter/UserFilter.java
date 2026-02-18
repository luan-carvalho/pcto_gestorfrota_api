package br.edu.ifto.gestorfrotaapi.user.persistence.filter;

import br.edu.ifto.gestorfrotaapi.user.domain.enums.Role;
import br.edu.ifto.gestorfrotaapi.user.domain.enums.UserStatus;

public record UserFilter(

        Long id,
        String name,
        String cpf,
        UserStatus status,
        Role role

) {

}
