package com.example.userselfservice.dtos;

import com.example.userselfservice.models.Roles;
import com.example.userselfservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private List<Roles> roles;

    public static UserDto from(User user){
        if(user==null)
            return null;
        UserDto dto=new UserDto();
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setRoles(user.getRoles());
        return dto;
    }
}
