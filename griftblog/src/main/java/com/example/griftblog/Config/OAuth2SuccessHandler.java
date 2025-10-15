package com.example.griftblog.Config;

import com.example.griftblog.Repository.UserRepository;
import com.example.griftblog.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuthUser = (OAuth2User) authentication.getPrincipal();
        String email = oAuthUser.getAttribute("email");
        String name = oAuthUser.getAttribute("name");

        // Create user if not found
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder()
                    .username(name)
                    .email(email)
                    .password("") // no password for Google users
                    .enabled(true)
                    .build();
            return userRepository.save(newUser);
        });

        // Generate JWT
        String token = jwtService.generateToken(user.getUsername());

        // Redirect back to frontend with token
        response.sendRedirect("http://localhost:5173/oauth-success?token=" + token);
    }
}
