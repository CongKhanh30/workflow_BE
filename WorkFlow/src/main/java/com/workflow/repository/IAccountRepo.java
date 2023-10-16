package com.workflow.repository;

import com.workflow.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepo extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
}
