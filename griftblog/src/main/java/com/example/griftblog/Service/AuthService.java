package com.example.griftblog.Service;

import com.example.griftblog.Config.JwtService;
import com.example.griftblog.Repository.UserRepository;
import com.example.griftblog.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authMan;
    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String Authenticate(String name,String password){
        authMan.authenticate(
                new UsernamePasswordAuthenticationToken(name,password)
        );
        User user = userRepo.findByUsername(name).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!user.isEnabled()) {
            throw new IllegalStateException("Account not verified. Please check your email.");
        }
        return jwtService.generateToken(user.getUsername());
    }
}
