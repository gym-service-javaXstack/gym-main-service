package com.example.springcore.controller.impl;

import com.example.springcore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl {
    private final UserService userService;
}
