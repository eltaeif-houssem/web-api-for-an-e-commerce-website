package com.ecommerce.server.service;

import com.ecommerce.server.dto.auth.AuthenticationRequest;
import com.ecommerce.server.dto.auth.AuthenticationResponse;
import com.ecommerce.server.dto.auth.RegistrationRequest;
import com.ecommerce.server.enums.RoleName;
import com.ecommerce.server.exception.UserAlreadyExistsException;
import com.ecommerce.server.model.User;
import com.ecommerce.server.repository.RoleRepository;
import com.ecommerce.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;


    public void register(RegistrationRequest request) {

        Optional<User> userExist = userRepository.findByEmail(request.getEmail());

        if(userExist.isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }

        var userRole = roleRepository.findByName(RoleName.ROLE_CLIENT)
                // todo - better exception handling
                .orElseThrow(() -> new IllegalStateException("ROLE CLIENT was not initiated"));
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdDate(LocalDateTime.now())
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getFullName());

        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}