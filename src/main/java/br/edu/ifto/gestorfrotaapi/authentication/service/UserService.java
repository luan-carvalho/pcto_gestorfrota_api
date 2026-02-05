package br.edu.ifto.gestorfrotaapi.authentication.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifto.gestorfrotaapi.authentication.command.UserCreateCommand;
import br.edu.ifto.gestorfrotaapi.authentication.command.UserUpdateCommand;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.authentication.mapper.UserMapper;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.repository.UserRepository;
import br.edu.ifto.gestorfrotaapi.authentication.util.TokenGenerator;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;
    private final UserMapper mapper;

    public UserService(UserRepository userRepo, UserMapper mapper) {
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<UserResponseDto> findAll() {

        return mapper.toResponseDto(userRepo.findAll());

    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserResponseDto findById(Long id) {

        return mapper.toResponseDto(getById(id));

    }

    private User getById(Long id) {

        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException());

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserCreateResponseDto create(UserCreateCommand cmd) {

        return mapper
                .toCreateResponseDto(
                        userRepo.save(
                                User.builder()
                                        .name(cmd.name())
                                        .cpf(cmd.cpf())
                                        .roles(cmd.roles())
                                        .firstAccessToken(TokenGenerator.generateToken())
                                        .build()));

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserResponseDto update(UserUpdateCommand cmd) {

        User existingUser = getById(cmd.userId());
        existingUser.updateInfo(cmd.name(), cmd.roles());
        return mapper.toResponseDto(existingUser);

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void activate(Long id) {

        User user = getById(id);
        user.activate();

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deactivate(Long id) {

        User user = getById(id);
        user.deactivate();

    }

}