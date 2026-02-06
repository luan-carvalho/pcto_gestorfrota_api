package br.edu.ifto.gestorfrotaapi.authentication.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateRequestDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserUpdateRequestDto;
import br.edu.ifto.gestorfrotaapi.authentication.repository.filter.UserFilter;
import br.edu.ifto.gestorfrotaapi.authentication.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> findAll(UserFilter filter,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserCreateResponseDto> create(@RequestBody @Valid UserCreateRequestDto dto) {

        UserCreateResponseDto created = userService.create(dto.toCommand());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(location).body(created);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto dto) {

        UserResponseDto updated = userService.update(dto.toCommand(id));

        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {
        userService.deactivate(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable Long userId) {
        userService.activate(userId);
        return ResponseEntity.noContent().build();
    }

}
