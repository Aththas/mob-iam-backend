package com.VEMS.vems.other.mapper;

import com.VEMS.vems.auth.dto.requestDto.SignInDto;
import com.VEMS.vems.auth.entity.token.Token;
import com.VEMS.vems.auth.entity.user.Role;
import com.VEMS.vems.auth.entity.user.User;
import com.VEMS.vems.auth.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public User mapUser(SignInDto signInDto){
        User user = new User();
        user.setFirstName(signInDto.getFirstName());
        user.setLastName(signInDto.getLastName());
        user.setEmail(signInDto.getEmail());
        user.setRole(Role.valueOf(signInDto.getRole()));
        user.setPassword(passwordEncoder.encode(signInDto.getPassword()));
        return user;
    }

    public void saveToken(String accessToken, User user) {
        Token token = new Token();
        token.setToken(accessToken);
        token.setUser(user);
        tokenRepository.save(token);
    }

    @Transactional
    public void removeToken(Long userId){
        List<Token> tokenList = tokenRepository.findAllByUserId(userId);
        if(!tokenList.isEmpty()){
            tokenRepository.deleteAllByUserId(userId);
        }
    }

    @Transactional
    public void removeTokenByToken(String token){
        Optional<Token> optionalToken = tokenRepository.findByToken(token);
        if(optionalToken.isPresent()){
            tokenRepository.deleteByToken(token);
        }
    }
}
