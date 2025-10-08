package com.example.griftblog.Service;

import com.example.griftblog.DTO.RegisterRequest;
import com.example.griftblog.Repository.ConfirmationTokenRepository;
import com.example.griftblog.Repository.RoleRepository;
import com.example.griftblog.Repository.UserRepository;

import com.example.griftblog.models.ConfirmationToken;
import com.example.griftblog.models.Role;
import com.example.griftblog.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private  final RoleRepository roleRepo;
    private  final ConfirmationTokenRepository tokenRepo;
    private  final  EmailService emailService;

     @Transactional// Ensures atomicity (all or nothing)
    public String registerUser(RegisterRequest request){
        if(userRepo.existsByUsername(request.username())){
            throw  new IllegalArgumentException("username already exists");
        }
        if (userRepo.existsByEmail(request.email())) {
            throw  new IllegalArgumentException("email already taken");
        }
        String encodedPassword = passwordEncoder.encode(request.password());
        Role defaultRole= roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Default role not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        User newUser= User.builder()
                .username(request.username())
                .email(request.email())
                .password(encodedPassword)
                .roles(roles)
                .enabled(false)
                .build();
        userRepo.save(newUser);
        ConfirmationToken token = new ConfirmationToken(newUser);
        tokenRepo.save(token);
        String link = "http://localhost:8080/api/v1/auth/confirm?token=" + token.getToken();
        emailService.sendMail(request.email(),"Confirm your Griftblog Account",buildEmailBody(request.username(),link));
        return token.getToken();
    }
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = tokenRepo.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found."));
        if (confirmationToken.getConfirmedAt() != null) {
            return "Email already confirmed.";
        }
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Token expired.";
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        tokenRepo.save(confirmationToken);
        enableUser(confirmationToken.getUser().getId());

        return "Account successfully confirmed! You can now log in.";
    }

    public void enableUser(Long userId) {
        userRepo.findById(userId).ifPresent(user -> {
            user.setEnabled(true);
            userRepo.save(user);
        });
    }
    public  String buildEmailBody(String name,String link){
        return "Hello " + name + ",\n\n"
                + "Thank you for registering. Please click on the below link to activate your account:\n\n"
                + link + "\n\n"
                + "This link will expire in 24 hours.";
    }
}
