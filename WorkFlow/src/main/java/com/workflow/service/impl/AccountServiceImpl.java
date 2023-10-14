package com.workflow.service.impl;

import com.workflow.model.Account;
import com.workflow.repository.IAccountRepo;
import com.workflow.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepo iAccountRepo;

    @Override
    public Account findByUsername(String username) {
        Account account = iAccountRepo.findByUsername(username);
        return account;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findByUsername(username);
        return new User(username, account.getPassword(), account.getRoles());
    }

    @Override
    public List<Account> findAll() {
        return (List<Account>) iAccountRepo.findAll();
    }

    @Override
    public void save(Account account) {
        iAccountRepo.save(account);
    }

    @Override
    public void edit(Account account) {
        iAccountRepo.save(account);
    }

    @Override
    public void delete(int id) {
        iAccountRepo.deleteById(id);
    }

    @Override
    public Account findByTeamId(int id) {

        return iAccountRepo.findById(id).get();
    }

    public String getCurrentUsername (){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    public Account getCurrentAccount(){ // trả về account hiện tại đang đăng nhập
        return findByUsername(getCurrentUsername());
    }
}
