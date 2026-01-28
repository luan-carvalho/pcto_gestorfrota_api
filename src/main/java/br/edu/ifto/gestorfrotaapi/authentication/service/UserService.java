package br.edu.ifto.gestorfrotaapi.authentication.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifto.gestorfrotaapi.authentication.command.UserCreateCommand;
import br.edu.ifto.gestorfrotaapi.authentication.command.UserUpdateCommand;
import br.edu.ifto.gestorfrotaapi.authentication.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.repository.UserRepository;
import br.edu.ifto.gestorfrotaapi.authentication.util.TokenGenerator;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<User> findAll() {

        return userRepo.findAll();

    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public User findById(Long id) {

        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public User create(UserCreateCommand cmd) {

        return userRepo.save(
                User.create(
                        cmd.name(),
                        cmd.registration(),
                        TokenGenerator.generateToken(),
                        cmd.roles()));

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public User update(Long userId, UserUpdateCommand cmd) {

        User existingUser = findById(userId);
        existingUser.updateInfo(cmd.name(), cmd.registration(), cmd.roles());
        return existingUser;

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void activate(Long id) {

        User user = findById(id);
        user.activate();

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deactivate(Long id) {

        User user = findById(id);
        user.deactivate();

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(Long id) {

        User user = findById(id);
        userRepo.delete(user);

    }
}