package com.example.userselfservice.services;

import com.example.userselfservice.exceptions.UserNotFound;
import com.example.userselfservice.models.Token;
import com.example.userselfservice.models.User;
import com.example.userselfservice.repositories.TokenRepository;
import com.example.userselfservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    BCryptPasswordEncoder bCryptPasswordEncoder;
    UserRepository userRepository;
    TokenRepository tokenRepository;
    UserService(BCryptPasswordEncoder bCryptPasswordEncoder,UserRepository userRepository,TokenRepository tokenRepository){
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.userRepository=userRepository;
        this.tokenRepository=tokenRepository;
    }
    public User singnup(String name,String email,String password){
        User user=new User();
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        user.setEmailVerified(true);
        return userRepository.save(user);
    }
    public Token login(String email, String password){
        Optional<User> optionalUser=userRepository.findByEmail(email);
        if(optionalUser.isEmpty())
            throw new UserNotFound("User does not exists");
        User user= optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword()))
            return null;
        Token token=generateToken(user);
        Token savedToken=tokenRepository.save(token);
        return savedToken;
    }
    private Token generateToken(User user) {
        LocalDate currentDate = LocalDate.now();
        LocalDate thirtyDaysLater = currentDate.plusDays(30);

        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpiry(expiryDate);
        //128 character alphanumeric string.
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);
        return token;
    }

    public void logout(String tokenValue){
        Optional<Token> optionalToken=tokenRepository.findByValueAndIsdeleted(tokenValue,false);
        if(optionalToken.isEmpty())
            return;
        Token token= optionalToken.get();
        token.setIsdeleted(true);
        tokenRepository.save(token);
    }
    public User validate(String token){
        Optional<Token> optionalToken=tokenRepository.findByValueAndIsdeletedAndExpiryGreaterThan(token,false,new Date());
        if(optionalToken.isEmpty())
                return null;
        return optionalToken.get().getUser();
    }
}
