package br.edu.ifto.gestorfrotaapi.infra.security.service;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.edu.ifto.gestorfrotaapi.user.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.user.model.User;
import br.edu.ifto.gestorfrotaapi.user.model.enums.UserStatus;
import br.edu.ifto.gestorfrotaapi.user.model.valueObjects.Cpf;
import br.edu.ifto.gestorfrotaapi.user.repository.UserRepository;

@Service
public class IUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public IUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) {

        System.out.println(cpf);

        User user = repository.findByCpfAndStatus(new Cpf(cpf), UserStatus.ACTIVE)
                .orElseThrow(() -> new UserNotFoundException());

        if (!user.isActive()) {

            throw new DisabledException("This user doesn't have access to the system!");

        }

        return user;

    }
}
