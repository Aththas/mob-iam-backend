package com.VEMS.vems.auth.repository;

import com.VEMS.vems.auth.entity.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    List<Token> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
