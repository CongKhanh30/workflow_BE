package com.workflow.repository;

import com.workflow.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface IAccountRepo extends CrudRepository<Account, Integer> {
    Account findByUsername(String username);
}
