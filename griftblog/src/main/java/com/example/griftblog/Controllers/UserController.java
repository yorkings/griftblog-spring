package com.example.griftblog.Controllers;

import com.example.griftblog.Service.UserService;
import com.example.griftblog.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/auth/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;



}
