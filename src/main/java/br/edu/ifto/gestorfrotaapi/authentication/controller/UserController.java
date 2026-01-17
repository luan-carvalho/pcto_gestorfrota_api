package br.edu.ifto.gestorfrotaapi.authentication.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserUpdateDto;
import br.edu.ifto.gestorfrotaapi.authentication.mapper.UserMapper;
import br.edu.ifto.gestorfrotaapi.authentication.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("admin/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserCreateResponseDto> create(@RequestBody @Valid UserCreateDto dto) {
        UserCreateResponseDto response = UserMapper
                .toCreateResponseDto(userService.create(dto.name(), dto.registration(), dto.roleId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(UserMapper.toResponseDto(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(UserMapper.toResponseDto(userService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDto dto) {
        return ResponseEntity
                .ok(UserMapper.toResponseDto(userService.update(id, dto.name(), dto.registration(), dto.roleId())));
    }

    @PatchMapping("/{userId}/inactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {
        userService.deactivateUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable Long userId) {
        userService.activateUser(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        userService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
