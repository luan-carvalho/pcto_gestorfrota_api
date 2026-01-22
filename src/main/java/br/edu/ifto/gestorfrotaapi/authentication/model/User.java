package br.edu.ifto.gestorfrotaapi.authentication.model;

import br.edu.ifto.gestorfrotaapi.authentication.exception.StatusUpdateException;
import br.edu.ifto.gestorfrotaapi.authentication.model.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String registration;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
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

        if (this.status == UserStatus.FIRST_ACESS && firstAccessToken != null) {

            throw new StatusUpdateException("Cannot change the status of a user with the first access pending");

        }

        this.status = UserStatus.INACTIVE;

    }

    public void activate() {

        if (this.status == UserStatus.FIRST_ACESS && firstAccessToken != null) {

            throw new StatusUpdateException("Cannot change the status of a user with the first access pending");

        }

        this.status = UserStatus.ACTIVE;

    }

    public String getFirstName() {

        return this.name.split(" ")[0];

    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateInfo(String name, String registration, Role role) {
        this.name = name;
        this.registration = registration;
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

    public String getPassword() {
        return password;
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

}
