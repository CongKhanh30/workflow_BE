package com.workflow.service.impl;

import com.workflow.model.Account;
import com.workflow.model.AccountRole;
import com.workflow.model.Role;
import com.workflow.repository.IAccountRepo;
import com.workflow.repository.IAccountRoleRepo;
import com.workflow.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepo iAccountRepo;
    private final IAccountRoleRepo accountRoleRepo;

    private List<Role> getRoles(Account account){
        List<Role> roles = new ArrayList<>();
        List<AccountRole> accountRoles = accountRoleRepo.findAllByAccount(account);
        for (AccountRole ar : accountRoles) {
            roles.add(ar.getRole());
        }
        return roles;
    }
    @Override
    public Account findByUsername(String username) {
        Account account = iAccountRepo.findByUsername(username);
        if (account == null) return null;
        account.setRoles(getRoles(account));
        return account;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findByUsername(username);
        account.setRoles(getRoles(account));
        return new User(username, account.getPassword(), getRoles(account));
    }

    @Override
    public List<Account> findAll() {
        return iAccountRepo.findAll();
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
        return iAccountRepo.findById(id).orElse(null);
    }

    public String getCurrentUsername (){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    public Account getCurrentAccount(){ // trả về account hiện tại đang đăng nhập
        return findByUsername(getCurrentUsername());
    }
}
