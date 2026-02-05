package br.edu.ifto.gestorfrotaapi.authentication.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.edu.ifto.gestorfrotaapi.authentication.dto.LoginResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserCreateResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.authentication.model.User;
import br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects.Cpf;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserCreateResponseDto toCreateResponseDto(User user);

    UserResponseDto toResponseDto(User user);

    LoginResponseDto toLoginDto(User user, String token);

    List<UserResponseDto> toResponseDto(List<User> users);

    default String mapCpf(Cpf cpf) {

        return cpf.getFormatted();

    }

}
