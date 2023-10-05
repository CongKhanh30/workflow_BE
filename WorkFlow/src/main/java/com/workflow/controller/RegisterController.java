package com.workflow.controller;

import com.workflow.model.Account;
import com.workflow.model.Role;
import com.workflow.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private IAccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestParam(value = "name") String name,
                                                 @RequestParam(value = "username") String username,
                                                 @RequestParam(value = "password") String password


    ) throws IOException {
        Account newAccount = new Account();
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(2);
        roles.add(role);
        newAccount.setName(name);
        newAccount.setUsername(username);
        newAccount.setPassword(password);
        newAccount.setRoles(roles);
        accountService.save(newAccount);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}

