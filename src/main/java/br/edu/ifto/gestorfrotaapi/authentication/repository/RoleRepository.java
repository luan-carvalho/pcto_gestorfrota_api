package br.edu.ifto.gestorfrotaapi.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifto.gestorfrotaapi.authentication.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
