package com.example.userselfservice.controllers;

import com.example.userselfservice.dtos.LoginReqdto;
import com.example.userselfservice.dtos.LogoutReqdto;
import com.example.userselfservice.dtos.SignUpReqdto;
import com.example.userselfservice.dtos.UserDto;
import com.example.userselfservice.models.Token;
import com.example.userselfservice.models.User;
import com.example.userselfservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;
    UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignUpReqdto reqdto){
        User user=userService.singnup(reqdto.getName(), reqdto.getEmail(), reqdto.getPassword());
        return UserDto.from(user);
    }
    @PostMapping("/login")
    public Token login(@RequestBody LoginReqdto reqdto){
        Token token= userService.login(reqdto.getEmail(), reqdto.getPassword());
        return token;
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutReqdto reqdto){
        userService.logout(reqdto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable String token){
        User user=userService.validate(token);
        return UserDto.from(user);
    }
}
