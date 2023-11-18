package com.groupProject.backend.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TEST")
public class Test {

    @GetMapping
    public String test()
    {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
