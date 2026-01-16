package br.edu.ifto.gestorfrotaapi.authentication.service;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.edu.ifto.gestorfrotaapi.authentication.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.authentication.model.CustomUserDetails;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.repository.UserRepository;

@Service
public class IUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public IUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String registration) {

        User user = repository.findByRegistration(registration)
                .orElseThrow(() -> new UserNotFoundException(registration));

        if (user.isInactive()) {

            throw new DisabledException("This user doesn't have access to the system!");

        }

        return new CustomUserDetails(user);

    }
}
