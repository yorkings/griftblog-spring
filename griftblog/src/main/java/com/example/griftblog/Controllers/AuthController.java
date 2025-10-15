package com.example.griftblog.Controllers;

import com.example.griftblog.DTO.LoginRequest;
import com.example.griftblog.DTO.RegisterRequest;
import com.example.griftblog.Service.AuthService;
import com.example.griftblog.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private  final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("✅ Successfully registered! Check your email for a confirmation link.");
    }
    @GetMapping("/confirm")
    public ResponseEntity<String> confirmEmail(@RequestParam("token") String token) {
        String response = userService.confirmToken(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String jwtToken = authService.authenticate(request);
        return ResponseEntity.ok(jwtToken);
    }


}
