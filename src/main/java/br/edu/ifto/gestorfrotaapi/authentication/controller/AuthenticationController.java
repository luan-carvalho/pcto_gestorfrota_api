package br.edu.ifto.gestorfrotaapi.authentication.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import br.edu.ifto.gestorfrotaapi.authentication.service.TokenService;
import br.edu.ifto.gestorfrotaapi.authentication.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final UserService userService;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authManager, UserService userService,
            TokenService tokenService) {
        this.authManager = authManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/first-access/verify")
    public ResponseEntity<Void> verifyUserFirstAccess(@RequestBody @Valid VerifyFirstAccessDto dto) {

        boolean isValid = userService.verifyFirstAccess(dto.registration(), dto.token());

        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/first-access/activate")
    public ResponseEntity<Map<String, String>> activateUserFirstAccess(@RequestBody @Valid ActivateFirstAccessDto dto) {

        userService.activateFirstAccess(dto.registration(), dto.token(), dto.password());
        return ResponseEntity.ok(Map.of("message", "Account activated successfully. You can login now!"));
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto data) {
        var authToken = new UsernamePasswordAuthenticationToken(data.registration(), data.password());
        var auth = authManager.authenticate(authToken);
        User user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user.getUsername());
        return ResponseEntity
                .ok(new LoginResponseDto(token, "Bearer", user.getFirstName(), user.getRoles()));
    }

    @PatchMapping("/update-password")
    public ResponseEntity<Void> updatePassword(
            @RequestBody @Valid UpdatePasswordDto dto,
            @AuthenticationPrincipal User user) {
        userService.updatePassword(dto, user);
        return ResponseEntity.noContent().build();
    }

}
