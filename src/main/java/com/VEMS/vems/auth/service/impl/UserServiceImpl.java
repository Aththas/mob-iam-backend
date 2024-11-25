package com.VEMS.vems.auth.service.impl;

import com.VEMS.vems.auth.entity.user.User;
import com.VEMS.vems.auth.repository.AuthRepository;
import com.VEMS.vems.auth.service.UserService;
import com.VEMS.vems.other.apiResponseDto.ApiResponse;
import com.VEMS.vems.other.exception.UserNotAuthenticated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final AuthRepository authRepository;

    @Override
    public ResponseEntity<ApiResponse<?>> userInfo() {
        try{
            User user = getCurrentUser();
            log.info("User Info: User Details Found for " + user.getEmail());
            return new ResponseEntity<>(
                    new ApiResponse<>(true, user,"User Details Found", null),
                    HttpStatus.OK);
        }catch (Exception e){
            log.error("User Info: " + e);
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, "Server Error", "500"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public User getCurrentUser(){
        Optional<User> optionalUser = authRepository.findByEmail(getCurrentUserName());
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }else{
            throw new UserNotAuthenticated("User Not Authenticated");
        }
    }

    private String getCurrentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
                return ((UserDetails) authentication.getPrincipal()).getUsername();
        }else{
            throw new UserNotAuthenticated("User Not Authenticated");
        }
    }

}
