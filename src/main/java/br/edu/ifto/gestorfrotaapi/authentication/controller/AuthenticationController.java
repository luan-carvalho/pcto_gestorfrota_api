package br.edu.ifto.gestorfrotaapi.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifto.gestorfrotaapi.authentication.dto.ActivateUserDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.LoginDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.LoginResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UpdatePasswordDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.VerifyFirstAccessDto;
import br.edu.ifto.gestorfrotaapi.authentication.service.AuthenticationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/first-access/verify")
    public ResponseEntity<Void> verifyUserFirstAccess(@RequestBody @Valid VerifyFirstAccessDto dto) {

        authService.verifyFirstAccess(dto.toCommand());
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/first-access/activate")
    public ResponseEntity<Void> activateUserFirstAccess(@RequestBody @Valid ActivateUserDto dto) {

        authService.activateFirstAccess(dto.toCommand());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto dto) {

        return ResponseEntity.ok(authService.login(dto.toCommand()));

    }

    @PatchMapping("/update-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updatePassword(
            @RequestBody @Valid UpdatePasswordDto dto) {

        authService.updateOwnPassword(dto.toCommand());
        return ResponseEntity.noContent().build();

    }

}
