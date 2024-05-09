package com.example.userselfservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReqdto {
    private String name;
    private String email;
    private String password;
}
