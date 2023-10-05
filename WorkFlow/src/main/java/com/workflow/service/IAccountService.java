package com.workflow.service;

import com.workflow.model.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends IService<Account>, UserDetailsService {
    Account findByUsername(String username);
}
