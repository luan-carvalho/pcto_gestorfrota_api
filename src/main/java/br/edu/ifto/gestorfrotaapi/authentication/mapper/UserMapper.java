package br.edu.ifto.gestorfrotaapi.authentication.mapper;

import java.util.List;

import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;

public class UserMapper {

    public static UserCreateResponseDto toCreateResponseDto(User user) {

        return new UserCreateResponseDto(user.getId(),
                user.getRegistration(),
                user.getName(),
                user.getStatus(),
                user.getRole(),
                user.getFirstAccessToken()

        );

    }

    public static UserResponseDto toResponseDto(User user) {

        return new UserResponseDto(user.getId(),
                user.getRegistration(),
                user.getName(),
                user.getStatus(),
                user.getRole());

    }

    public static List<UserResponseDto> toResponseDto(List<User> users) {

        return users.stream().map(UserMapper::toResponseDto).toList();

    }

}
