package com.example.userselfservice.repositories;

import com.example.userselfservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    @Override
    Token save(Token token);


    Optional<Token> findByValueAndIsdeleted(String value,boolean deleted);
    Optional<Token> findByValueAndIsdeletedAndExpiryGreaterThan(String value, boolean deleted, Date currDate);
}
