package com.example.userselfservice.Security.models;

import com.example.userselfservice.models.Roles;
import org.springframework.security.core.GrantedAuthority;

import javax.management.relation.Role;


public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;

    public CustomGrantedAuthority(Roles role){
        this.authority=role.getValue();
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
