package br.edu.ifto.gestorfrotaapi.authentication.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role.description", target = "role")
    UserCreateResponseDto toCreateResponseDto(User user);

    @Mapping(source = "role.description", target = "role")
    UserResponseDto toResponseDto(User user);

    @Mapping(source = "role.description", target = "role")
    List<UserResponseDto> toResponseDto(List<User> users);

}
