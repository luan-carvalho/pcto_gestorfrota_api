package br.edu.ifto.gestorfrotaapi.authentication.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
                SELECT DISTINCT u
                FROM User u
                WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', CAST(:name AS STRING), '%')))
                AND (:status is NULL OR u.status = :status)
                ORDER BY u.name
            """)
    Page<User> searchWithOptionalFilters(Pageable pageable, String name, UserStatus status);

    Optional<User> findByCpf(String cpf);

    Optional<User> findByCpfAndStatus(String cpf, UserStatus status);

    boolean existsBycpf(String cpf);

}
