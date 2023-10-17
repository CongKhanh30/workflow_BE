package com.workflow.controller;

import com.workflow.dto.AccountToken;
import com.workflow.dto.ChangePasswordRequest;
import com.workflow.model.Account;
import com.workflow.service.IAccountService;
import com.workflow.service.impl.AccountServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@CrossOrigin("*")
public class AccountController {

    private final IAccountService iAccountService;

    private final AccountServiceImpl accountServiceImpl;

    private final AuthenticationManager authenticationManager;

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


    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword
    ) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String currentUsername = userDetails.getUsername();
            Account currentAccount = iAccountService.findByUsername(currentUsername);

            if (!currentAccount.getPassword().equals(currentPassword)) {
                return new ResponseEntity<>("Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST);
            }

            // Change Password
            currentAccount.setPassword(newPassword);
            accountServiceImpl.save(currentAccount);

            return new ResponseEntity<>("Cập nhật mật khẩu thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Không thể xác định người dùng hiện tại", HttpStatus.UNAUTHORIZED);
        }
    }

}
