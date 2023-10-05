package com.workflow.service.impl;

import com.workflow.model.Account;
import com.workflow.repository.IAccountRepo;
import com.workflow.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountRepo iAccountRepo;

    @Override
    public Account findByUsername(String username) {
        Account account = iAccountRepo.findByUsername(username);
        return account;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findByUsername(username);
        return new User(username,account.getPassword(),account.getRoles());
    }

    public String getCurrentUsername (){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
