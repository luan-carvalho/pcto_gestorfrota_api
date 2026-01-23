package br.edu.ifto.gestorfrotaapi.authentication.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifto.gestorfrotaapi.authentication.dto.UpdatePasswordDto;
import br.edu.ifto.gestorfrotaapi.authentication.exception.RoleNotFoundException;
import br.edu.ifto.gestorfrotaapi.authentication.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.authentication.model.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;
import br.edu.ifto.gestorfrotaapi.authentication.repository.RoleRepository;
import br.edu.ifto.gestorfrotaapi.authentication.repository.UserRepository;
import br.edu.ifto.gestorfrotaapi.authentication.util.TokenGenerator;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Role> findAllRoles() {

        return roleRepo.findAll();

    }

    public List<User> findAll() {

        return userRepo.findAll();

    }

    public User findById(Long id) {

        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    }

    public Role findRoleById(Long roleId) {

        return roleRepo.findById(roleId).orElseThrow(() -> new NoSuchElementException("Role not found"));

    }

    public User create(String name, String registration, Long roleId) {

        Role role = roleRepo.findById(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));
        User newUser = new User(name, registration, TokenGenerator.generateToken(), role);
        return userRepo.save(newUser);

    }

    public User update(Long userId, String name, String registration, Long roleId) {

        User existingUser = findById(userId);

        Role role = null;

        if (roleId != null) {

            role = roleRepo.findById(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));

        }

        existingUser.updateInfo(name, registration, role);
        return userRepo.save(existingUser);

    }

    public void activateUser(Long id) {

        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.activate();
        userRepo.save(user);

    }

    public void deactivateUser(Long id) {

        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.deactivate();
        userRepo.save(user);

    }

    public void updatePassword(Long id, String newPassword) {

        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.updatePassword(encodedPassword);
        userRepo.save(user);

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