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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin("*")
@RequestMapping("/accounts")
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


    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody Account account) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String currentUsername = userDetails.getUsername();
            Account currentAccount = iAccountService.findByUsername(currentUsername);
            if (!currentAccount.getPassword().equals(account.getPassword())) {
                return new ResponseEntity<>("Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST);
            }
            currentAccount.setPassword(account.getPassword());
            accountServiceImpl.save(currentAccount);
            return new ResponseEntity<>("Cập nhật mật khẩu thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Không thể xác định người dùng hiện tại", HttpStatus.UNAUTHORIZED);
        }
    }


//
//    @PostMapping("/checkPassword/{id}")
//    public boolean checkPassword(@PathVariable int id , @RequestBody Account accountEdit) {
//        Account account = accountServiceImpl.findById(id);
//        if (account.getPassword().equals(accountEdit.getPassword())) {
//            return true;
//        }else  {
//            return false;
//        }
//    }
//
//    @PutMapping("/changePassword/{id}")
//    public Account changePassword(@RequestBody Account accountEdit) {
//        Account account = accountServiceImpl.findById(accountEdit.getId());
//        account.setPassword(accountEdit.getPassword());
//        accountServiceImpl.edit(account);
//        return account;
//    }


}
