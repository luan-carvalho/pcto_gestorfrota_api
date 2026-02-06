package br.edu.ifto.gestorfrotaapi.authentication.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifto.gestorfrotaapi.authentication.command.UserCreateCommand;
import br.edu.ifto.gestorfrotaapi.authentication.command.UserUpdateCommand;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.exception.CpfAlreadyRegistered;
import br.edu.ifto.gestorfrotaapi.authentication.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.authentication.mapper.UserMapper;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.repository.UserRepository;
import br.edu.ifto.gestorfrotaapi.authentication.repository.filter.UserFilter;
import br.edu.ifto.gestorfrotaapi.authentication.repository.spec.UserSpecification;
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
    public Page<UserResponseDto> findAll(UserFilter filter, Pageable pageable) {

        Specification<User> spec = Specification
                .where(UserSpecification.hasId(filter.id()))
                .and(UserSpecification.hasName(filter.name()))
                .and(UserSpecification.hasStatus(filter.status()))
                .and(UserSpecification.hasRole(filter.role()));

        return userRepo.findAll(spec, pageable).map(mapper::toResponseDto);

    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserResponseDto findById(Long id) {

        return mapper.toResponseDto(getUser(id));

    }

    public User getUser(Long id) {

        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException());

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserCreateResponseDto create(UserCreateCommand cmd) {

        if (userRepo.existsByCpf(cmd.cpf())) {

            throw new CpfAlreadyRegistered(cmd.cpf());

        }

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

        System.out.println("TESTE SERVICE");

        User existingUser = getUser(cmd.userId());
        existingUser.updateInfo(cmd.name(), cmd.roles());
        return mapper.toResponseDto(existingUser);

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void activate(Long id) {

        User user = getUser(id);
        user.activate();

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deactivate(Long id) {

        User user = getUser(id);
        user.deactivate();

    }

}