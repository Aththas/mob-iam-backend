package com.VEMS.vems.auth.repository;

import com.VEMS.vems.auth.entity.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
