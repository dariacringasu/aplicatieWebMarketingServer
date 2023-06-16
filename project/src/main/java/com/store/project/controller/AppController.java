package com.store.project.controller;


import com.store.project.service.dao.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    private UserService clientService;
    @GetMapping(value="/welcome")
    public String getPage(){
        return "merge";
    }
}
