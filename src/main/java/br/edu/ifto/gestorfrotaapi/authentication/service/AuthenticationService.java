package br.edu.ifto.gestorfrotaapi.authentication.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifto.gestorfrotaapi.authentication.exception.InvalidTokenException;
import br.edu.ifto.gestorfrotaapi.authentication.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.authentication.exception.WrongPasswordException;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;
import br.edu.ifto.gestorfrotaapi.authentication.repository.UserRepository;
import br.edu.ifto.gestorfrotaapi.authentication.util.SecurityUtils;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthenticationService(UserRepository userRepo, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public String generateToken(String registration) {

        return tokenService.generateToken(registration);

    }

    public void verifyFirstAccess(String registration, String token) {

        User user = userRepo.findByRegistration(registration)
                .orElseThrow(() -> new UserNotFoundException(registration));

        if (!user.verifyFirstAccess(token)) {

            throw new InvalidTokenException();

        }

    }

    public void activateFirstAccess(String registration, String token, String rawPassword) {

        User user = userRepo.findByRegistrationAndStatus(registration, UserStatus.FIRST_ACESS)
                .orElseThrow(() -> new UserNotFoundException(registration));

        if (!user.verifyFirstAccess(token)) {
            throw new InvalidTokenException();
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.activateFirstAccess(encodedPassword);
    }

    @PreAuthorize("isAuthenticated()")
    public void updateOwnPassword(String currentPassword, String newPassword) {

        User logged = SecurityUtils.currentUser();

        if (!passwordEncoder.matches(currentPassword, logged.getPassword())) {

            throw new WrongPasswordException();

        }

        logged.updatePassword(passwordEncoder.encode(newPassword));

    }

}
