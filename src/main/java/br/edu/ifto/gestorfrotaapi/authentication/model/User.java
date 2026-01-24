package br.edu.ifto.gestorfrotaapi.authentication.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.ifto.gestorfrotaapi.authentication.exception.StatusUpdateException;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.Role;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<Role> roles;

    private String firstAccessToken;

    public User() {

    }

    public User(String name, String registration, String firstAccessToken, List<Role> roles) {

        this.name = name;
        this.registration = registration;
        this.firstAccessToken = firstAccessToken;
        this.roles = roles;
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

    public void updateInfo(String name, String registration, List<Role> roles) {

        if (name != null && !name.isBlank())
            this.name = name;

        if (registration != null && !registration.isBlank())
            this.registration = registration;

        if (roles != null && !roles.isEmpty())
            this.roles = roles;

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

    public List<Role> getRoles() {
        return roles;
    }

    public String getFirstAccessToken() {
        return firstAccessToken;
    }

    public boolean hasRole(Role role) {

        return this.roles.contains(role);

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.registration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((registration == null) ? 0 : registration.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (registration == null) {
            if (other.registration != null)
                return false;
        } else if (!registration.equals(other.registration))
            return false;
        return true;
    }

}
