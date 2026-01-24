package br.edu.ifto.gestorfrotaapi.authentication.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.ifto.gestorfrotaapi.authentication.exception.StatusUpdateException;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String registration;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String firstAccessToken;

    public User() {

    }

    public User(String name, String registration, String firstAccessToken, Role role) {

        this.name = name;
        this.registration = registration;
        this.firstAccessToken = firstAccessToken;
        this.role = role;
        this.status = UserStatus.FIRST_ACESS;

    }

    public boolean isActive() {

        return status == UserStatus.ACTIVE;

    }

    public boolean isInactive() {

        return status == UserStatus.INACTIVE;

    }

    public void deactivate() {

        if (this.status == UserStatus.ACTIVE)
            throw new StatusUpdateException("User already inactive");

        this.status = UserStatus.INACTIVE;

    }

    public void activate() {

        if (this.status == UserStatus.FIRST_ACESS && firstAccessToken != null) {

            throw new StatusUpdateException("Cannot change the status of a user with the first access pending");

        }

        if (this.status == UserStatus.ACTIVE)
            throw new StatusUpdateException("User already active");

        this.status = UserStatus.ACTIVE;

    }

    public String getFirstName() {

        return this.name.split(" ")[0];

    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateInfo(String name, String registration, Role role) {

        if (name != null && !name.isBlank())
            this.name = name;

        if (registration != null && !registration.isBlank())
            this.registration = registration;

        if (role != null)
            this.role = role;

    }

    public boolean verifyFirstAccess(String token) {

        return token.equals(this.firstAccessToken) && this.status == UserStatus.FIRST_ACESS;

    }

    public void activateFirstAccess(String password) {

        this.password = password;
        this.status = UserStatus.ACTIVE;
        this.firstAccessToken = null;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegistration() {
        return registration;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Role getRole() {
        return role;
    }

    public String getFirstAccessToken() {
        return firstAccessToken;
    }

    public boolean hasRole(Role role) {

        return this.role == role;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.registration;
    }
}
