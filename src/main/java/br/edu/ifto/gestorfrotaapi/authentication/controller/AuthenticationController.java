package br.edu.ifto.gestorfrotaapi.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifto.gestorfrotaapi.authentication.dto.ActivateFirstAccessDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.LoginDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.LoginResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UpdatePasswordDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.VerifyFirstAccessDto;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.service.AuthenticationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationManager authManager, AuthenticationService authService) {
        this.authManager = authManager;
        this.authService = authService;
    }

    @PostMapping("/first-access/verify")
    public ResponseEntity<Void> verifyUserFirstAccess(@RequestBody @Valid VerifyFirstAccessDto dto) {

        authService.verifyFirstAccess(dto.registration(), dto.token());
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/first-access/activate")
    public ResponseEntity<Void> activateUserFirstAccess(@RequestBody @Valid ActivateFirstAccessDto dto) {

        authService.activateFirstAccess(dto.registration(), dto.token(), dto.password());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto data) {

        var authToken = new UsernamePasswordAuthenticationToken(data.registration(), data.password());
        var auth = authManager.authenticate(authToken);
        User user = (User) auth.getPrincipal();
        var token = authService.generateToken(user.getUsername());

        return ResponseEntity
                .ok(new LoginResponseDto(token, "Bearer", user.getFirstName(), user.getRoles()));

    }

    @PatchMapping("/update-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updatePassword(
            @RequestBody @Valid UpdatePasswordDto dto) {

        authService.updateOwnPassword(dto.currentPassword(), dto.newPassword());
        return ResponseEntity.noContent().build();

    }

}
