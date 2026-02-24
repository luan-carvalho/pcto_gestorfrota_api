package br.edu.ifto.gestorfrotaapi.authentication.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifto.gestorfrotaapi.authentication.command.ActivateUserCommand;
import br.edu.ifto.gestorfrotaapi.authentication.command.CheckUserFirstAccesCommand;
import br.edu.ifto.gestorfrotaapi.authentication.command.LoginCommand;
import br.edu.ifto.gestorfrotaapi.authentication.command.UpdatePasswordCommand;
import br.edu.ifto.gestorfrotaapi.authentication.dto.LoginResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.mapper.UserMapper;
import br.edu.ifto.gestorfrotaapi.infra.security.service.TokenService;
import br.edu.ifto.gestorfrotaapi.infra.security.util.SecurityUtils;
import br.edu.ifto.gestorfrotaapi.user.exception.InvalidTokenException;
import br.edu.ifto.gestorfrotaapi.user.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.user.exception.WrongPasswordException;
import br.edu.ifto.gestorfrotaapi.user.model.User;
import br.edu.ifto.gestorfrotaapi.user.model.enums.UserStatus;
import br.edu.ifto.gestorfrotaapi.user.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final UserMapper mapper;

    public AuthenticationService(UserRepository userRepo, PasswordEncoder passwordEncoder, TokenService tokenService,
            AuthenticationManager authManager, UserMapper mapper) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authManager = authManager;
        this.mapper = mapper;
    }

    public LoginResponseDto login(LoginCommand cmd) {

        var authToken = new UsernamePasswordAuthenticationToken(cmd.cpf(), cmd.password());
        var auth = authManager.authenticate(authToken);
        User user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user.getCpf().getValue());

        return mapper.toLoginDto(user, token);

    }

    public void verifyFirstAccess(CheckUserFirstAccesCommand cmd) {

        User user = userRepo.findByCpf(cmd.cpf())
                .orElseThrow(() -> new UserNotFoundException());

        if (!user.verifyFirstAccess(cmd.token())) {

            throw new InvalidTokenException();

        }

    }

    public void activateFirstAccess(ActivateUserCommand cmd) {

        User user = userRepo.findByCpfAndStatus(cmd.cpf(), UserStatus.FIRST_ACCESS)
                .orElseThrow(() -> new UserNotFoundException());

        if (!user.verifyFirstAccess(cmd.token())) {
            throw new InvalidTokenException();
        }

        String encodedPassword = passwordEncoder.encode(cmd.password());
        user.activateFirstAccess(encodedPassword);
    }

    public void updateOwnPassword(UpdatePasswordCommand cmd) {

        User detachedUser = SecurityUtils.currentUser();
        User logged = userRepo.findById(detachedUser.getId()).orElseThrow(() -> new UserNotFoundException());

        if (!passwordEncoder.matches(cmd.currentPassword(), logged.getPassword())) {

            throw new WrongPasswordException();

        }

        logged.updatePassword(passwordEncoder.encode(cmd.newPassword()));

    }

}
