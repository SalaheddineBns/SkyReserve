package com.salah.identityservice.service;

import com.salah.identityservice.dto.LoginRequestDto;
import com.salah.identityservice.dto.LoginResponseDto;
import com.salah.identityservice.dto.RegisterRequestDto;
import com.salah.identityservice.dto.RegisterResponseDto;
import com.salah.identityservice.model.User;
import com.salah.identityservice.repository.UserRepository;
import com.salah.identityservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private  AuthenticationManager authenticationManager;

    public RegisterResponseDto register(RegisterRequestDto request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        User savedUser = userRepository.save(user);

        // Retourner un DTO
        return new RegisterResponseDto(
                savedUser.getUserId(),
                savedUser.getEmail(),
                savedUser.getRole()
        );

    }

    public LoginResponseDto login(LoginRequestDto request) {
 User user = userRepository.findByEmail(request.email())
               .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

      if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
      }
        String token = jwtUtil.generateToken(user.getEmail()); // Email ou ID selon ton choix
        return new LoginResponseDto(token, user.getEmail(), user.getRole());
   }
}


