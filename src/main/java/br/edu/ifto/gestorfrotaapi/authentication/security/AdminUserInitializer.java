package br.edu.ifto.gestorfrotaapi.authentication.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.authentication.repository.UserRepository;
import jakarta.transaction.Transactional;

@Component
public class AdminUserInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.name}")
    private String adminName;

    @Value("${app.admin.registration}")
    private String adminRegistration;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @Value("${app.admin.insert-enabled:}")
    private boolean enabled;

    public AdminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void createAdminUserIfMissing() {

        if (!enabled) {
            return;
        }

        if (adminPassword.isBlank()) {
            return;
        }

        if (adminName.isBlank()) {
            return;
        }

        if (userRepository.existsByRegistration(adminRegistration)) {

            return;

        }

        String encodedPassword = passwordEncoder.encode(adminPassword);

        User admin = new User(adminName, adminRegistration, "", List.of(Role.values()));
        admin.activateFirstAccess(encodedPassword);

        userRepository.save(admin);

    }
}
