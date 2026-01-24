package br.edu.ifto.gestorfrotaapi.authentication.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifto.gestorfrotaapi.authentication.dto.UpdatePasswordDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserUpdateDto;
import br.edu.ifto.gestorfrotaapi.authentication.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;
import br.edu.ifto.gestorfrotaapi.authentication.repository.UserRepository;
import br.edu.ifto.gestorfrotaapi.authentication.util.TokenGenerator;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {

        return userRepo.findAll();

    }

    public User findById(Long id) {

        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public User create(UserCreateDto dto) {

        User newUser = new User(dto.name(), dto.registration(), TokenGenerator.generateToken(), dto.roles());
        return userRepo.save(newUser);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public User update(Long userId, UserUpdateDto dto) {

        User existingUser = findById(userId);
        existingUser.updateInfo(dto.name(), dto.registration(), dto.roles());
        return userRepo.save(existingUser);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public void activateUser(Long id) {

        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.activate();
        userRepo.save(user);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public void deactivateUser(Long id) {

        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.deactivate();
        userRepo.save(user);

    }

    public void updatePassword(UpdatePasswordDto dto, User loggedUser) {

        String encodedPassword = passwordEncoder.encode(dto.newPassword());
        loggedUser.updatePassword(encodedPassword);
        userRepo.save(loggedUser);

    }

    public boolean verifyFirstAccess(String registration, String token) {

        User user = userRepo.findByRegistration(registration)
                .orElseThrow(() -> new UserNotFoundException(registration));
        return user.verifyFirstAccess(token);

    }

    @Transactional
    public void activateFirstAccess(String registration, String token, String rawPassword) {

        User user = userRepo.findByRegistrationAndStatus(registration, UserStatus.FIRST_ACESS)
                .orElseThrow(() -> new UserNotFoundException(registration));

        if (!user.verifyFirstAccess(token)) {
            throw new IllegalStateException("Invalid token or account already activated!");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.activateFirstAccess(encodedPassword);
        userRepo.save(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public void delete(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepo.delete(user);
    }

    public void updatePassword(String registration, UpdatePasswordDto dto) {

        User user = userRepo.findByRegistration(registration)
                .orElseThrow(() -> new UserNotFoundException(registration));

        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        user.updatePassword(passwordEncoder.encode(dto.newPassword()));
        userRepo.save(user);

    }
}