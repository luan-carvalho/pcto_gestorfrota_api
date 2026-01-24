package br.edu.ifto.gestorfrotaapi.authentication.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserCreateResponseDto toCreateResponseDto(User user);

    UserResponseDto toResponseDto(User user);

    List<UserResponseDto> toResponseDto(List<User> users);

}
