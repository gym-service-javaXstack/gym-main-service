package com.example.springcore.mapper;

import com.example.springcore.dto.UserDTO;
import com.example.springcore.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO fromUserToUserDTO(User user);

    User fromUserDTOToUser(UserDTO userDTO);
}
