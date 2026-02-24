package br.edu.ifto.gestorfrotaapi.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.edu.ifto.gestorfrotaapi.user.model.User;
import br.edu.ifto.gestorfrotaapi.user.model.enums.UserStatus;
import br.edu.ifto.gestorfrotaapi.user.model.valueObjects.Cpf;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByCpf(Cpf cpf);

    Optional<User> findByCpfAndStatus(Cpf cpf, UserStatus status);

    boolean existsByCpf(Cpf cpf);

}
