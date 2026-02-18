package br.edu.ifto.gestorfrotaapi.user.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.edu.ifto.gestorfrotaapi.user.domain.enums.UserStatus;
import br.edu.ifto.gestorfrotaapi.user.domain.model.User;
import br.edu.ifto.gestorfrotaapi.user.domain.valueObjects.Cpf;

public interface JpaUserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByCpf(Cpf cpf);

    Optional<User> findByCpfAndStatus(Cpf cpf, UserStatus status);

    boolean existsByCpf(Cpf cpf);

}
