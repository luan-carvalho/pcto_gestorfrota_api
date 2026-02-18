package br.edu.ifto.gestorfrotaapi.authentication.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.edu.ifto.gestorfrotaapi.authentication.application.dto.LoginResponseDto;
import br.edu.ifto.gestorfrotaapi.user.application.dto.UserCreateResponseDto;
import br.edu.ifto.gestorfrotaapi.user.application.dto.UserResponseDto;
import br.edu.ifto.gestorfrotaapi.user.domain.model.User;
import br.edu.ifto.gestorfrotaapi.user.domain.valueObjects.Cpf;

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
