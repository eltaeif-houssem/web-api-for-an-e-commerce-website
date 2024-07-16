package com.ecommerce.server.service;

import com.ecommerce.server.exception.NotFoundException;
import com.ecommerce.server.model.User;
import com.ecommerce.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String userEmail = getUserPrincipal().getName();
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isEmpty()){
            throw new NotFoundException("User not found!");
        }
        return user.get();
    }

    public Principal getUserPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
