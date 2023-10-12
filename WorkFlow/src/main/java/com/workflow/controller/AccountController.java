package com.workflow.controller;

import com.workflow.dto.AccountToken;
import com.workflow.model.Account;
import com.workflow.service.IAccountService;
import com.workflow.service.impl.AccountServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin("*")
public class AccountController {
    @Autowired
    private IAccountService iAccountService;

    @Autowired
    AccountServiceImpl accountServiceImpl;

    @Autowired
    AuthenticationManager authenticationManager;

    public static final String PRIVATE_KEY = "123456789999887abc";
    private static final long EXPIRE_TIME = 86400L;

    public String createToken(String username) {
        return Jwts.builder()
                .setSubject((username))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME * 1000))
                .signWith(SignatureAlgorithm.HS512, PRIVATE_KEY)
                .compact();
    }

    @PostMapping("/login")
    public AccountToken login(@RequestBody Account account) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = createToken(account.getUsername());
        Account accountByUserName = iAccountService.findByUsername(account.getUsername());
        return new AccountToken(accountByUserName.getId(), accountByUserName.getUsername(), token, accountByUserName.getRoles());
    }

    // doi mat khau nguoi dung
    @PostMapping ("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable int id, @RequestBody Account account) {
        Account currentAccount = accountServiceImpl.findByTeamId(id);
        if (currentAccount == null) {
            // thong bao nguoi dung khong ton tai
            return new ResponseEntity<>("Tai khoan khong ton tai", HttpStatus.NOT_FOUND);
        }

        // check mat khau cu co dung khong
        if (!currentAccount.getPassword().equals(account.getPassword())) {
            return new ResponseEntity<>("Mat khau cu khong dung", HttpStatus.BAD_REQUEST);
        }

        // cap nhat mat khau moi
        currentAccount.setPassword(account.getPassword());
        accountServiceImpl.save(currentAccount);
        return new ResponseEntity<>("Cap nhat mat khau thanh cong", HttpStatus.OK);
    }
}
