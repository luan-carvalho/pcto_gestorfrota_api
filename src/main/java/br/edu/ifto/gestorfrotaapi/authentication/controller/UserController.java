package br.edu.ifto.gestorfrotaapi.authentication.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifto.gestorfrotaapi.authentication.command.UserCreateCommand;
import br.edu.ifto.gestorfrotaapi.authentication.command.UserUpdateCommand;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateRequestDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserUpdateRequestDto;
import br.edu.ifto.gestorfrotaapi.authentication.mapper.UserMapper;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserCreateResponseDto> create(@RequestBody @Valid UserCreateRequestDto dto) {

        User created = userService.create(new UserCreateCommand(
                dto.registration(),
                dto.name(),
                dto.roles()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(userMapper.toCreateResponseDto(created));

    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userMapper.toResponseDto(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toResponseDto(userService.findById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto dto) {

        userService.update(
                id,
                new UserUpdateCommand(
                        dto.name(),
                        dto.registration(),
                        dto.roles()));

        return ResponseEntity
                .noContent().build();
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        userService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
