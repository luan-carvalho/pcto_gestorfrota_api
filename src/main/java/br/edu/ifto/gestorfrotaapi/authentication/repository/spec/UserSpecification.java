package br.edu.ifto.gestorfrotaapi.authentication.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;

public class UserSpecification {

    public static Specification<User> hasId(Long id) {

        return (root, query, cb) -> id == null ? null : cb.equal(root.get("id"), id);

    }

    public static Specification<User> hasCpf(String cpf) {

        return (root, query, cb) -> {
            if (cpf == null || cpf.isBlank()) {
                return null;
            }
            return cb.equal(root.get("cpf").get("value"), cpf);
        };
    }

    public static Specification<User> hasName(String name) {

        return (root, query, cb) -> {

            if (name == null || name.isBlank()) {

                return null;

            }

            var searchName = cb.lower(root.get("name"));
            return cb.like(searchName, "%" + name.toLowerCase() + "%");

        };
    }

    public static Specification<User> hasStatus(UserStatus status) {

        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);

    }

    public static Specification<User> hasRole(Role role) {

        return (root, query, cb) -> role == null ? null : cb.isMember(role, root.get("role"));

    }

}
